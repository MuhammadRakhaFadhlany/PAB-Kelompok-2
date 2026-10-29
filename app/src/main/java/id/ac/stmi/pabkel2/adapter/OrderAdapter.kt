package id.ac.stmi.pabkel2.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import id.ac.stmi.pabkel2.OrderRecord
import id.ac.stmi.pabkel2.ProductCatalog
import id.ac.stmi.pabkel2.R
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(private val orders: List<OrderRecord>) : BaseAdapter() {
    private fun money(value: Int) = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(value).replace(",00", "")

    override fun getCount() = orders.size
    override fun getItem(position: Int) = orders[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_order_card, parent, false)
        val order = getItem(position)

        val firstLine = order.items.lines().firstOrNull().orEmpty()
        val productName = firstLine.substringBefore(" x").substringBefore(" =").trim().ifBlank { "Pesanan ${order.id}" }
        val matchedProduct = ProductCatalog.products.find { productName.contains(it.name, ignoreCase = true) || it.name.contains(productName, ignoreCase = true) }

        val imgView = view.findViewById<ImageView>(R.id.imgOrderProduct)
        if (matchedProduct != null && matchedProduct.image != 0) {
            imgView.setImageResource(matchedProduct.image)
        } else {
            imgView.setImageResource(R.drawable.busi_iridium)
        }

        view.findViewById<TextView>(R.id.tvOrderProductName).text = productName
        view.findViewById<TextView>(R.id.tvOrderDate).text = order.date

        val statusBadge = view.findViewById<TextView>(R.id.tvOrderStatusBadge)
        when (order.status) {
            "Dibatalkan" -> {
                statusBadge.text = "⊗ Pesanan dibatalkan"
                statusBadge.setTextColor(Color.parseColor("#F4511E"))
            }
            "Selesai", "Pesanan diterima" -> {
                statusBadge.text = "✓ ${order.status}"
                statusBadge.setTextColor(Color.parseColor("#388E3C"))
            }
            else -> {
                statusBadge.text = "⏱ ${order.status}"
                statusBadge.setTextColor(Color.parseColor("#F4511E"))
            }
        }

        view.findViewById<TextView>(R.id.tvOrderPrice).text = money(order.total)
        val itemCount = order.items.lines().filter { it.isNotBlank() }.size
        view.findViewById<TextView>(R.id.tvOrderCount).text = "$itemCount produk"

        return view
    }
}
