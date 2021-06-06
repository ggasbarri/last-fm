package com.ggasbarri.lastfm.injection

import com.ggasbarri.lastfm.api.LAST_FM_BASE_URL
import com.ggasbarri.lastfm.api.LastFmDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLastFmDatasource(
        // Potential dependencies of this type
    ): LastFmDatasource {
        return Retrofit.Builder()
            .baseUrl(LAST_FM_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(LastFmDatasource::class.java)
    }

}