package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences("geara", MODE_PRIVATE)
        val name = prefs.getString("customer_name", "")?.substringBefore(" ").orEmpty()
        val email = prefs.getString("customer_email", "")?.substringBefore("@").orEmpty()
        val greeting = name.ifBlank { email }
        findViewById<TextView>(R.id.tvWelcome).text = if (greeting.isBlank()) "Halo, Sobat Geara!" else "Halo, $greeting!"

        findViewById<Button>(R.id.btnCatalog).setOnClickListener { openCatalog() }
        findViewById<TextView>(R.id.btnNotifications).setOnClickListener { startActivity(Intent(this, NotificationsActivity::class.java)) }
        findViewById<TextView>(R.id.btnCart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
        findViewById<TextView>(R.id.btnProfile).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        findViewById<Button>(R.id.btnOrderStatus).setOnClickListener { startActivity(Intent(this, OrderStatusActivity::class.java)) }
        findViewById<Button>(R.id.btnServiceCenter).setOnClickListener { startActivity(Intent(this, ServiceCenterActivity::class.java)) }

        findViewById<EditText>(R.id.etHomeSearch).setOnEditorActionListener { field, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH || action == EditorInfo.IME_ACTION_DONE) {
                startActivity(Intent(this, CatalogActivity::class.java).putExtra("SEARCH", field.text.toString()))
                true
            } else false
        }
        mapOf(R.id.catRem to "Rem", R.id.catMesin to "Mesin", R.id.catFilter to "Filter", R.id.catLampu to "Lampu", R.id.catOli to "Oli")
            .forEach { (id, category) -> findViewById<TextView>(id).setOnClickListener { openCatalog(category) } }
        mapOf(R.id.homeProduct1 to 3, R.id.homeProduct2 to 1, R.id.homeProduct3 to 5)
            .forEach { (id, productId) -> findViewById<android.view.View>(id).setOnClickListener { openDetail(productId) } }
    }

    private fun openCatalog(category: String? = null) {
        val intent = Intent(this, CatalogActivity::class.java)
        if (category != null) intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }

    private fun openDetail(productId: Int) {
        startActivity(Intent(this, DetailProductActivity::class.java).putExtra("PRODUCT_ID", productId))
    }
}
