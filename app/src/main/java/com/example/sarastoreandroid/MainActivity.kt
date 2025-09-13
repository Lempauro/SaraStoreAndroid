package com.example.sarastoreandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.sarastoreandroid.ui.catalog.CatalogScreen
import com.example.sarastoreandroid.ui.catalog.Product
import com.example.sarastoreandroid.ui.login.ForgotPasswordScreen
import com.example.sarastoreandroid.ui.login.LoginScreen
import com.example.sarastoreandroid.ui.login.RegisterScreen
import com.example.sarastoreandroid.ui.theme.SaraStoreAndroidTheme

enum class Screen { LOGIN, REGISTER, FORGOT, CATALOG, PLACEHOLDER }

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

    // Productos de ejemplo con tus drawables renombrados
    val sampleProducts = listOf(
        Product(1, "Camiseta cuello redondo verde", "$90.000", R.drawable.camiseta_verde),
        Product(2, "Camiseta azul rey", "$90.000", R.drawable.camiseta_azul_rey),
        Product(3, "Camiseta tipo polo negra", "$120.000", R.drawable.camiseta_polo_negra),
        Product(4, "Camiseta cuello v agua marina", "$75.000", R.drawable.camiseta_agua_marina)
    )

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
            onCartClick = { current.value = Screen.PLACEHOLDER },
            onProfileClick = { current.value = Screen.PLACEHOLDER },
            onAddToCart = { _, _ -> current.value = Screen.PLACEHOLDER }
        )

        Screen.PLACEHOLDER -> {
            androidx.compose.material3.Text(text = "Pendiente por hacer", modifier = Modifier.fillMaxSize())
        }
    }
}
