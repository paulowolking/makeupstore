package com.wolking.makeupstore.domain.product.repository

import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.data.remote.Resource
import com.wolking.makeupstore.data.remote.service.BaseApiResponse

abstract class ProductRepository : BaseApiResponse() {
    abstract suspend fun getProducts(): Resource<List<Product>>
}