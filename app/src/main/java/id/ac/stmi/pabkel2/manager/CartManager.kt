package id.ac.stmi.pabkel2.manager

import id.ac.stmi.pabkel2.model.Product

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)

object CartManager {

    private val cartItems = mutableListOf<CartItem>()

    fun getItems(): List<CartItem> {
        return cartItems
    }

    fun addProduct(product: Product) {
        val existingItem = cartItems.find {
            it.product.id == product.id
        }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun increaseQuantity(productId: Int) {
        cartItems.find {
            it.product.id == productId
        }?.quantity = (cartItems.find {
            it.product.id == productId
        }?.quantity ?: 0) + 1
    }

    fun decreaseQuantity(productId: Int) {
        val item = cartItems.find {
            it.product.id == productId
        }

        if (item != null) {
            item.quantity--

            if (item.quantity <= 0) {
                cartItems.remove(item)
            }
        }
    }

    fun removeProduct(productId: Int) {
        cartItems.removeAll {
            it.product.id == productId
        }
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
