package com.example.sarastoreandroid.ui.cart

/**
 * Modelo simple para un item en el carrito.
 * unitPrice: precio por unidad en enteros (ej. 90000)
 */
data class CartItem(
    val productId: Int,
    val name: String,
    val imageRes: Int,
    var size: String,
    var quantity: Int,
    val unitPrice: Int
)
