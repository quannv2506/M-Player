package com.quannv.music.views.song

import android.os.Bundle
import android.view.LayoutInflater
import com.quannv.music.bases.BaseFragment
import com.quannv.music.databinding.FragmentAlbumBinding
import com.quannv.music.databinding.FragmentArtistBinding
import com.quannv.music.databinding.FragmentSongBinding

class ArtistFragment : BaseFragment<FragmentArtistBinding>() {

    companion object {
        fun newInstance(): ArtistFragment {
            val args = Bundle().apply {
            }
            return ArtistFragment().apply {
                arguments = args
            }
        }
    }

    override fun setupView() {
        super.setupView()
        setText()
    }

    override fun setupEventControl() {
        super.setupEventControl()
    }

    override fun bindData() {
        super.bindData()
    }

    override fun observeHandle() {
        super.observeHandle()
    }

    private fun setText() {

    }

    override fun onLanguageChanged() {
        super.onLanguageChanged()
        setText()
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentArtistBinding {
        return FragmentArtistBinding.inflate(inflater)
    }
}