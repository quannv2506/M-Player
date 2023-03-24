package com.quannv.music.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.quannv.music.models.Album
import com.quannv.music.models.Artist
import io.reactivex.Observable

class ArtistRepository(private val contentResolver: ContentResolver) {

    private fun convertToArtist(cursor: Cursor): Artist {
        val artist = Artist()
        artist.id = cursor.getLong(0)
        artist.artist = cursor.getString(1)
        artist.numberOfSongs = cursor.getInt(2)
        artist.numberOfSongs = cursor.getInt(3)
        return artist
    }

    fun loadArtists(): Observable<MutableList<Artist>> {
        return Observable.fromCallable {
            val artists = mutableListOf<Artist>()
            val auri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
            )
            val cursor = contentResolver.query(
                auri,
                projection,
                null,
                null,
                MediaStore.Audio.Artists.DEFAULT_SORT_ORDER
            )
            while (cursor?.moveToNext() == true) {
                val artist = convertToArtist(cursor)
                artists.add(artist)
            }
            artists
        }
    }
}