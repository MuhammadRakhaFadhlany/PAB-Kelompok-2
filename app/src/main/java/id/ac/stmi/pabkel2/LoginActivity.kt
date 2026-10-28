package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        email.setText(getSharedPreferences("geara", MODE_PRIVATE).getString("customer_email", ""))
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val address = email.text.toString().trim()
            when {
                address.isBlank() -> email.error = "Email wajib diisi"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(address).matches() -> email.error = "Format email belum benar"
                password.text.isNullOrEmpty() -> password.error = "Password wajib diisi"
                else -> {
                    getSharedPreferences("geara", MODE_PRIVATE).edit().putString("customer_email", address).apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
        findViewById<TextView>(R.id.tvRegister).setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            Toast.makeText(this, "Hubungi Pusat Layanan untuk bantuan akun", Toast.LENGTH_SHORT).show()
        }
    }
}
