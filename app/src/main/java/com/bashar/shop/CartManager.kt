package com.bashar.shop

object CartManager {
    val items = mutableListOf<CartItem>()

    fun add(product: Product) {
        val item = items.find { it.product.id == product.id }
        if (item == null) items.add(CartItem(product, 1)) else item.quantity++
    }

    fun total(): Double = items.sumOf { it.product.price * it.quantity }

    fun clear() = items.clear()
}
