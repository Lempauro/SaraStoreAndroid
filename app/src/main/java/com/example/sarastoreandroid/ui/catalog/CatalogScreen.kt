package com.example.sarastoreandroid.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import com.example.sarastoreandroid.R

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int,
    val sizes: List<String> = listOf("S", "M", "L", "XL")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    products: List<Product>,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAddToCart: (Product, String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar: logo + search + icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo (no muy grande)
            Image(
                painter = painterResource(id = R.drawable.logo_sarastore),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(56.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Search bar
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar productos...") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Icons (Inicio, Carrito, Yo)
            Row {
                IconButton(onClick = onHomeClick) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Inicio")
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = onCartClick) {
                    Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = onProfileClick) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "Tu")
                }
            }
        }

        HorizontalDivider()

        // Title
        Text(
            text = "Nuestros productos",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 8.dp)
        )

        // Grid-like layout using pairs in LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            val filtered = if (query.isBlank()) products else products.filter {
                it.name.lowercase().contains(query.trim().lowercase())
            }

            val rows = filtered.chunked(2)
            items(rows) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (item in rowItems) {
                        ProductCard(
                            product = item,
                            modifier = Modifier.weight(1f),
                            onAdd = { selectedProduct, selectedSize ->
                                onAddToCart(selectedProduct, selectedSize)
                            }
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onAdd: (Product, String) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))

            // Tallas -> dropdown simple
            var expanded by remember { mutableStateOf(false) }
            var selectedSize by remember { mutableStateOf(product.sizes.firstOrNull() ?: "M") }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Tallas:", modifier = Modifier.padding(end = 8.dp))

                // <-- Cambiado: altura mayor, singleLine y textStyle para evitar recorte -->
                OutlinedTextField(
                    value = selectedSize,
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier
                        .width(80.dp)
                        .height(48.dp), // aumento de altura para que no recorte el texto
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp), // tamaño de texto legible
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Tallas")
                        }
                    }
                )

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    product.sizes.forEach { s ->
                        DropdownMenuItem(text = { Text(s) }, onClick = {
                            selectedSize = s
                            expanded = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Precio: ${product.price}", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onAdd(product, selectedSize) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CB371))
            ) {
                Text(text = "Añadir al carrito", color = Color.White)
            }
        }
    }
}
