package com.example.sarastoreandroid.ui.cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import java.text.NumberFormat
import java.util.Locale

/**
 * Pantalla de carrito: muestra lista de CartItem con edición de talla, cantidad,
 * eliminación y total dinámico.
 *
 * Nota: se removieron referencias a KeyboardOptions para evitar errores de compilación
 * en entornos donde la dependencia no esté resolviendo esa clase. El campo de cantidad
 * sigue aceptando solo dígitos y al cambiar reemplaza el item en la lista para forzar recomposición.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("DEPRECATION")
@Composable
fun CartScreen(
    cartItems: MutableList<CartItem>,
    onBack: () -> Unit,
    onUpdate: (() -> Unit)? = null,
    onCheckout: () -> Unit = {}
) {
    val context = LocalContext.current

    // Formatter memoizado para mostrar montos
    val formatter = remember { NumberFormat.getInstance(Locale("es", "CO")) }

    // total calculado a partir de la lista (memoizado)
    val total by remember {
        derivedStateOf { cartItems.sumOf { it.unitPrice * it.quantity } }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(12.dp)
            ) {
                if (cartItems.isEmpty()) {
                    Text("Tu carrito está vacío", fontSize = 16.sp, modifier = Modifier.padding(12.dp))
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        itemsIndexed(cartItems) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = item.imageRes),
                                        contentDescription = item.name,
                                        modifier = Modifier.size(80.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = item.name)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            // Talla: dropdown simple -> al seleccionar actualizamos el elemento
                                            var expanded by remember { mutableStateOf(false) }
                                            Box {
                                                OutlinedButton(onClick = { expanded = !expanded }) {
                                                    Text(text = item.size)
                                                }
                                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                                    listOf("S", "M", "L", "XL").forEach { s ->
                                                        DropdownMenuItem(text = { Text(s) }, onClick = {
                                                            // reemplazamos el elemento para forzar recomposición
                                                            val newItem = item.copy(size = s)
                                                            cartItems[index] = newItem
                                                            expanded = false
                                                            onUpdate?.invoke()
                                                        })
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(12.dp))

                                            // Cantidad editable -> al cambiar reemplazamos elemento
                                            var qtyText by remember { mutableStateOf(item.quantity.toString()) }
                                            OutlinedTextField(
                                                value = qtyText,
                                                onValueChange = { v ->
                                                    val clean = v.filter { it.isDigit() }
                                                    qtyText = clean
                                                    val num = clean.toIntOrNull() ?: 0
                                                    if (num > 0) {
                                                        val newItem = item.copy(quantity = num)
                                                        cartItems[index] = newItem
                                                        onUpdate?.invoke()
                                                    }
                                                },
                                                modifier = Modifier.width(88.dp),
                                                singleLine = true
                                                // NOTA: no usamos KeyboardOptions aquí (compatibilidad)
                                            )

                                            Spacer(modifier = Modifier.width(12.dp))

                                            Text(
                                                text = formatter.format(item.unitPrice),
                                                fontSize = 14.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }

                                    // Icono eliminar
                                    IconButton(onClick = {
                                        cartItems.removeAt(index)
                                        onUpdate?.invoke()
                                        Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }
                    }

                    // Total y botón de compra (color verde)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "TOTAL: ${formatter.format(total)}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onCheckout() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CB371))
                    ) {
                        Text("Realizar compra", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    )
}
