package com.ggasbarri.lastfm.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toDrawable
import coil.decode.DataSource
import coil.intercept.Interceptor
import coil.memory.MemoryCache
import coil.request.ErrorResult
import coil.request.ImageResult
import coil.request.SuccessResult
import com.ggasbarri.lastfm.BuildConfig
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.injection.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalImageInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {

        val data = chain.request.data
        if (data !is Album) return chain.proceed(chain.request)

        if(data.imageUrl == null) return ErrorResult(
            drawable = null,
            chain.request,
            throwable = KotlinNullPointerException("Album::imageUrl was null")
        )

        val imageFile = getFileFor(data.id.toString())

        if (!imageFile.exists()) return chain.proceed(chain.request)

        val filePath: String = imageFile.path
        val bitmap = BitmapFactory.decodeFile(filePath)

        return SuccessResult(
            drawable = bitmap.toDrawable(context.resources),
            request = chain.request,
            metadata = ImageResult.Metadata(
                memoryCacheKey = MemoryCache.Key(imageFile.name),
                isSampled = false,
                dataSource = DataSource.DISK,
                isPlaceholderMemoryCacheKeyPresent = chain.request.placeholderMemoryCacheKey != null,
            )
        )

    }

    suspend fun saveBitmap(bitmap: Bitmap, fileName: String) {
        val file = getFileFor(fileName)

        coroutineScope {
            launch(dispatcher) {
                runCatching {
                    context.getUriForFile(file)?.let {
                        context.contentResolver.openOutputStream(it)?.run {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
                            close()
                        }
                    }
                }
            }
        }
    }

    suspend fun deleteBitmap(fileName: String) {
        val file = getFileFor(fileName)

        coroutineScope {
            launch(dispatcher) {
                runCatching {
                    if (file.exists())
                        file.delete()
                }
            }
        }
    }

    private fun getFileFor(fileName: String) = File(
        context.filesDir.absolutePath,
        "${URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())}.png"
    )
}

private fun Context.getUriForFile(file: File): Uri? {
    return FileProvider.getUriForFile(
        this,
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        file
    )
}