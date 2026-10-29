package id.ac.stmi.pabkel2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ServiceCenterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_center)

        findViewById<TextView>(R.id.btnServiceBack).setOnClickListener { finish() }

        val btnTabFaq = findViewById<TextView>(R.id.btnTabFaq)
        val btnTabContact = findViewById<TextView>(R.id.btnTabContact)

        btnTabFaq.setOnClickListener {
            startActivity(Intent(this, FaqActivity::class.java))
        }

        btnTabContact.setOnClickListener {
            btnTabContact.setBackgroundResource(R.drawable.bg_tab_pill_selected)
            btnTabContact.setTextColor(Color.WHITE)
            btnTabFaq.setBackgroundResource(R.drawable.bg_tab_pill_unselected)
            btnTabFaq.setTextColor(Color.parseColor("#F4511E"))
        }

        findViewById<View>(R.id.rowCustomerService).setOnClickListener { openChat() }
        findViewById<View>(R.id.rowWebsite).setOnClickListener {
            Toast.makeText(this, "Situs web Geara: https://geara.id", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.rowWhatsApp).setOnClickListener {
            shareTo("com.whatsapp", "WhatsApp")
        }
        findViewById<View>(R.id.rowFacebook).setOnClickListener {
            shareTo("com.facebook.katana", "Facebook")
        }
        findViewById<View>(R.id.rowInstagram).setOnClickListener {
            shareTo("com.instagram.android", "Instagram")
        }
    }

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
