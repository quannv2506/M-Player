package com.quannv.music.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.quannv.music.models.Album
import io.reactivex.Observable

class AlbumRepository(private val contentResolver: ContentResolver) {

    private fun convertToAlbum(cursor: Cursor): Album {
        val album = Album()
        album.id = cursor.getLong(0)
        album.album = cursor.getString(1)
        album.artist = cursor.getString(2)
        album.numberOfSongs = cursor.getInt(3)
        return album
    }

    fun loadAlbums(): Observable<MutableList<Album>> {
        return Observable.fromCallable {
            val albums = mutableListOf<Album>()
            val mUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            )
            val cursor = contentResolver.query(
                mUri,
                projection,
                null,
                null,
                MediaStore.Audio.Albums.DEFAULT_SORT_ORDER
            )
            while (cursor?.moveToNext() == true) {
                val song = convertToAlbum(cursor)
                albums.add(song)
            }
            albums
        }
    }
}