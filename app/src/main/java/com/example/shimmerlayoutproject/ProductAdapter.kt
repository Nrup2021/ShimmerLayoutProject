package com.example.shimmerlayoutproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shimmerlayoutproject.databinding.RowProductListItemBinding

class ProductAdapter(
    private val productList: ArrayList<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    var showShimmer: Boolean = true

    inner class ProductViewHolder(val binding: RowProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            RowProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }


    override fun getItemCount(): Int = when {
        showShimmer -> 10
        else -> productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        with(holder) {
            with(productList[position]) {
                with(binding) {
                    if (showShimmer) {
                        shimmerLayout.startShimmer()
                    } else {
                        shimmerLayout.stopShimmer()
                        shimmerLayout.setShimmer(null)

                        tvProductName.background = null
                        tvProductDescription.background = null
                        tvProductPrice.background = null
                        ivProductImage.background = null


                        tvProductName.text = product.name
                        tvProductDescription.text = product.description
                        tvProductPrice.text = product.price.toString()

                        Glide.with(ivProductImage)
                            .load(product.url)
                            .into(ivProductImage)
                    }

                }
            }
        }
    }

    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }

}