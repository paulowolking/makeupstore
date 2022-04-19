package com.wolking.makeupstore.presentation.ui.makeup.favorite.adapter

import com.wolking.makeupstore.domain.product.model.Product


interface ItemProductFavoriteAdapterListener {
    fun itemSelected(product: Product)
    fun itemFavorite(product: Product, position: Int)
}