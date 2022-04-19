package com.wolking.makeupstore.presentation.ui.makeup.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wolking.makeupstore.R
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.presentation.ui.makeup.utils.extensions.toMoneyReal

class ItemProductFavoriteAdapter(
    private var context: Context,
    private var products: MutableList<Product> = mutableListOf(),
    private val listenerProduct: ItemProductFavoriteAdapterListener
) : RecyclerView.Adapter<ItemProductFavoriteAdapter.ViewHolder>() {

    constructor(
        context: Context, listenerProduct: ItemProductFavoriteAdapterListener
    ) : this(
        context, mutableListOf<Product>(), listenerProduct = listenerProduct
    )

    fun updateItemsList(items: List<Product>) {
        this.products.clear()
        this.products.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view).apply {
            textViewTitle = view.findViewById(R.id.tv_title)
            textViewPrice = view.findViewById(R.id.tv_price)
            textViewDescription = view.findViewById(R.id.tv_description)
            imageViewPhoto = view.findViewById(R.id.iv_photo)
            ratingBar = view.findViewById(R.id.ratingBar)
            imageViewFavorite = view.findViewById(R.id.iv_favorite)
            imageViewFavorite.isVisible = true
            imageViewFavorite.setOnClickListener {
                imageViewFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )

                listenerProduct.itemFavorite(
                    products[adapterPosition],
                    adapterPosition
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]


        product.imageLink?.let {
            Glide.with(context)
                .load(product.imageLink)
                .apply(RequestOptions().centerInside())
                .into(holder.imageViewPhoto)
        }

        holder.textViewTitle.text = product.name
        holder.textViewDescription.text = product.description
        holder.ratingBar.rating = product.rating?.toFloat() ?: 0.0F
        holder.textViewPrice.text = product.price.toMoneyReal()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textViewTitle: TextView
        lateinit var textViewPrice: TextView
        lateinit var textViewDescription: TextView
        lateinit var imageViewPhoto: ImageView
        lateinit var ratingBar: RatingBar
        lateinit var imageViewFavorite: ImageView

        init {
            itemView.setOnClickListener { listenerProduct.itemSelected(products[adapterPosition]) }
        }
    }
}