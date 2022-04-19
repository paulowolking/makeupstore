package com.wolking.makeupstore.data.database.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wolking.makeupstore.data.database.models.product.ProductFavoriteDB

@Dao
interface ProductFavoriteDao {
    @Query("SELECT * FROM products")
    fun getAll(): List<ProductFavoriteDB>

    @Query("SELECT * FROM products WHERE product_id IN (:productIds)")
    fun loadAllByIds(productIds: IntArray): List<ProductFavoriteDB>

    @Query("SELECT * FROM products WHERE product_id LIKE :productId  LIMIT 1")
    fun findById(productId: Int): ProductFavoriteDB?

    @Insert
    fun insert(product: ProductFavoriteDB)

    @Insert
    fun insertAll(vararg products: ProductFavoriteDB)

    @Query("DELETE FROM products WHERE product_id = :productId")
    fun delete(productId: Int)
}