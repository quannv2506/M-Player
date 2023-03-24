package com.quannv.music.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.quannv.music.models.Song
import io.reactivex.Observable

class SongRepository(private val contentResolver: ContentResolver) {

    private fun convertToSong(cursor: Cursor): Song {
        val song = Song()
        song.id = cursor.getString(0)
        song.albumId = cursor.getLong(1)
        song.artist = cursor.getString(2)
        song.title = cursor.getString(3)
        song.mData = cursor.getString(4)
        song.displayName = cursor.getString(5)
        song.duration = cursor.getString(6)
        song.isPlaying = false
        return song
    }

    fun loadSongs(): Observable<MutableList<Song>> {
        return Observable.fromCallable {
            val songs = mutableListOf<Song>()
            val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
            val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
            )
            val cursor = contentResolver.query(
                musicUri, projection, selection, null, null
            )
            while (cursor?.moveToNext() == true) {
                val song = convertToSong(cursor)
                songs.add(song)
            }
            songs
        }
    }
}