package com.ggasbarri.lastfm.image

import coil.map.Mapper
import com.ggasbarri.lastfm.db.models.Album
import javax.inject.Inject

class AlbumCoilMapper @Inject constructor() : Mapper<Album, String> {
    override fun map(data: Album) = data.imageUrl!!
}