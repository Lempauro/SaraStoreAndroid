package com.example.sarastoreandroid.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla placeholder para "¿Olvidaste tu contraseña?"
 * Muestra un texto y un botón "Volver".
 * Cuando se implemente la recuperación real, aquí irá la lógica.
 */
@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Recuperación de contraseña (pendiente de implementación)")
            Button(onClick = onBack) {
                Text(text = "Volver")
            }
        }
    }
}
