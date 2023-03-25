package com.quannv.music.views.song

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.quannv.music.bases.BaseFragment
import com.quannv.music.bases.OutcomeState
import com.quannv.music.databinding.FragmentSongBinding
import com.quannv.music.utilities.LogUtils
import com.quannv.music.utilities.observeEventUnhandled
import com.quannv.music.views.viewmodel.SongViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

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

    private val songViewModel: SongViewModel by activityViewModel()
    private var songAdapter: SongAdapter? = null

    override fun setupView() {
        super.setupView()
        setText()
        setAdapter()
    }

    override fun setupEventControl() {
        super.setupEventControl()
    }

    override fun bindData() {
        super.bindData()
    }

    override fun observeHandle() {
        super.observeHandle()
        songViewModel.songTokensResponse.observeEventUnhandled(this) { state ->
            when (state) {
                OutcomeState.Loading -> {
                    LogUtils.d("++++++++++GetAllMusic loading:")
                }
                is OutcomeState.Error -> {
                    LogUtils.d("++++++++++GetAllMusic failed:")
                }
                is OutcomeState.Success -> {
                    val result = state.result
                    LogUtils.d("++++++++++GetAllMusic success: $result")
                    songAdapter?.submitList(result)
                }
            }
        }
    }

    private fun setAdapter() {
        songAdapter = SongAdapter(requireContext(), onItemClick = {

        })
        binding.rvSong.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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