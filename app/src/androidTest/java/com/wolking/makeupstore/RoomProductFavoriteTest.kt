package com.wolking.makeupstore

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wolking.makeupstore.data.database.AppDatabase
import com.wolking.makeupstore.data.database.data_source.ProductFavoriteDao
import com.wolking.makeupstore.data.database.models.product.ProductFavoriteDB
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RoomProductFavoriteTest {
    private lateinit var productFavoriteDao: ProductFavoriteDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        productFavoriteDao = db.productFavoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertProductAndSearch() {
        val productId = 100
        val productFavoriteDB = ProductFavoriteDB(
            name = "Product",
            productId = productId,
            brand = "",
            price = 0.0,
            imageLink = "",
            description = "",
            category = "",
            type = ""
        )
        productFavoriteDao.insert(productFavoriteDB)
        val productInserted = productFavoriteDao.findById(productId)
        assert(productInserted != null)
    }

    @Test
    @Throws(Exception::class)
    fun insertProductAndRemove() {
        val productId = 100
        val productFavoriteDB = ProductFavoriteDB(
            name = "Product",
            productId = productId,
            brand = "",
            price = 0.0,
            imageLink = "",
            description = "",
            category = "",
            type = ""
        )
        productFavoriteDao.insert(productFavoriteDB)
        val productInserted = productFavoriteDao.findById(productId)
        productInserted?.let {
            productFavoriteDao.delete(productId = it.productId)
        }
        val productFind = productFavoriteDao.findById(productId)
        assert(productFind == null)
    }
}