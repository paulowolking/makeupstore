package com.wolking.makeupstore.presentation.ui.makeup.list.listerner

import com.wolking.makeupstore.domain.product.model.Product


interface ItemProductAdapterListener {
    fun itemSelected(product: Product)
}