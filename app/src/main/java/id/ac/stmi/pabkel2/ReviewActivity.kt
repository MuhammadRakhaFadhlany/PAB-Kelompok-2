package id.ac.stmi.pabkel2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReviewActivity : AppCompatActivity() {
    private lateinit var orderId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        orderId = intent.getStringExtra("ORDER_ID").orEmpty()
        val order = OrderHistory.get(this, orderId)
        if (order == null) { finish(); return }
        findViewById<TextView>(R.id.tvReviewProduct).text = order.items.substringBefore("\n").substringBefore(" =")
        findViewById<RatingBar>(R.id.rbReviewRating).rating = if (order.rating > 0) order.rating else 5f
        findViewById<EditText>(R.id.etReviewText).setText(order.review)
        findViewById<Button>(R.id.btnCancelReview).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnSendReview).setOnClickListener {
            val rating = findViewById<RatingBar>(R.id.rbReviewRating).rating
            if (rating < 1f) {
                Toast.makeText(this, "Pilih nilai bintang terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val comment = findViewById<EditText>(R.id.etReviewText).text.toString()
            if (OrderHistory.saveReview(this, orderId, rating, comment)) {
                Toast.makeText(this, "Ulasan berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
