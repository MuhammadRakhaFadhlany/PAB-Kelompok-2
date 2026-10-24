package id.ac.stmi.pabkel2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.pabkel2.adapter.ProductAdapter
import id.ac.stmi.pabkel2.model.Product

class CatalogActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var spCategory: Spinner
    private lateinit var lvProducts: ListView

    private lateinit var productAdapter: ProductAdapter

    private val productList = mutableListOf<Product>()
    private var filteredProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        etSearch = findViewById(R.id.etSearch)
        spCategory = findViewById(R.id.spCategory)
        lvProducts = findViewById(R.id.lvProducts)

        loadProducts()
        setupCategorySpinner()
        setupProductList()
        setupSearch()
    }

    private fun loadProducts() {

        productList.add(
            Product(
                id = 1,
                name = "Oli Mesin",
                code = "OLI-001",
                category = "Mesin",
                price = 85000.0,
                compatibility = "Honda, Yamaha, Suzuki",
                description = "Oli mesin kendaraan bermotor."
            )
        )

        productList.add(
            Product(
                id = 2,
                name = "Filter Oli",
                code = "FLT-001",
                category = "Mesin",
                price = 45000.0,
                compatibility = "Toyota, Honda",
                description = "Filter oli untuk menjaga kebersihan oli mesin."
            )
        )

        productList.add(
            Product(
                id = 3,
                name = "Kampas Rem",
                code = "REM-001",
                category = "Rem",
                price = 120000.0,
                compatibility = "Toyota, Daihatsu",
                description = "Kampas rem kendaraan."
            )
        )

        productList.add(
            Product(
                id = 4,
                name = "Aki Kendaraan",
                code = "AKI-001",
                category = "Kelistrikan",
                price = 650000.0,
                compatibility = "Mobil dan motor",
                description = "Aki untuk kebutuhan kelistrikan kendaraan."
            )
        )

        filteredProducts.addAll(productList)
    }

    private fun setupCategorySpinner() {

        val categories = listOf(
            "Semua Kategori",
            "Mesin",
            "Rem",
            "Kelistrikan"
        )

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )

        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spCategory.adapter = spinnerAdapter

        spCategory.setOnItemSelectedListener(
            object : android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    filterProducts()
                }

                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {
                }
            }
        )
    }

    private fun setupProductList() {

        productAdapter = ProductAdapter(filteredProducts) { product ->

            val intent = Intent(
                this,
                DetailProductActivity::class.java
            )

            intent.putExtra("PRODUCT_ID", product.id)
            startActivity(intent)
        }

        lvProducts.adapter = productAdapter
    }

    private fun setupSearch() {

        etSearch.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    filterProducts()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )
    }

    private fun filterProducts() {

        val keyword = etSearch.text.toString()
            .trim()
            .lowercase()

        val selectedCategory =
            spCategory.selectedItem?.toString() ?: "Semua Kategori"

        filteredProducts.clear()

        filteredProducts.addAll(
            productList.filter { product ->

                val matchesKeyword =
                    product.name.lowercase().contains(keyword) ||
                            product.code.lowercase().contains(keyword) ||
                            product.compatibility.lowercase().contains(keyword)

                val matchesCategory =
                    selectedCategory == "Semua Kategori" ||
                            product.category == selectedCategory

                matchesKeyword && matchesCategory
            }
        )

        productAdapter.notifyDataSetChanged()
    }
}
