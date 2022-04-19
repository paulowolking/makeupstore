package com.wolking.makeupstore.data.database.models.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wolking.makeupstore.domain.product.model.Product

@Entity(tableName = "products")
data class ProductFavoriteDB(
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "brand") val brand: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "image_link") val imageLink: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "type") val type: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun toDomain(): Product {
        val product = Product()
        product.let {
            it.id = productId
            it.name = name
            it.brand = brand
            it.price = price ?: 0.0
            it.imageLink = imageLink
            it.description = description
            it.category = category
        }
        return product
    }
}