package com.wolking.makeupstore

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

import org.junit.Test

import com.google.gson.Gson
import com.wolking.makeupstore.data.entities.product.repository.ProductRepositoryImpl
import com.wolking.makeupstore.data.remote.Resource
import com.wolking.makeupstore.data.remote.service.ApiService
import com.wolking.makeupstore.mocks.utils.MockResponseFileReader
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiMockServerTest {
    private val mockWebServer = MockWebServer()
    private val port = 8000

    lateinit var apiService: ApiService
    lateinit var productRepositoryImpl: ProductRepositoryImpl

    @Before
    fun init() {
        mockWebServer.start(port)
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiService::class.java)

        productRepositoryImpl = ProductRepositoryImpl(apiService)
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessfulProductResponse() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(MockResponseFileReader("products.json").content)
            }
        }

        when (val response = productRepositoryImpl.getProducts()) {
            is Resource.Success -> {
                assert(response.data.isNotEmpty())
            }
            is Resource.Error -> {
                assert(false)
            }
            else -> {}
        }
    }
}