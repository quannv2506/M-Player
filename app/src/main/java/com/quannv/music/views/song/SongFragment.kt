package com.quannv.music.views.song

import android.os.Bundle
import android.view.LayoutInflater
import com.quannv.music.bases.BaseFragment
import com.quannv.music.databinding.FragmentSongBinding

class SongFragment : BaseFragment<FragmentSongBinding>() {

    companion object {
        fun newInstance(): SongFragment {
            val args = Bundle().apply {
            }
            return SongFragment().apply {
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

    override fun getViewBinding(inflater: LayoutInflater): FragmentSongBinding {
        return FragmentSongBinding.inflate(inflater)
    }
}