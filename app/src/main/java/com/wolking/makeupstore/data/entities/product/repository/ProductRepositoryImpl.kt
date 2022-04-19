package com.wolking.makeupstore.data.entities.product.repository

import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.data.remote.Resource
import com.wolking.makeupstore.data.remote.service.ApiService
import com.wolking.makeupstore.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository() {
    override suspend fun getProducts(): Resource<List<Product>> {
        return withContext(Dispatchers.IO) {
            safeApiCall { apiService.getProducts() }
        }
    }
}