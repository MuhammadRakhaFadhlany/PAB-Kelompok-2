package id.ac.stmi.pabkel2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import id.ac.stmi.pabkel2.R
import id.ac.stmi.pabkel2.manager.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val items: List<CartItem>) : BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(position: Int): CartItem = items[position]
    override fun getItemId(position: Int): Long = items[position].product.id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        val item = getItem(position)
        view.findViewById<TextView>(R.id.tvProductName).text = item.product.name
        view.findViewById<TextView>(R.id.tvProductCode).text = "${item.product.code} | Jumlah: ${item.quantity} unit"
        view.findViewById<TextView>(R.id.tvProductPrice).text = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(item.subtotal).replace(",00", "")
        view.findViewById<ImageView>(R.id.imgProduct).setImageResource(if (item.product.image != 0) item.product.image else android.R.drawable.ic_menu_gallery)
        view.findViewById<Button>(R.id.btnDetail).visibility = View.GONE
        return view
    }
}
