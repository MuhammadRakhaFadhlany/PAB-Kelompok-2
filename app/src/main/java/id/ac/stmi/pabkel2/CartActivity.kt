package id.ac.stmi.pabkel2

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.manager.CartManager
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {
    private fun money(value: Double) = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(value).replace(",00", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        findViewById<Button>(R.id.btnCheckout).setOnClickListener {
            if (CartManager.getItems().isEmpty()) Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
            else startActivity(android.content.Intent(this, CheckoutActivity::class.java))
        }
        render()
    }

    override fun onResume() { super.onResume(); render() }

    private fun render() {
        val items = CartManager.getItems()
        val list = findViewById<ListView>(R.id.lvCart)
        val rows = if (items.isEmpty()) listOf("Keranjang masih kosong. Pilih produk dari katalog untuk mulai berbelanja.")
        else items.map { "${it.product.name}\n${it.quantity} unit x ${money(it.product.price)} = ${money(it.subtotal)}\nKetuk untuk ubah jumlah; tekan lama untuk hapus" }
        list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rows)
        list.setOnItemClickListener { _, _, position, _ -> if (items.isNotEmpty()) editQuantity(items[position].product.id, items[position].quantity, items[position].product.stock) }
        list.setOnItemLongClickListener { _, _, position, _ ->
            if (items.isNotEmpty()) {
                val item = items[position]
                AlertDialog.Builder(this).setTitle("Hapus ${item.product.name}?")
                    .setMessage("Barang ini akan dihapus dari keranjang.")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ -> CartManager.remove(item.product.id); render() }
                    .show()
            }
            true
        }
        findViewById<TextView>(R.id.tvCartSubtotal).text = "Subtotal (${CartManager.getTotalQuantity()} unit): ${money(CartManager.getSubtotal())}"
    }

    private fun editQuantity(productId: Int, current: Int, stock: Int) {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(current.toString())
            selectAll()
            hint = "Jumlah (maks. $stock)"
        }
        AlertDialog.Builder(this)
            .setTitle("Ubah jumlah")
            .setMessage("Stok tersedia: $stock unit")
            .setView(input)
            .setNegativeButton("Batal", null)
            .setNeutralButton("Hapus") { _, _ -> CartManager.remove(productId); render() }
            .setPositiveButton("Simpan") { _, _ ->
                val qty = input.text.toString().toIntOrNull() ?: 0
                if (qty < 1 || qty > stock) Toast.makeText(this, "Masukkan jumlah 1 sampai $stock", Toast.LENGTH_SHORT).show()
                else { CartManager.updateQuantity(productId, qty); render() }
            }
            .show()
    }
}
