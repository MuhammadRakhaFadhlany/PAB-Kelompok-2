package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        val id = intent.getStringExtra("ORDER_ID").orEmpty()
        val order = OrderHistory.get(this, id)
        if (order == null) { finish(); return }
        findViewById<TextView>(R.id.tvTrackingAddress).text = order.address
        findViewById<TextView>(R.id.tvTrackingOrder).text = "Pesanan ${order.id}"
        val stages = listOf("Pesanan Anda telah diterima", "Gudang menyiapkan suku cadang", "Kurir dalam perjalanan", "Pesanan Anda telah sampai")
        val current = when (order.status) {
            "Dikemas" -> 1
            "Dikirim" -> 2
            "Tiba" -> 3
            "Dibatalkan" -> -1
            else -> 0
        }
        val stageViews = listOf(R.id.tvStage1, R.id.tvStage2, R.id.tvStage3, R.id.tvStage4)
        val detailViews = listOf(R.id.tvStageDetail1, R.id.tvStageDetail2, R.id.tvStageDetail3, R.id.tvStageDetail4)
        stageViews.forEachIndexed { index, viewId ->
            findViewById<TextView>(viewId).text = stages[index]
            findViewById<TextView>(detailViews[index]).text = when {
                current == -1 -> if (index == 0) "Dibatalkan" else "Tidak dilanjutkan"
                index < current -> listOf("Diterima", "Dikemas", "Dikirim", "Tiba")[index]
                index == current -> listOf("Diterima", "Dikemas", "Dikirim", "Tiba")[index]
                else -> "Menunggu"
            }
        }
        findViewById<TextView>(R.id.tvTrackingEta).text = when (order.status) {
            "Tiba" -> "Pesanan telah sampai"
            "Dibatalkan" -> "Pesanan dibatalkan"
            "Dikirim" -> "Sedang dikirim"
            else -> "Estimasi tiba 1-3 hari"
        }
        findViewById<Button>(R.id.btnTrackingHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
        findViewById<Button>(R.id.btnTrackingReview).setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java).putExtra("ORDER_ID", order.id))
        }
    }
}
