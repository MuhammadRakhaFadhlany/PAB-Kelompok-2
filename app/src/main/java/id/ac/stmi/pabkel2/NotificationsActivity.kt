package id.ac.stmi.pabkel2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        val orders = OrderHistory.getAll(this)
        val messages = mutableListOf(
            "Suku cadang baru untuk kendaraan Anda. Buka katalog Geara untuk melihat pilihan terbaru.",
            "Suku cadang favorit Anda sedang promo.",
            "Kurir sedang dalam perjalanan mengantarkan pesanan."
        )
        orders.firstOrNull()?.let { messages.add(0, "Pesanan ${it.id}: ${it.status}. Ketuk untuk membuka riwayat transaksi.") }
        val list = findViewById<ListView>(R.id.lvNotifications)
        list.adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return (super.getView(position, convertView, parent) as TextView).apply {
                    setTextColor(Color.WHITE)
                    textSize = 15f
                    setPadding(18, 18, 18, 18)
                    minHeight = 84
                    setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
        list.setOnItemClickListener { _, _, position, _ ->
            if (orders.isNotEmpty() && position == 0) startActivity(Intent(this, OrderStatusActivity::class.java))
        }
    }
}
