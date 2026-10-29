package id.ac.stmi.pabkel2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import id.ac.stmi.pabkel2.manager.CartItem
import java.text.NumberFormat
import java.util.Locale

class CheckoutAdapter(private val items: List<CartItem>) : BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(position: Int): CartItem = items[position]
    override fun getItemId(position: Int): Long = items[position].product.id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val item = getItem(position)
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)
        text1.text = item.product.name
        text1.setTextColor(0xFF40231C.toInt())
        val formattedSubtotal = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(item.subtotal).replace(",00", "")
        text2.text = "${item.quantity} x ${item.product.code} = $formattedSubtotal"
        text2.setTextColor(0xFF887B74.toInt())
        return view
    }
}
