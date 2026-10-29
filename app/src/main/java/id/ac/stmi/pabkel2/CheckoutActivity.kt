package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.manager.CartManager
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {
    private fun money(value: Double) = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(value).replace(",00", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val items = CartManager.getItems()
        findViewById<EditText>(R.id.etAddress).setText(getSharedPreferences("geara", MODE_PRIVATE).getString("shipping_address", ""))
        findViewById<ListView>(R.id.lvCheckoutItems).adapter = id.ac.stmi.pabkel2.adapter.CheckoutAdapter(items)

        val subtotal = CartManager.getSubtotal()
        val tax = (subtotal * 0.10).toInt().toDouble()
        val shipping = if (subtotal == 0.0) 0.0 else 3000.0
        val total = subtotal + tax + shipping
        findViewById<TextView>(R.id.tvSubtotal).text = "Subtotal: ${money(subtotal)}"
        findViewById<TextView>(R.id.tvTax).text = "Pajak dan biaya: ${money(tax)}"
        findViewById<TextView>(R.id.tvShipping).text = "Ongkir: ${money(shipping)}"
        findViewById<TextView>(R.id.tvTotal).text = "Total: ${money(total)}"

        findViewById<Button>(R.id.btnPlaceOrder).setOnClickListener {
            val address = findViewById<EditText>(R.id.etAddress)
            val value = address.text.toString().trim()
            when {
                items.isEmpty() -> Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
                value.isBlank() -> { address.error = "Alamat wajib diisi"; address.requestFocus() }
                else -> {
                    getSharedPreferences("geara", MODE_PRIVATE).edit()
                        .putString("shipping_address", value)
                        .putInt("order_subtotal", subtotal.toInt())
                        .putInt("order_tax", tax.toInt())
                        .putInt("order_shipping", shipping.toInt())
                        .putInt("order_total", total.toInt())
                        .apply()
                    startActivity(Intent(this, PaymentActivity::class.java))
                }
            }
        }
    }
}
