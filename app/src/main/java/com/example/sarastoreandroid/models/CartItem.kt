package com.example.sarastoreandroid.models

/**
 * Representa un ítem dentro del carrito de compras.
 * - product: el producto asociado.
 * - quantity: cantidad seleccionada.
 */
data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
