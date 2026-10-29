package id.ac.stmi.pabkel2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import id.ac.stmi.pabkel2.R
import id.ac.stmi.pabkel2.manager.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val items: List<CartItem>) : BaseAdapter() {
    private fun money(value: Double) = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(value).replace(",00", "")

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = items[position].product.id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        val item = getItem(position)
        val product = item.product

        view.findViewById<ImageView>(R.id.imgCartProduct).setImageResource(
            if (product.image != 0) product.image else android.R.drawable.ic_menu_gallery
        )
        view.findViewById<TextView>(R.id.tvCartProductName).text = product.name
        view.findViewById<TextView>(R.id.tvCartProductInfo).text = "${item.quantity} unit x ${money(product.price)}"
        view.findViewById<TextView>(R.id.tvCartSubtotal).text = "Subtotal: ${money(item.subtotal)}"

        return view
    }
}
