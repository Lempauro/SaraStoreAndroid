package com.example.sarastoreandroid.domain

/**
 * Data class para lógica de negocio del carrito (independiente de UI).
 */
data class DomainCartItem(
    val productId: Int,
    val name: String,
    val unitPrice: Int, // valor numérico, ej. 90000
    val size: String,
    val quantity: Int
)

object CartDomain {
    /**
     * Calcula el total en unidades monetarias (sum unitPrice * quantity).
     */
    fun calculateTotal(items: List<DomainCartItem>): Int {
        return items.sumOf { it.unitPrice * it.quantity }
    }

    /**
     * Devuelve una nueva lista con el producto añadido o incrementado (inmutable).
     */
    fun addOrIncrement(
        items: List<DomainCartItem>,
        productId: Int,
        name: String,
        unitPrice: Int,
        size: String
    ): List<DomainCartItem> {
        val mutable = items.toMutableList()
        val idx = mutable.indexOfFirst { it.productId == productId && it.size == size }
        if (idx >= 0) {
            val old = mutable[idx]
            mutable[idx] = old.copy(quantity = old.quantity + 1)
        } else {
            mutable.add(DomainCartItem(productId, name, unitPrice, size, 1))
        }
        return mutable.toList()
    }
}
