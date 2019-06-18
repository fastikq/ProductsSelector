package dudukov.andrey.productsselector.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dudukov.andrey.productsselector.R
import dudukov.andrey.productsselector.model.Product

class ProductsAdapterChecked(private val addProduct: (Product) -> Unit, private val deleteProduct: (Product) -> Unit) :
    RecyclerView.Adapter<ProductsAdapterChecked.ProductsCheckedViewHolder>() {

    private val products: MutableList<Product> = mutableListOf()

    class ProductsCheckedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: CardView = itemView.findViewById(R.id.item_card)
        val name: AppCompatTextView = itemView.findViewById(R.id.tv_name)
        val cost: AppCompatTextView = itemView.findViewById(R.id.tv_cost)
        val checked: AppCompatImageView = itemView.findViewById(R.id.img_checked)
    }

    fun swap(newProduct: MutableList<Product>){
        products.clear()
        products.addAll(newProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsCheckedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_checked, parent, false)
        return ProductsCheckedViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsCheckedViewHolder, position: Int) {
        val currentItem = products[position]
        val name = currentItem.name
        val cost = currentItem.cost
        holder.item.setOnClickListener {
            holder.checked.visibility = if (holder.checked.visibility == View.GONE) {
                addProduct(currentItem)
                View.VISIBLE
            }
            else {
                deleteProduct(currentItem)
                View.GONE
            }
        }
        holder.name.text = name
        holder.cost.text = cost.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }
}