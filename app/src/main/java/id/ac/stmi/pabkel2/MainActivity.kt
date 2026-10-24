package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnCatalog = findViewById<Button>(R.id.btnCatalog)
        val btnCart = findViewById<Button>(R.id.btnCart)
        val btnServiceCenter = findViewById<Button>(R.id.btnServiceCenter)
        val btnOrderStatus = findViewById<Button>(R.id.btnOrderStatus)

        tvWelcome.text = "Selamat Datang di Auto Parts Hub!"

        btnCatalog.setOnClickListener {
            startActivity(Intent(this, CatalogActivity::class.java))
        }

        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        btnServiceCenter.setOnClickListener {
            startActivity(Intent(this, ServiceCenterActivity::class.java))
        }

        btnOrderStatus.setOnClickListener {
            startActivity(Intent(this, OrderStatusActivity::class.java))
        }
    }
}
