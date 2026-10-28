package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.adapter.ProductAdapter
import id.ac.stmi.pabkel2.model.Product
import java.util.Locale

class CatalogActivity : AppCompatActivity() {
    private lateinit var search: EditText
    private lateinit var category: Spinner
    private lateinit var adapter: ProductAdapter
    private val filtered = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        search = findViewById(R.id.etSearch)
        category = findViewById(R.id.spCategory)
        val list = findViewById<ListView>(R.id.lvProducts)
        adapter = ProductAdapter(filtered) { product ->
            startActivity(Intent(this, DetailProductActivity::class.java).putExtra("PRODUCT_ID", product.id))
        }
        list.adapter = adapter
        category.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ProductCatalog.categories)
        val selectedCategory = intent.getStringExtra("CATEGORY")
        if (selectedCategory != null) {
            val index = ProductCatalog.categories.indexOf(selectedCategory)
            if (index >= 0) category.setSelection(index)
        }
        category.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) = Unit
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) = filter()
        }
        search.setText(intent.getStringExtra("SEARCH") ?: "")
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = filter()
            override fun afterTextChanged(s: Editable?) = Unit
        })
        findViewById<Button>(R.id.btnGoCart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
    }

    private fun filter() {
        val q = search.text.toString().lowercase(Locale.ROOT)
        val selected = category.selectedItem?.toString() ?: "Semua Kategori"
        filtered.clear()
        filtered.addAll(ProductCatalog.products.filter {
            (it.name + " " + it.code + " " + it.compatibility).lowercase(Locale.ROOT).contains(q) &&
                (selected == "Semua Kategori" || it.category == selected)
        })
        adapter.notifyDataSetChanged()
    }
}
