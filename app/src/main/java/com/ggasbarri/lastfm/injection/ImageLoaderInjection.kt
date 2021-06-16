package com.ggasbarri.lastfm.injection

import android.content.Context
import coil.ImageLoader
import com.ggasbarri.lastfm.image.AlbumCoilMapper
import com.ggasbarri.lastfm.image.LocalImageInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderInjection {
    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext applicationContext: Context,
        localImageInterceptor: LocalImageInterceptor,
        albumCoilMapper: AlbumCoilMapper,
    ): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .allowHardware(false)
            .componentRegistry {
                add(localImageInterceptor)
                add(albumCoilMapper)
            }
            .build()
    }
}