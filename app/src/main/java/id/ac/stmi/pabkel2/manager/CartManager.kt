package id.ac.stmi.pabkel2.manager

import id.ac.stmi.pabkel2.model.Product

data class CartItem(val product: Product, var quantity: Int) {
    val subtotal: Double get() = product.price * quantity
}

object CartManager {
    private val items = mutableListOf<CartItem>()

    fun getItems(): List<CartItem> = items.toList()
    fun getItemQuantity(productId: Int): Int = items.find { it.product.id == productId }?.quantity ?: 0
    fun add(product: Product, quantity: Int = 1): Boolean {
        if (quantity < 1) return false
        val found = items.find { it.product.id == product.id }
        val next = (found?.quantity ?: 0) + quantity
        if (next > product.stock) return false
        if (found != null) found.quantity = next else items.add(CartItem(product, quantity))
        return true
    }
    fun updateQuantity(productId: Int, quantity: Int): Boolean {
        val item = items.find { it.product.id == productId } ?: return false
        if (quantity < 1) items.remove(item)
        else if (quantity > item.product.stock) return false
        else item.quantity = quantity
        return true
    }
    fun remove(productId: Int) { items.removeAll { it.product.id == productId } }
    fun getSubtotal(): Double = items.sumOf { it.subtotal }
    fun getTotalQuantity(): Int = items.sumOf { it.quantity }
    fun clear() { items.clear() }
}
