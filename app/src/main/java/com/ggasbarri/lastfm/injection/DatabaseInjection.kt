package com.ggasbarri.lastfm.injection

import android.content.Context
import androidx.room.Room
import com.ggasbarri.lastfm.db.AppDatabase
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.dao.ArtistsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideArtistsDao(appDatabase: AppDatabase): ArtistsDao {
        return appDatabase.artistsDao()
    }

    @Provides
    fun provideAlbumsDao(appDatabase: AppDatabase): AlbumsDao {
        return appDatabase.albumsDao()
    }
}