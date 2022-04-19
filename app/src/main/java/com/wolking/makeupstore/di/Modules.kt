package com.wolking.makeupstore.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import com.wolking.makeupstore.BuildConfig
import com.wolking.makeupstore.data.database.AppDatabase
import com.wolking.makeupstore.data.database.data_source.ProductFavoriteDao
import com.wolking.makeupstore.data.database.models.product.repository.ProductFavoriteRepositoryImpl
import com.wolking.makeupstore.data.entities.product.repository.ProductRepositoryImpl
import com.wolking.makeupstore.data.remote.ApiInterceptor
import com.wolking.makeupstore.data.remote.service.ApiService
import com.wolking.makeupstore.data.remote.utils.BooleanTypeAdapter
import com.wolking.makeupstore.domain.product.repository.ProductRepository
import com.wolking.makeupstore.domain.product_favorite.repository.ProductFavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "http://makeup-api.herokuapp.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(ApiInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTypeAdapter())
                .registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
                .create()
        )

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "makeupdatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesProductRepository(apiService: ApiService): ProductRepository =
        ProductRepositoryImpl(apiService)

    @Provides
    fun providesProductFavoriteRepository(appDatabase: AppDatabase): ProductFavoriteRepository =
        ProductFavoriteRepositoryImpl(appDatabase)
}