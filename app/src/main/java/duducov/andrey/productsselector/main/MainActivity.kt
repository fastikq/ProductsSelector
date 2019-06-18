package duducov.andrey.productsselector.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import duducov.andrey.productsselector.products.ProductsActivity
import duducov.andrey.productsselector.main.adapter.ProductsAdapter
import duducov.andrey.productsselector.R
import duducov.andrey.productsselector.model.Product
import duducov.andrey.productsselector.main.touchhelper.SimpleItemTouchHelperCallback

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val productsList: MutableList<Product> = mutableListOf()
    private val adapter = ProductsAdapter(this::deleteProductFromSelectionList)
    private val tvSum: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.tv_sum) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_list)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDairy: MaterialButton = findViewById(R.id.btn_dairy)
        val buttonFruits: MaterialButton = findViewById(R.id.btn_fruits)
        buttonDairy.setOnClickListener(this)
        buttonFruits.setOnClickListener(this)
        recyclerView.adapter = adapter

        fillDataOfProducts()
        calculateCost()
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, ProductsActivity::class.java)
        when(v?.id){
            R.id.btn_dairy -> intent.putExtra("product_type", "Dairy")
            R.id.btn_fruits -> intent.putExtra("product_type", "Fruits")
        }
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 1) {
            val newProductsSerializable = data!!.getSerializableExtra("products_list") as List<*>
            for (p: Any? in newProductsSerializable) {
                if (p is Product) productsList.add(p)
            }
            fillDataOfProducts()
            calculateCost()
        }
    }

    private fun fillDataOfProducts() {
        adapter.swap(productsList)
        val callback: ItemTouchHelper.Callback =
            SimpleItemTouchHelperCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun calculateCost(){
        val allCost = productsList.sumBy { it.cost }
        val sum = StringBuilder()
        sum.append(getString(R.string.tv_sum))
        sum.append(" ")
        sum.append(allCost)
        sum.append(" ")
        sum.append(getString(R.string.uah))
        tvSum.text = sum
    }

    private fun deleteProductFromSelectionList(position: Int) {
        productsList.removeAt(position)
        calculateCost()
    }

}
