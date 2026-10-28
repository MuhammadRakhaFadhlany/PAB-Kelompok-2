package id.ac.stmi.pabkel2

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SupportChatActivity : AppCompatActivity() {
    private lateinit var messages: LinearLayout
    private lateinit var scroll: ScrollView
    private lateinit var input: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_chat)
        messages = findViewById(R.id.chatMessages)
        scroll = findViewById(R.id.chatScroll)
        input = findViewById(R.id.etChatMessage)
        appendMessage("Halo!", true)
        appendMessage("Halo! Pilih nomor layanan agar tim Geara dapat membantu Anda.\n\n1. Pesanan suku cadang\n2. Pembayaran\n3. Akun dan profil\n4. Lacak pengiriman\n5. Keamanan akun", false)
        findViewById<Button>(R.id.btnSendChat).setOnClickListener { sendMessage() }
        input.setOnEditorActionListener { _, _, _ -> sendMessage(); true }
    }

    private fun sendMessage() {
        val text = input.text.toString().trim()
        if (text.isBlank()) return
        input.setText("")
        appendMessage(text, true)
        val latest = OrderHistory.getAll(this).firstOrNull()
        when (text) {
            "1" -> if (latest == null) appendMessage("Belum ada pesanan di akun Anda. Pilih produk dari katalog untuk mulai berbelanja.", false, "Buka Riwayat Transaksi") { openOrders() }
            else appendMessage("Pesanan aktif Anda\n${latest.items}\nNo. pesanan: ${latest.id}\n${latest.date}\nStatus: ${latest.status}", false, "Lacak Pesanan") { openTracking(latest.id) }
            "2" -> appendMessage("Pembayaran tersedia melalui Transfer Bank, E-Wallet, atau Bayar di Tempat (COD). Pilihan metode dapat diatur saat checkout.", false)
            "3" -> {
                val prefs = getSharedPreferences("geara", MODE_PRIVATE)
                appendMessage("Akun Geara\nNama: ${prefs.getString("customer_name", "Pelanggan Geara")}\nEmail: ${prefs.getString("customer_email", "")}", false, "Buka Profil") {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
            }
            "4" -> if (latest == null) appendMessage("Belum ada pesanan yang dapat dilacak.", false)
            else appendMessage("Status pesanan ${latest.id}: ${latest.status}.", false, "Lacak Pesanan") { openTracking(latest.id) }
            "5" -> appendMessage("Jangan bagikan kata sandi atau kode verifikasi kepada siapa pun. Tim Geara tidak akan meminta kode rahasia Anda.", false)
            else -> appendMessage("Saya belum memahami pesan tersebut. Balas dengan angka 1 sampai 5, atau hubungi kami melalui WhatsApp di halaman Pusat Bantuan.", false)
        }
    }

    private fun openOrders() = startActivity(Intent(this, OrderStatusActivity::class.java))
    private fun openTracking(id: String) = startActivity(Intent(this, TrackingActivity::class.java).putExtra("ORDER_ID", id))

    private fun appendMessage(text: String, fromCustomer: Boolean, actionLabel: String? = null, action: (() -> Unit)? = null) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = if (fromCustomer) Gravity.END else Gravity.START
            setPadding(0, 5, 0, 5)
        }
        val bubble = TextView(this).apply {
            this.text = text
            textSize = 13f
            setTextColor(Color.rgb(64, 35, 28))
            setPadding(16, 13, 16, 13)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 24f
                setColor(if (fromCustomer) Color.rgb(246, 235, 177) else Color.WHITE)
                if (!fromCustomer) setStroke(1, Color.rgb(244, 81, 30))
            }
        }
        row.addView(bubble, LinearLayout.LayoutParams(if (fromCustomer) dp(230) else dp(285), ViewGroup.LayoutParams.WRAP_CONTENT))
        val time = TextView(this).apply {
            this.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            textSize = 11f
            setTextColor(Color.rgb(112, 96, 87))
            gravity = if (fromCustomer) Gravity.END else Gravity.START
            setPadding(5, 2, 5, 1)
        }
        row.addView(time)
        if (actionLabel != null && action != null) {
            val actionButton = Button(this)

            actionButton.text = actionLabel
            actionButton.isAllCaps = false
            actionButton.setTextColor(Color.WHITE)

            actionButton.backgroundTintList =
                androidx.core.content.ContextCompat.getColorStateList(
                    this,
                    R.color.geara_orange
                )

            actionButton.setOnClickListener {
                action.invoke()
            }

            row.addView(actionButton)
        }
        messages.addView(row)
        scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
