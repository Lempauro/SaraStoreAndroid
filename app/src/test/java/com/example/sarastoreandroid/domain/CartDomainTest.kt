package com.example.sarastoreandroid.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class CartDomainTest {

    @Test
    fun calculateTotalEmptyListReturnsZero() {
        val total = CartDomain.calculateTotal(emptyList())
        assertEquals(0, total)
    }

    @Test
    fun calculateTotalWithItemsReturnsSum() {
        val items = listOf(
            DomainCartItem(1, "A", 100, "M", 2), // 200
            DomainCartItem(2, "B", 50, "L", 1)   // 50
        )
        val total = CartDomain.calculateTotal(items)
        assertEquals(250, total)
    }

    @Test
    fun addOrIncrementAddsNewItem() {
        val items = emptyList<DomainCartItem>()
        val result = CartDomain.addOrIncrement(items, 1, "A", 100, "M")
        assertEquals(1, result.size)
        assertEquals(1, result[0].quantity)
    }

    @Test
    fun addOrIncrementIncrementsExisting() {
        val items = listOf(DomainCartItem(1, "A", 100, "M", 1))
        val result = CartDomain.addOrIncrement(items, 1, "A", 100, "M")
        assertEquals(1, result.size)
        assertEquals(2, result[0].quantity)
    }
}
