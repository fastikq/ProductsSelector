package duducov.andrey.productsselector.products

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import duducov.andrey.productsselector.products.adapter.ProductsAdapterChecked
import duducov.andrey.productsselector.R
import duducov.andrey.productsselector.model.Product
import java.io.Serializable

class ProductsActivity : AppCompatActivity(), View.OnClickListener{

    private val selectionList: MutableList<Product> = mutableListOf()
    private val adapter = ProductsAdapterChecked(
        this::addProductToSelectionList,
        this::deleteProductFromSelectionList
    )
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_list) }
    private val btnConfirm: MaterialButton by lazy { findViewById<MaterialButton>(R.id.btn_confirm) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val title = intent.getStringExtra("product_type")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = title
        setSupportActionBar(toolbar)
        btnConfirm.setOnClickListener(this)
        fillDataOfProducts()
    }

    private fun fillDataOfProducts() {
        recyclerView.adapter = adapter
        val typeProduct = intent.getStringExtra("product_type")
        adapter.swap(getListProducts(typeProduct))
    }

    private fun getListProducts(typeProducts: String) =
        if(typeProducts == "Dairy") mutableListOf(
            Product("Milk", 120),
            Product("Yogurt", 200),
            Product("Sour cream", 355),
            Product("Cottage cheese", 700),
            Product("Kefir", 105),
            Product("Cheese", 475),
            Product("Cream Cheese", 210)
        )
        else  mutableListOf(
            Product("Apple", 70),
            Product("Orange", 135),
            Product("Banana", 235),
            Product("Kiwi", 145),
            Product("Cherry", 300),
            Product("Lemon", 265),
            Product("Mandarin", 400),
            Product("Grapefruit", 500)

        )

    private fun deleteProductFromSelectionList(product: Product) {
        selectionList.remove(product)
        updateVisibilityButtonConfirm()
    }

    private fun addProductToSelectionList(product: Product) {
        selectionList.add(product)
        updateVisibilityButtonConfirm()
    }

    private fun updateVisibilityButtonConfirm(){
        btnConfirm.visibility = if(selectionList.size > 0) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_confirm ->{
                val data = Intent()
                data.putExtra("products_list", selectionList as Serializable)
                setResult(1, data)
                finish()
            }
        }
    }
}
