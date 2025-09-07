package com.example.sarastoreandroid.models

/**
 * Representa un producto de la tienda.
 * Campos básicos: id, nombre, descripción, precio e imagen (URL).
 * Data class para facilitar la conversión desde JSON (Gson/Retrofit).
 */
data class Product(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = ""
)
