package com.example.sarastoreandroid.models

/**
 * Representa un usuario del sistema.
 * Ajustar campos de acuerdo al contrato real del backend si es necesario.
 */
data class User(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val token: String? = null // si se maneja token de autenticación
)
