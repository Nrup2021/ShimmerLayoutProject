package com.example.shimmerlayoutproject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
        holder.binding.container.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.alpha_animation)
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
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(android.R.drawable.ic_dialog_alert)
                            .into(ivProductImage)
                    }

                    container.setOnClickListener { onItemClickListener?.let { it(product) } }
                }
            }
        }
    }

    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }

}