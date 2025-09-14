package com.example.sarastoreandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.sarastoreandroid.ui.catalog.CatalogScreen
import com.example.sarastoreandroid.ui.catalog.Product
import com.example.sarastoreandroid.ui.cart.CartItem
import com.example.sarastoreandroid.ui.cart.CartScreen
import com.example.sarastoreandroid.ui.login.ForgotPasswordScreen
import com.example.sarastoreandroid.ui.login.LoginScreen
import com.example.sarastoreandroid.ui.login.RegisterScreen
import com.example.sarastoreandroid.ui.theme.SaraStoreAndroidTheme

enum class Screen { LOGIN, REGISTER, FORGOT, CATALOG, CART, PLACEHOLDER }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaraStoreAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val current = remember { mutableStateOf(Screen.LOGIN) }

    // Lista mutable de CartItem (estado compartido)
    val cartItems = remember { mutableStateListOf<CartItem>() }

    // Productos de ejemplo con priceValue en enteros (por ejemplo 90000)
    val sampleProducts = listOf(
        Product(1, "Camiseta cuello redondo verde", "$90.000", 90000, R.drawable.camiseta_verde),
        Product(2, "Camiseta azul rey", "$90.000", 90000, R.drawable.camiseta_azul_rey),
        Product(3, "Camiseta tipo polo negra", "$120.000", 120000, R.drawable.camiseta_polo_negra),
        Product(4, "Camiseta cuello v agua marina", "$75.000", 75000, R.drawable.camiseta_agua_marina)
    )

    // Función para agregar producto al carrito
    fun addToCart(product: Product, size: String) {
        // si ya existe mismo producto y talla -> aumentar cantidad en 1
        val existing = cartItems.indexOfFirst { it.productId == product.id && it.size == size }
        if (existing >= 0) {
            val item = cartItems[existing]
            item.quantity = item.quantity + 1
            cartItems[existing] = item // trigger recomposition
        } else {
            cartItems.add(
                CartItem(
                    productId = product.id,
                    name = product.name,
                    imageRes = product.imageRes,
                    size = size,
                    quantity = 1,
                    unitPrice = product.priceValue
                )
            )
        }
    }

    when (current.value) {
        Screen.LOGIN -> LoginScreen(
            onLoginSuccess = { current.value = Screen.CATALOG },
            onRegisterClick = { current.value = Screen.REGISTER },
            onForgotClick = { current.value = Screen.FORGOT }
        )

        Screen.REGISTER -> RegisterScreen(
            onBack = { current.value = Screen.LOGIN },
            onRegisterSuccess = { current.value = Screen.LOGIN }
        )

        Screen.FORGOT -> ForgotPasswordScreen(onBack = { current.value = Screen.LOGIN })

        Screen.CATALOG -> CatalogScreen(
            products = sampleProducts,
            onHomeClick = { current.value = Screen.PLACEHOLDER },
            onCartClick = { current.value = Screen.CART },
            onProfileClick = { current.value = Screen.PLACEHOLDER },
            onAddToCart = { product, size ->
                addToCart(product, size)
            }
        )

        Screen.CART -> CartScreen(
            cartItems = cartItems,
            onBack = { current.value = Screen.CATALOG },
            onUpdate = { /* opcional: persistir si quieres */ },
            onCheckout = { current.value = Screen.PLACEHOLDER }
        )

        Screen.PLACEHOLDER -> {
            androidx.compose.material3.Text(text = "Pendiente por hacer", modifier = Modifier.fillMaxSize())
        }
    }
}
