package com.wolking.makeupstore.domain.product_favorite.repository

import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.data.remote.service.BaseApiResponse

abstract class ProductFavoriteRepository : BaseApiResponse() {
    abstract suspend fun getProducts(): List<Product>

    abstract suspend fun getProductById(productId: Int): Product?

    abstract suspend fun favorite(product: Product)

    abstract suspend fun delete(product: Product)
}