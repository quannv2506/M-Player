package com.quannv.music.views.song

import android.os.Bundle
import android.view.LayoutInflater
import com.quannv.music.bases.BaseFragment
import com.quannv.music.databinding.FragmentAlbumBinding
import com.quannv.music.databinding.FragmentSongBinding

class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    companion object {
        fun newInstance(): AlbumFragment {
            val args = Bundle().apply {
            }
            return AlbumFragment().apply {
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

    override fun getViewBinding(inflater: LayoutInflater): FragmentAlbumBinding {
        return FragmentAlbumBinding.inflate(inflater)
    }
}