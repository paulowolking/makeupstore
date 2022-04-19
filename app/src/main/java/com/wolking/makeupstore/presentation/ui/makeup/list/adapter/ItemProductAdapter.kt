package com.wolking.makeupstore.presentation.ui.makeup.list.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wolking.makeupstore.R
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.presentation.ui.makeup.list.listerner.ItemProductAdapterListener
import com.wolking.makeupstore.presentation.ui.makeup.utils.extensions.toMoneyReal

class ItemProductAdapter(
    private var context: Context,
    private var productData: MutableList<Product> = mutableListOf(),
    private val listenerProduct: ItemProductAdapterListener
) : RecyclerView.Adapter<ItemProductAdapter.ViewHolder>() {

    constructor(
        context: Context, listenerProduct: ItemProductAdapterListener
    ) : this(
        context, mutableListOf<Product>(), listenerProduct = listenerProduct
    )

    fun updateItemsList(items: List<Product>) {
        this.productData.clear()
        this.productData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view).apply {
            textViewTitle = view.findViewById(R.id.tv_title)
            textViewBrand = view.findViewById(R.id.tv_brand)
            textViewPrice = view.findViewById(R.id.tv_price)
            textViewDescription = view.findViewById(R.id.tv_description)
            imageViewPhoto = view.findViewById(R.id.iv_photo)
            ratingBar = view.findViewById(R.id.ratingBar)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productData[position]

        product.imageLink?.let {
            Glide.with(context)
                .load(product.imageLink)
                .apply(RequestOptions().centerInside())
                .fallback(ColorDrawable(ContextCompat.getColor(context, R.color.gray)))
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.gray)))
                .into(holder.imageViewPhoto)
        }

        holder.textViewTitle.text = product.name
        holder.textViewBrand.text = product.brand
        holder.textViewDescription.text = product.description
        holder.ratingBar.rating = product.rating?.toFloat() ?: 0.0F
        holder.textViewPrice.text = product.price.toMoneyReal()
    }

    override fun getItemCount(): Int {
        return productData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textViewTitle: TextView
        lateinit var textViewBrand: TextView
        lateinit var textViewPrice: TextView
        lateinit var textViewDescription: TextView
        lateinit var imageViewPhoto: ImageView
        lateinit var ratingBar: RatingBar

        init {
            itemView.setOnClickListener { listenerProduct.itemSelected(productData[adapterPosition]) }
        }
    }
}