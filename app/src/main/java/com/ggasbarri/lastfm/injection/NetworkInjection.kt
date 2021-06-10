package com.ggasbarri.lastfm.injection

import com.ggasbarri.lastfm.BuildConfig
import com.ggasbarri.lastfm.api.LAST_FM_BASE_URL
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.authentication.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLastFmDatasource(
        okHttpClient: OkHttpClient
    ): LastFmDatasource {
        return Retrofit.Builder()
            .baseUrl(LAST_FM_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(LastFmDatasource::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor()).let {
                if(BuildConfig.DEBUG) {
                    // Only add Logging for debug builds
                    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    it.addInterceptor(httpLoggingInterceptor)
                }
                else it
            }
            .build()
    }

}