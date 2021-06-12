package com.ggasbarri.lastfm.api.authentication

import com.ggasbarri.lastfm.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        // This key was securely hid using https://github.com/google/secrets-gradle-plugin
        // and removed from Version Control
        val apiKey = BuildConfig.LAST_FM_API_KEY

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .addQueryParameter("format", "json")
            .build()

        val request: Request = original
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}