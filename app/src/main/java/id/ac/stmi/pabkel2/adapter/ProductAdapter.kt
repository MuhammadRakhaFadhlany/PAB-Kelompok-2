package id.ac.stmi.pabkel2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import id.ac.stmi.pabkel2.R
import id.ac.stmi.pabkel2.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val products: List<Product>, private val onDetailClick: (Product) -> Unit) : BaseAdapter() {
    override fun getCount() = products.size
    override fun getItem(position: Int) = products[position]
    override fun getItemId(position: Int) = products[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        val product = getItem(position)
        view.findViewById<TextView>(R.id.tvProductName).text = product.name
        view.findViewById<TextView>(R.id.tvProductCode).text = "${product.code} | ${product.category} | Stok ${product.stock}"
        view.findViewById<TextView>(R.id.tvProductPrice).text = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(product.price).replace(",00", "")
        view.findViewById<ImageView>(R.id.imgProduct).setImageResource(if (product.image != 0) product.image else android.R.drawable.ic_menu_gallery)
        view.findViewById<Button>(R.id.btnDetail).setOnClickListener { onDetailClick(product) }
        return view
    }
}
