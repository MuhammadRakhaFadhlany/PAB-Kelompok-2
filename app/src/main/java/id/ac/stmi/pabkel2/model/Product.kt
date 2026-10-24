package id.ac.stmi.pabkel2.model

data class Product(
    val id: Int,
    val name: String,
    val code: String,
    val category: String,
    val price: Double,
    val compatibility: String,
    val description: String,
    val image: Int = 0
)
