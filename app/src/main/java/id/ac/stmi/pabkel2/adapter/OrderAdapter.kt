package id.ac.stmi.pabkel2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import id.ac.stmi.pabkel2.OrderRecord
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(private val orders: List<OrderRecord>) : BaseAdapter() {
    override fun getCount(): Int = orders.size
    override fun getItem(position: Int): OrderRecord = orders[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val order = getItem(position)
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)
        text1.text = "${order.id} • ${order.status}"
        text1.setTextColor(0xFF40231C.toInt())
        text1.textSize = 15f
        val formattedTotal = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(order.total).replace(",00", "")
        text2.text = "${order.date}\n${order.items.replace("\n", ", ")}\nTotal: $formattedTotal"
        text2.setTextColor(0xFF887B74.toInt())
        text2.textSize = 13f
        return view
    }
}
