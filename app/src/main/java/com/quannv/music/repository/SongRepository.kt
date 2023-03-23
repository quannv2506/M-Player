package com.quannv.music.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.quannv.music.views.home.models.Song
import io.reactivex.Observable

class SongRepository(private val context: Context) {

    private fun convertToSong(cursor: Cursor): Song {
        val song = Song()
        song.id = cursor.getString(0)
        song.artist = cursor.getString(1)
        song.title = cursor.getString(2)
        song.mData = cursor.getString(3)
        song.displayName = cursor.getString(4)
        song.duration = cursor.getString(5)
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
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
            )
            val cursor = context.contentResolver.query(
                musicUri, projection, selection, null, null
            )
            while (cursor?.moveToNext() == true) {
                songs.add(convertToSong(cursor))
            }
            songs
        }
    }
}