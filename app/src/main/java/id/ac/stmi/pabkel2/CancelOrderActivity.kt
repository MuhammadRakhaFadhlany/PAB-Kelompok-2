package id.ac.stmi.pabkel2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CancelOrderActivity : AppCompatActivity() {
    private lateinit var orderId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_order)
        orderId = intent.getStringExtra("ORDER_ID").orEmpty()
        findViewById<Button>(R.id.btnSubmitCancel).setOnClickListener {
            val reasonGroup = findViewById<RadioGroup>(R.id.rgCancelReason)
            val other = findViewById<EditText>(R.id.etCancelOther)
            val selected = when (reasonGroup.checkedRadioButtonId) {
                R.id.reasonVehicle -> "Produk tidak sesuai kebutuhan kendaraan"
                R.id.reasonWrong -> "Salah memilih produk"
                R.id.reasonQuantity -> "Ingin mengubah jumlah barang"
                R.id.reasonAccidental -> "Pesanan dibuat tanpa sengaja"
                R.id.reasonPrice -> "Menemukan harga yang lebih sesuai"
                R.id.reasonDelivery -> "Estimasi pengiriman terlalu lama"
                R.id.reasonOther -> other.text.toString().trim()
                else -> ""
            }
            if (selected.isBlank()) {
                if (reasonGroup.checkedRadioButtonId == R.id.reasonOther) other.error = "Tuliskan alasan pembatalan"
                else Toast.makeText(this, "Pilih alasan pembatalan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (OrderHistory.updateStatus(this, orderId, "Dibatalkan", selected)) {
                Toast.makeText(this, "Pesanan dibatalkan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        findViewById<Button>(R.id.btnKeepOrder).setOnClickListener { finish() }
    }
}
