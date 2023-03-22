package com.project.mvvm.views.home

import android.os.Bundle
import android.view.LayoutInflater
import com.project.mvvm.bases.BaseFragment
import com.project.mvvm.databinding.FragmentDetailBinding
import com.project.mvvm.repository.TokenRepository
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val homeVM: HomeVM by viewModel<HomeVM>()

    private val repository: TokenRepository by inject()

    override fun setupView() {
        super.setupView()
        setText()
    }

    override fun observeHandle() {
        super.observeHandle()

    }

    private fun setText() {

    }

    override fun setupEventControl() {
        super.setupEventControl()
    }

    override fun onLanguageChanged() {
        super.onLanguageChanged()
        setText()
    }

    companion object {
        fun newInstance(): DetailFragment {
            val args = Bundle()
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }
}