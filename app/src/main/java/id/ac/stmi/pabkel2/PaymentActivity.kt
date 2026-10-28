package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.manager.CartManager
import java.text.NumberFormat
import java.util.Locale

class PaymentActivity : AppCompatActivity() {
    private fun money(value: Int) = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(value).replace(",00", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val prefs = getSharedPreferences("geara", MODE_PRIVATE)
        val address = prefs.getString("shipping_address", "Alamat belum diisi") ?: "Alamat belum diisi"
        val subtotal = prefs.getInt("order_subtotal", 0)
        val tax = prefs.getInt("order_tax", 0)
        val shipping = prefs.getInt("order_shipping", 0)
        val total = prefs.getInt("order_total", 0)
        findViewById<TextView>(R.id.tvPaymentAddress).text = address
        val savedMethod = prefs.getString("payment_method", "Transfer Bank")
        val paymentGroup = findViewById<RadioGroup>(R.id.rgPaymentMethod)
        paymentGroup.check(when (savedMethod) {
            "E-Wallet" -> R.id.rbEWallet
            "Bayar di Tempat (COD)" -> R.id.rbCOD
            else -> R.id.rbTransfer
        })
        findViewById<TextView>(R.id.tvPaymentTotal).text = money(total)
        val summary = CartManager.getItems().joinToString("\n") {
            "${it.product.name} x ${it.quantity} = ${money(it.subtotal.toInt())}"
        }
        findViewById<TextView>(R.id.tvPaymentSummary).text = if (summary.isBlank()) "Ringkasan pesanan tidak tersedia" else summary

        findViewById<Button>(R.id.btnPayNow).setOnClickListener {
            val method = when (findViewById<RadioGroup>(R.id.rgPaymentMethod).checkedRadioButtonId) {
                R.id.rbTransfer -> "Transfer Bank"
                R.id.rbEWallet -> "E-Wallet"
                R.id.rbCOD -> "Bayar di Tempat (COD)"
                else -> null
            }
            if (method == null) {
                Toast.makeText(this, "Pilih metode pembayaran", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val items = CartManager.getItems()
            if (items.isEmpty()) {
                Toast.makeText(this, "Keranjang kosong. Silakan pilih produk kembali.", Toast.LENGTH_SHORT).show()
                finish()
                return@setOnClickListener
            }
            OrderHistory.add(this, items, subtotal, tax, shipping, total, address, method)
            prefs.edit().putString("last_order", "Pesanan berhasil dibuat dengan $method. Total ${money(total)}.").apply()
            CartManager.clear()
            Toast.makeText(this, "Pesanan berhasil dibuat", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, OrderStatusActivity::class.java))
            finish()
        }
    }
}
