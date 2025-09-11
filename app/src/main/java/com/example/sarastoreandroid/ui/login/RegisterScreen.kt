package com.example.sarastoreandroid.ui.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * RegisterScreen con ExposedDropdownMenuBox corregido.
 * Usamos TextFieldDefaults.colors() en lugar de textFieldColors().
 */
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("DEPRECATION") // evita warning por ArrowBack si aplica
@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current
    val primaryBlue = Color(0xFF0D6EFD)

    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }

    val opcionesId = listOf("Cédula de ciudadanía", "Cédula de extranjería", "Pasaporte")
    var tipoId by remember { mutableStateOf(opcionesId[0]) }
    var expanded by remember { mutableStateOf(false) }

    var numeroId by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var confirmarCorreo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    val correosCoinciden = correo.isNotBlank() && correo == confirmarCorreo
    val contrasenasCoinciden = contrasena.length >= 6 && contrasena == confirmarContrasena
    val camposObligatorios = nombres.isNotBlank() && apellidos.isNotBlank() && numeroId.isNotBlank()
    val formValido = camposObligatorios && correosCoinciden && contrasenasCoinciden && celular.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crea tu cuenta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Iniciar sesión",
                    color = primaryBlue,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth()
            )

            // Dropdown corregido
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = tipoId,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Tipo de identificación") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opcionesId.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                tipoId = opcion
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = numeroId,
                onValueChange = { numeroId = it },
                label = { Text("Número de identificación") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = celular,
                onValueChange = { celular = it },
                label = { Text("Celular") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = confirmarCorreo,
                onValueChange = { confirmarCorreo = it },
                label = { Text("Confirmar correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = correo.isNotBlank() && confirmarCorreo.isNotBlank() && correo != confirmarCorreo
            )

            if (correo.isNotBlank() && confirmarCorreo.isNotBlank() && correo != confirmarCorreo) {
                Text(text = "Los correos no coinciden", color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña (mín. 6)") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = contrasena.isNotBlank() && confirmarContrasena.isNotBlank() && contrasena != confirmarContrasena
            )

            if (contrasena.isNotBlank() && confirmarContrasena.isNotBlank() && contrasena != confirmarContrasena) {
                Text(text = "Las contraseñas no coinciden", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                    onRegisterSuccess()
                },
                enabled = formValido,
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Registrarme", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
