package com.quannv.music.views.artist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.quannv.music.bases.BaseFragment
import com.quannv.music.bases.OutcomeState
import com.quannv.music.customizes.ItemSpaceDecoration
import com.quannv.music.databinding.FragmentArtistBinding
import com.quannv.music.utilities.LogUtils
import com.quannv.music.utilities.dpToPx
import com.quannv.music.utilities.observeEventUnhandled
import com.quannv.music.views.viewmodel.ArtistViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    private val artistViewModel: ArtistViewModel by activityViewModel()
    private var artistAdapter: ArtistAdapter? = null
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
        artistViewModel.getArtistsResponse.observeEventUnhandled(this){state ->
            when(state){
                OutcomeState.Loading -> {}
                is OutcomeState.Error -> {
                    LogUtils.e("++++++++++GetAllArtist failed:")
                }
                is OutcomeState.Success -> {
                    val result = state.result
                    LogUtils.d("++++++++++GetAllArtist success: $result")
                    artistAdapter?.submitList(result)
                }
            }
        }
    }

    private fun setAdapter() {
        artistAdapter = ArtistAdapter(requireContext(), onItemClick = {

        })
        binding.rvArtist.apply {
            adapter = artistAdapter
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

    override fun getViewBinding(inflater: LayoutInflater): FragmentArtistBinding {
        return FragmentArtistBinding.inflate(inflater)
    }
}