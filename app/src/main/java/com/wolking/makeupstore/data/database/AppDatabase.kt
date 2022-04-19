package com.wolking.makeupstore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wolking.makeupstore.data.database.data_source.ProductFavoriteDao
import com.wolking.makeupstore.data.database.models.product.ProductFavoriteDB

@Database(entities = [ProductFavoriteDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productFavoriteDao(): ProductFavoriteDao
}
