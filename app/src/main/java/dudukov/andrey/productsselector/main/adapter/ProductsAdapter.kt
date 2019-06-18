package dudukov.andrey.productsselector.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import dudukov.andrey.productsselector.R
import dudukov.andrey.productsselector.model.Product
import dudukov.andrey.productsselector.main.touchhelper.ItemTouchHelperAdapter

class ProductsAdapter(private val deleteProduct: (position: Int) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(),
    ItemTouchHelperAdapter {

    private val products: MutableList<Product> = mutableListOf()

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: AppCompatTextView = itemView.findViewById(R.id.tv_name)
        val cost: AppCompatTextView = itemView.findViewById(R.id.tv_cost)
    }
    fun swap(newProduct: MutableList<Product>){
        products.clear()
        products.addAll(newProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {

        val currentItem = products[position]
        val name = currentItem.name
        val cost = currentItem.cost
        holder.name.text = name
        holder.cost.text = cost.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onItemDismiss(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
        deleteProduct(position)
    }
}