package com.example.sarastoreandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sarastoreandroid.ui.login.ForgotPasswordScreen
import com.example.sarastoreandroid.ui.login.LoginScreen
import com.example.sarastoreandroid.ui.login.RegisterScreen
import com.example.sarastoreandroid.ui.theme.SaraStoreAndroidTheme

/**
 * MainActivity: controla navegación simple entre pantallas.
 */
class MainActivity : ComponentActivity() {

    private enum class Screen { LOGIN, REGISTER, FORGOT, CATALOG }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaraStoreAndroidTheme {
                val current = remember { mutableStateOf(Screen.LOGIN) }

                when (current.value) {
                    Screen.LOGIN -> LoginScreen(
                        onLoginSuccess = { current.value = Screen.CATALOG },
                        onRegisterClick = { current.value = Screen.REGISTER },
                        onForgotClick = { current.value = Screen.FORGOT }
                    )

                    Screen.REGISTER -> RegisterScreen(
                        onBack = { current.value = Screen.LOGIN },
                        onRegisterSuccess = { current.value = Screen.CATALOG }
                    )

                    Screen.FORGOT -> ForgotPasswordScreen(
                        onBack = { current.value = Screen.LOGIN }
                    )

                    Screen.CATALOG -> {
                        // Temporal: cuando tengamos CatalogScreen() lo reemplazamos aquí
                        Text(text = "Catálogo - (pendiente por crear)")
                    }
                }
            }
        }
    }
}
