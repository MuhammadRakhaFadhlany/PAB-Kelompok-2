package id.ac.stmi.pabkel2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ServiceCenterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_center)
        findViewById<Button>(R.id.btnFaq).setOnClickListener { openFaq() }
        findViewById<Button>(R.id.btnContactSupport).setOnClickListener { openChat() }
        findViewById<TextView>(R.id.rowCustomerService).setOnClickListener { openChat() }
        findViewById<TextView>(R.id.rowWebsite).setOnClickListener {
            Toast.makeText(this, "Situs web Geara belum dikonfigurasi.", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.rowWhatsApp).setOnClickListener {
            shareTo("com.whatsapp", "WhatsApp")
        }
        findViewById<TextView>(R.id.rowFacebook).setOnClickListener {
            shareTo("com.facebook.katana", "Facebook")
        }
        findViewById<TextView>(R.id.rowInstagram).setOnClickListener {
            shareTo("com.instagram.android", "Instagram")
        }
    }

    private fun openFaq() = startActivity(Intent(this, FaqActivity::class.java))
    private fun openChat() = startActivity(Intent(this, SupportChatActivity::class.java))

    private fun shareTo(packageName: String, appName: String) {
        val message = "Halo Geara, saya membutuhkan bantuan terkait suku cadang/pesanan. Mohon bantuannya."
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            setPackage(packageName)
        }
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, "$appName belum terpasang atau tidak menerima pesan teks.", Toast.LENGTH_LONG).show()
        }
    }
}
