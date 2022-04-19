package com.wolking.makeupstore.data.database.models.product.repository

import com.wolking.makeupstore.data.database.AppDatabase
import com.wolking.makeupstore.data.database.models.product.ProductFavoriteDB
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.domain.product_favorite.repository.ProductFavoriteRepository

class ProductFavoriteRepositoryImpl(
    private val appDatabase: AppDatabase
) : ProductFavoriteRepository() {
    override suspend fun getProducts(): List<Product> {
        val products = ArrayList<Product>()
        val productsDb = appDatabase.productFavoriteDao().getAll()
        productsDb.forEach {
            products.add(it.toDomain())
        }
        return products
    }

    override suspend fun getProductById(productId: Int): Product? {
        return appDatabase.productFavoriteDao().findById(productId)?.toDomain()
    }

    override suspend fun favorite(product: Product) {
        val favoriteDb = getProductById(productId = product.id)
        if (favoriteDb == null) {
            val productFavorite = ProductFavoriteDB(
                productId = product.id,
                name = product.name,
                brand = product.brand,
                price = product.price,
                imageLink = product.imageLink,
                description = product.description,
                category = product.category,
                type = product.productType.toString()
            )
            appDatabase.productFavoriteDao().insert(productFavorite)
        }
    }

    override suspend fun delete(product: Product) {
        appDatabase.productFavoriteDao().delete(product.id)
    }
}