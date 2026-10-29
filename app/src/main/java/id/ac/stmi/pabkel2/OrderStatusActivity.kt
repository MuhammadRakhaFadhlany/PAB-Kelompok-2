package id.ac.stmi.pabkel2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.adapter.OrderAdapter

class OrderStatusActivity : AppCompatActivity() {
    private var selectedTab = "Aktif"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status)

        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }

        val btnAktif = findViewById<TextView>(R.id.btnTabAktif)
        val btnSelesai = findViewById<TextView>(R.id.btnTabSelesai)
        val btnDibatalkan = findViewById<TextView>(R.id.btnTabDibatalkan)

        fun updateTabUI() {
            btnAktif.setBackgroundResource(if (selectedTab == "Aktif") R.drawable.bg_tab_pill_selected else R.drawable.bg_tab_pill_unselected)
            btnAktif.setTextColor(if (selectedTab == "Aktif") Color.WHITE else Color.parseColor("#F4511E"))

            btnSelesai.setBackgroundResource(if (selectedTab == "Selesai") R.drawable.bg_tab_pill_selected else R.drawable.bg_tab_pill_unselected)
            btnSelesai.setTextColor(if (selectedTab == "Selesai") Color.WHITE else Color.parseColor("#F4511E"))

            btnDibatalkan.setBackgroundResource(if (selectedTab == "Dibatalkan") R.drawable.bg_tab_pill_selected else R.drawable.bg_tab_pill_unselected)
            btnDibatalkan.setTextColor(if (selectedTab == "Dibatalkan") Color.WHITE else Color.parseColor("#F4511E"))

            renderOrders()
        }

        btnAktif.setOnClickListener { selectedTab = "Aktif"; updateTabUI() }
        btnSelesai.setOnClickListener { selectedTab = "Selesai"; updateTabUI() }
        btnDibatalkan.setOnClickListener { selectedTab = "Dibatalkan"; updateTabUI() }
    }

    override fun onResume() {
        super.onResume()
        renderOrders()
    }

    private fun renderOrders() {
        val allOrders = OrderHistory.getAll(this)
        val filtered = allOrders.filter { order ->
            when (selectedTab) {
                "Aktif" -> order.status != "Selesai" && order.status != "Pesanan diterima" && order.status != "Dibatalkan"
                "Selesai" -> order.status == "Selesai" || order.status == "Pesanan diterima"
                "Dibatalkan" -> order.status == "Dibatalkan"
                else -> true
            }
        }

        val emptyView = findViewById<TextView>(R.id.tvOrderStatus)
        val listView = findViewById<ListView>(R.id.lvOrders)
        listView.emptyView = emptyView

        listView.adapter = OrderAdapter(filtered)
        listView.setOnItemClickListener { _, _, position, _ ->
            if (filtered.isNotEmpty()) {
                showOrderActions(filtered[position])
            }
        }
    }

    private fun showOrderActions(order: OrderRecord) {
        val actions = mutableListOf<String>()
        if (order.status == "Belum Dibayar" || order.status == "Menunggu Pembayaran") {
            actions += "Bayar Sekarang"
        }
        actions += "Lihat detail"
        actions += "Lacak pesanan"
        if (order.rating == 0f && order.status != "Dibatalkan") actions += "Berikan ulasan"
        if (order.status == "Belum Dibayar" || order.status == "Pesanan diterima" || order.status == "Dikemas") actions += "Batalkan pesanan"
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("${order.id} - ${order.status}")
            .setItems(actions.toTypedArray()) { _, which ->
                when (actions[which]) {
                    "Bayar Sekarang" -> {
                        OrderHistory.updateStatus(this, order.id, "Pesanan diterima")
                        android.widget.Toast.makeText(this, "Pembayaran berhasil!", android.widget.Toast.LENGTH_SHORT).show()
                        renderOrders()
                    }
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
