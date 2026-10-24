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

class ProductAdapter(
    private val products: List<Product>,
    private val onDetailClick: (Product) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = products.size

    override fun getItem(position: Int): Product {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return products[position].id.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        val product = getItem(position)

        val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
        val tvProductName = view.findViewById<TextView>(R.id.tvProductName)
        val tvProductCode = view.findViewById<TextView>(R.id.tvProductCode)
        val tvProductPrice = view.findViewById<TextView>(R.id.tvProductPrice)
        val btnDetail = view.findViewById<Button>(R.id.btnDetail)

        tvProductName.text = product.name
        tvProductCode.text = product.code

        val formatter = NumberFormat.getCurrencyInstance(
            Locale("id", "ID")
        )

        tvProductPrice.text = formatter.format(product.price)

        if (product.image != 0) {
            imgProduct.setImageResource(product.image)
        } else {
            imgProduct.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }

        btnDetail.setOnClickListener {
            onDetailClick(product)
        }

        return view
    }
}
