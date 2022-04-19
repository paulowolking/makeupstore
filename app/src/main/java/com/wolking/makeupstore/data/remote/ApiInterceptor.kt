package com.wolking.makeupstore.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val urlBuilder = request.url.newBuilder()
        val requestBuilder = request.newBuilder()
            .method(request.method, request.body)
            .header("Accept", "application/json")
            .header("Content-type", "application/json")
        return chain.proceed(requestBuilder.url(urlBuilder.build()).build())
    }

}