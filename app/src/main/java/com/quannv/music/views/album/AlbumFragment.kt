package com.quannv.music.views.album

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.quannv.music.bases.BaseFragment
import com.quannv.music.bases.OutcomeState
import com.quannv.music.customizes.ItemSpaceDecoration
import com.quannv.music.databinding.FragmentAlbumBinding
import com.quannv.music.utilities.LogUtils
import com.quannv.music.utilities.dpToPx
import com.quannv.music.utilities.observeEventUnhandled
import com.quannv.music.viewmodel.AlbumViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    private val albumViewModel: AlbumViewModel by activityViewModel()
    private var albumAdapter: AlbumAdapter? = null
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
        albumViewModel.getAlbumsResponse.observeEventUnhandled(this) { state ->
            when (state) {
                OutcomeState.Loading -> {}
                is OutcomeState.Error -> {
                    LogUtils.e("++++++++++GetAllAlbum failed:")
                }
                is OutcomeState.Success -> {
                    val result = state.result
                    LogUtils.d("++++++++++GetAllAlbum success: $result")
                    albumAdapter?.submitList(result)
                }
            }
        }
    }

    private fun setAdapter() {
        albumAdapter = AlbumAdapter(requireContext(), onItemClick = {

        })
        binding.rvAlbum.apply {
            adapter = albumAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(
                ItemSpaceDecoration(
                    16.dpToPx(this.context),
                    16.dpToPx(this.context),
                    2,
                    false
                )
            )
        }
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