package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FaqActivity : AppCompatActivity() {
    private data class Faq(val category: String, val question: String, val answer: String)
    private val entries = listOf(
        Faq("Umum", "Bagaimana memilih suku cadang?", "Periksa merek, model, tahun, dan kode komponen kendaraan. Hubungi tim Geara jika Anda membutuhkan bantuan memilih produk."),
        Faq("Umum", "Bagaimana melacak pesanan?", "Buka menu Riwayat Transaksi, pilih pesanan, lalu tekan Lacak Pesanan untuk melihat alamat dan tahap pengiriman."),
        Faq("Umum", "Apa pilihan pembayarannya?", "Anda dapat memilih Transfer Bank, E-Wallet, atau Bayar di Tempat (COD) saat checkout."),
        Faq("Umum", "Bisakah pesanan dibatalkan?", "Pesanan berstatus Pesanan Diterima atau Dikemas dapat dibatalkan dari menu Riwayat Transaksi. Pilih pesanan dan tuliskan alasannya."),
        Faq("Layanan", "Bagaimana mengajukan retur?", "Hubungi Pusat Bantuan dan sertakan nomor pesanan serta alasan retur agar tim Geara dapat membantu."),
        Faq("Layanan", "Bagaimana menghubungi Geara?", "Pilih Hubungi Kami untuk mengobrol dengan layanan pelanggan atau pilih WhatsApp, Facebook, dan Instagram pada halaman Pusat Bantuan."),
        Faq("Akun", "Bagaimana mengubah alamat pengiriman?", "Buka Profil, pilih Alamat Pengiriman, lalu simpan alamat terbaru. Anda juga dapat mengubahnya saat checkout."),
        Faq("Akun", "Bagaimana mengubah metode pembayaran?", "Buka Profil lalu pilih Metode Pembayaran. Pilihan tersebut akan digunakan sebagai pilihan awal saat checkout.")
    )
    private var category = "Umum"
    private var query = ""
    private val openQuestions = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        findViewById<Button>(R.id.btnChatTab).setOnClickListener { startActivity(Intent(this, SupportChatActivity::class.java)) }
        findViewById<Button>(R.id.btnFaqGeneral).setOnClickListener { selectCategory("Umum") }
        findViewById<Button>(R.id.btnFaqAccount).setOnClickListener { selectCategory("Akun") }
        findViewById<Button>(R.id.btnFaqService).setOnClickListener { selectCategory("Layanan") }
        findViewById<EditText>(R.id.etFaqSearch).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { query = s?.toString().orEmpty(); render() }
            override fun afterTextChanged(s: Editable?) = Unit
        })
        render()
    }

    private fun selectCategory(value: String) {
        category = value
        findViewById<Button>(R.id.btnFaqGeneral).backgroundTintList = getColorStateList(if (value == "Umum") R.color.geara_orange else R.color.geara_peach)
        findViewById<Button>(R.id.btnFaqAccount).backgroundTintList = getColorStateList(if (value == "Akun") R.color.geara_orange else R.color.geara_peach)
        findViewById<Button>(R.id.btnFaqService).backgroundTintList = getColorStateList(if (value == "Layanan") R.color.geara_orange else R.color.geara_peach)
        findViewById<Button>(R.id.btnFaqGeneral).setTextColor(getColor(if (value == "Umum") R.color.white else R.color.geara_orange))
        findViewById<Button>(R.id.btnFaqAccount).setTextColor(getColor(if (value == "Akun") R.color.white else R.color.geara_orange))
        findViewById<Button>(R.id.btnFaqService).setTextColor(getColor(if (value == "Layanan") R.color.white else R.color.geara_orange))
        render()
    }

    private fun render() {
        val container = findViewById<LinearLayout>(R.id.faqList)
        container.removeAllViews()
        val matching = entries.filter { (it.category == category || query.isNotBlank()) && (it.question + " " + it.answer).contains(query, true) && (query.isBlank() || it.category == category || it.question.contains(query, true) || it.answer.contains(query, true)) }
        matching.forEach { faq ->
            val question = TextView(this).apply {
                text = faq.question + if (faq.question in openQuestions) "     v" else "     >"
                textSize = 16f
                setTextColor(getColor(R.color.geara_brown))
                setPadding(2, 17, 2, 17)
                gravity = Gravity.CENTER_VERTICAL
                setOnClickListener {
                    if (!openQuestions.add(faq.question)) openQuestions.remove(faq.question)
                    render()
                }
            }
            container.addView(question, LinearLayout.LayoutParams(-1, -2))
            val divider = View(this).apply { setBackgroundColor(getColor(R.color.geara_border)) }
            container.addView(divider, LinearLayout.LayoutParams(-1, 1))
            if (faq.question in openQuestions) {
                val answer = TextView(this).apply {
                    text = faq.answer
                    textSize = 13f
                    setTextColor(getColor(R.color.geara_gray))
                    setPadding(0, 16, 0, 24)
                }
                container.addView(answer, LinearLayout.LayoutParams(-1, -2))
            }
        }
        if (matching.isEmpty()) {
            container.addView(TextView(this).apply { text = "Pertanyaan tidak ditemukan."; setTextColor(getColor(R.color.geara_gray)); setPadding(0, 20, 0, 20) })
        }
    }
}
