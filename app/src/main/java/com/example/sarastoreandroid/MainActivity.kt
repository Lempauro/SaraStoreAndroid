package com.example.sarastoreandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sarastoreandroid.ui.login.LoginScreen
import com.example.sarastoreandroid.ui.theme.SaraStoreAndroidTheme

/**
 * Actividad principal que carga la UI con Compose.
 * - Muestra LoginScreen inicialmente.
 * - Tras un login exitoso muestra un placeholder de catálogo.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaraStoreAndroidTheme {
                val logged = remember { mutableStateOf(false) }

                if (!logged.value) {
                    LoginScreen(onLoginSuccess = { logged.value = true })
                } else {
                    // Placeholder: reemplazar por CatalogScreen() en el siguiente módulo
                    Text(text = "Catálogo - (pendiente por crear)")
                }
            }
        }
    }
}
