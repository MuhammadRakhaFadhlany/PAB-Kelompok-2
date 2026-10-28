package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OrderStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status)
        findViewById<Button>(R.id.btnBackHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }

    override fun onResume() { super.onResume(); renderOrders() }

    private fun renderOrders() {
        val orders = OrderHistory.getAll(this)
        val empty = findViewById<TextView>(R.id.tvOrderStatus)
        val list = findViewById<ListView>(R.id.lvOrders)
        empty.text = "Belum ada transaksi. Pesanan yang sudah dibayar akan muncul di sini."
        list.emptyView = empty
        list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, orders.map { order ->
            val review = if (order.rating > 0) "\nUlasan: ${order.rating.toInt()}/5" else ""
            "${order.id} | ${order.date}\n${order.items.replace("\n", "\n  ")}\nTotal ${money(order.total)} | ${order.status}$review"
        })
        list.setOnItemClickListener { _, _, position, _ -> showOrderActions(orders[position]) }
    }

    private fun showOrderActions(order: OrderRecord) {
        val actions = mutableListOf("Lihat detail", "Lacak pesanan")
        if (order.rating == 0f && order.status != "Dibatalkan") actions += "Berikan ulasan"
        if (order.status == "Pesanan diterima" || order.status == "Dikemas") actions += "Batalkan pesanan"
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("${order.id} - ${order.status}")
            .setItems(actions.toTypedArray()) { _, which ->
                when (actions[which]) {
                    "Lihat detail" -> showDetails(order)
                    "Lacak pesanan" -> startActivity(Intent(this, TrackingActivity::class.java).putExtra("ORDER_ID", order.id))
                    "Berikan ulasan" -> startActivity(Intent(this, ReviewActivity::class.java).putExtra("ORDER_ID", order.id))
                    "Batalkan pesanan" -> startActivity(Intent(this, CancelOrderActivity::class.java).putExtra("ORDER_ID", order.id))
                }
            }
            .setNegativeButton("Tutup", null)
            .show()
    }

    private fun showDetails(order: OrderRecord) {
        val reason = if (order.cancelReason.isBlank()) "" else "\nAlasan pembatalan: ${order.cancelReason}"
        val review = if (order.rating == 0f) "" else "\nUlasan: ${order.rating.toInt()}/5 - ${order.review}"
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Detail ${order.id}")
            .setMessage("${order.date}\n\n${order.items}\n\nSubtotal: ${money(order.subtotal)}\nPajak dan biaya: ${money(order.tax)}\nOngkir: ${money(order.shipping)}\nTotal: ${money(order.total)}\n\nPembayaran: ${order.paymentMethod}\nAlamat: ${order.address}\nStatus: ${order.status}$reason$review")
            .setPositiveButton("Tutup", null)
            .show()
    }

    private fun money(value: Int) = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("id", "ID")).format(value).replace(",00", "")
}
