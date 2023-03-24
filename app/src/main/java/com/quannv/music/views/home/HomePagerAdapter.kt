package com.quannv.music.views.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quannv.music.views.song.AlbumFragment
import com.quannv.music.views.song.ArtistFragment
import com.quannv.music.views.song.SongFragment

class HomePagerAdapter constructor(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    private val listFragment = mutableListOf<Fragment>()

    init {
        listFragment.add(SONG, SongFragment.newInstance())
        listFragment.add(ALBUM, AlbumFragment.newInstance())
        listFragment.add(ARTIST, ArtistFragment.newInstance())
    }

    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }

    companion object {
        const val SONG = 0
        const val ALBUM = 1
        const val ARTIST = 2
    }
}