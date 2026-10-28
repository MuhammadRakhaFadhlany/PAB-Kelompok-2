package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etRegisterEmail)
        val password = findViewById<EditText>(R.id.etRegisterPassword)
        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            when {
                name.text.isNullOrBlank() -> name.error = "Nama wajib diisi"
                email.text.isNullOrBlank() -> email.error = "Email wajib diisi"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString().trim()).matches() -> email.error = "Format email belum benar"
                password.text.isNullOrBlank() -> password.error = "Password wajib diisi"
                else -> {
                    getSharedPreferences("geara", MODE_PRIVATE).edit()
                        .putString("customer_name", name.text.toString().trim())
                        .putString("customer_email", email.text.toString().trim())
                        .apply()
                    Toast.makeText(this, "Pendaftaran berhasil. Silakan masuk.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
        findViewById<TextView>(R.id.tvBackLogin).setOnClickListener { finish() }
    }
}
