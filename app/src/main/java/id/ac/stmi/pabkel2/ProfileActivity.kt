package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private val prefs by lazy { getSharedPreferences("geara", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        renderAccount()
        findViewById<Button>(R.id.btnProfileOrders).setOnClickListener { startActivity(Intent(this, OrderStatusActivity::class.java)) }
        findViewById<Button>(R.id.btnProfileHelp).setOnClickListener { startActivity(Intent(this, ServiceCenterActivity::class.java)) }
        findViewById<Button>(R.id.btnMyProfile).setOnClickListener { showProfile() }
        findViewById<Button>(R.id.btnShippingAddress).setOnClickListener { editAddress() }
        findViewById<Button>(R.id.btnPaymentMethod).setOnClickListener { choosePaymentMethod() }
        findViewById<Button>(R.id.btnProfileSettings).setOnClickListener { showSettings() }
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }

    private fun renderAccount() {
        val name = prefs.getString("customer_name", "")?.ifBlank { "Pelanggan Geara" } ?: "Pelanggan Geara"
        val email = prefs.getString("customer_email", "") ?: ""
        findViewById<TextView>(R.id.tvProfileAccount).text = "$name\n$email"
    }

    private fun showProfile() {
        val name = prefs.getString("customer_name", "Pelanggan Geara") ?: "Pelanggan Geara"
        val email = prefs.getString("customer_email", "") ?: ""
        AlertDialog.Builder(this).setTitle("Data Profil").setMessage("Nama: $name\nEmail: $email")
            .setPositiveButton("Tutup", null).show()
    }

    private fun editAddress() {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
            setText(prefs.getString("shipping_address", ""))
            hint = "Alamat pengiriman"
        }
        AlertDialog.Builder(this).setTitle("Alamat Pengiriman").setView(input)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan") { _, _ ->
                val address = input.text.toString().trim()
                if (address.isBlank()) Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
                else { prefs.edit().putString("shipping_address", address).apply(); Toast.makeText(this, "Alamat disimpan", Toast.LENGTH_SHORT).show() }
            }.show()
    }

    private fun choosePaymentMethod() {
        val methods = arrayOf("Transfer Bank", "E-Wallet", "Bayar di Tempat (COD)")
        val current = prefs.getString("payment_method", methods[0])
        var selected = methods.indexOf(current).coerceAtLeast(0)
        AlertDialog.Builder(this).setTitle("Metode Pembayaran")
            .setSingleChoiceItems(methods, selected) { _, which -> selected = which }
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan") { _, _ ->
                prefs.edit().putString("payment_method", methods[selected]).apply()
                Toast.makeText(this, "Metode pembayaran disimpan", Toast.LENGTH_SHORT).show()
            }.show()
    }

    private fun showSettings() {
        val enabled = prefs.getBoolean("notifications_enabled", true)
        AlertDialog.Builder(this).setTitle("Pengaturan Notifikasi")
            .setSingleChoiceItems(arrayOf("Aktif", "Nonaktif"), if (enabled) 0 else 1) { dialog, which ->
                prefs.edit().putBoolean("notifications_enabled", which == 0).apply()
                dialog.dismiss()
                Toast.makeText(this, "Pengaturan disimpan", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tutup", null).show()
    }
}
