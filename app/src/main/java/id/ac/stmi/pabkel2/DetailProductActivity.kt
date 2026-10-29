package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.manager.CartManager
import java.text.NumberFormat
import java.util.Locale

class DetailProductActivity : AppCompatActivity() {
    private fun money(value: Double) = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(value).replace(",00", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        val product = ProductCatalog.byId(intent.getIntExtra("PRODUCT_ID", 1)) ?: ProductCatalog.products.first()
        findViewById<ImageView>(R.id.imgDetail).setImageResource(if (product.image != 0) product.image else android.R.drawable.ic_menu_gallery)
        findViewById<TextView>(R.id.tvDetailName).text = product.name
        findViewById<TextView>(R.id.tvDetailPrice).text = money(product.price)
        findViewById<TextView>(R.id.tvDetailCode).text = "Kode: ${product.code} | Kategori: ${product.category} | Stok: ${product.stock} unit"
        findViewById<TextView>(R.id.tvDetailDescription).text = product.description + "\nCocok untuk: " + product.compatibility
        val quantity = findViewById<EditText>(R.id.etQuantity)
        val total = findViewById<TextView>(R.id.tvDetailSelectedTotal)
        fun updateTotal() {
            val count = quantity.text.toString().toIntOrNull() ?: 0
            total.text = "Total: ${money(product.price * count)}"
        }
        quantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = updateTotal()
            override fun afterTextChanged(s: Editable?) = Unit
        })
        updateTotal()
        findViewById<Button>(R.id.btnAddToCart).setOnClickListener {
            val count = quantity.text.toString().toIntOrNull()
            when {
                count == null || count < 1 -> quantity.error = "Masukkan jumlah minimal 1"
                count > product.stock -> quantity.error = "Stok tersedia ${product.stock} unit"
                !CartManager.add(product, count) -> Toast.makeText(this, "Jumlah melebihi stok yang tersedia", Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(this, "$count ${product.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CartActivity::class.java))
                }
            }
        }
    }
}
