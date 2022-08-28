package com.example.dlight.data.remoteSource

import com.example.dlight.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.GITHUB_TOKEN
        var chainRequest = chain.request()
        chainRequest =
            chainRequest.newBuilder().header("Authorization", "Bearer $token")
                .build()
        return chain.proceed(chainRequest)
    }
}