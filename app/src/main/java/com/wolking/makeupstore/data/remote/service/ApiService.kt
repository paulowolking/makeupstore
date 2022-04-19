package com.wolking.makeupstore.data.remote.service

import com.wolking.makeupstore.domain.product.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/products.json")
    suspend fun getProducts(): Response<List<Product>>
}