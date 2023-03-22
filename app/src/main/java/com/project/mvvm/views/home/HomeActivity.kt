package com.project.mvvm.views.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mvvm.bases.BaseActivity
import com.project.mvvm.bases.OutcomeState
import com.project.mvvm.databinding.ActivitySplashBinding
import com.project.mvvm.db.entity.Token
import com.project.mvvm.utilities.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivitySplashBinding>() {

    private val homeVM: HomeVM by viewModel()
    private var tokenAdapter: TokenAdapter? = null
    private var allTokens: List<Token> = emptyList()
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var page = 1

    override fun setupView() {
        super.setupView()
        setText()
        setAdapter()
    }

    override fun observeHandle() {
        super.observeHandle()
        //cách 1
        homeVM.getTokensState.observeEventUnhandled(this) { state ->
            when (state) {
                OutcomeState.Loading -> {
                    LogUtils.d("++++++++++Api loading:")
                }
                is OutcomeState.Error -> {
                    LogUtils.d("++++++++++Api failed:")
                }
                is OutcomeState.Success -> {
                    LogUtils.d("++++++++++Api success:")
                    val result = state.result
                    tokenAdapter?.submitList(result.subList(0, 20))
                }
            }
        }
        //cách 2
        /*homeVM.getTokensState.observe(this, EventObserver { state ->
            when (state) {
                OutcomeState.Loading -> {
                    LogUtils.d("++++++++++Api loading:")
                }
                is OutcomeState.Error -> {
                    LogUtils.d("++++++++++Api failed:")
                }
                is OutcomeState.Success -> {
                    LogUtils.d("++++++++++Api success:")
                    val result = state.result
                    tokenAdapter?.submitList(result.subList(0, 20))
                }
            }
        })*/

        homeVM.getList()
    }


    override fun setupEventControl() {
        super.setupEventControl()
        rootView.refreshLayout.setOnRefreshListener {
            homeVM.getList()
        }
    }

    private fun setText() {

    }

    private fun setAdapter() {
        tokenAdapter = TokenAdapter(this, onItemClick = {
            toast(it.name)
        })
        val mLayoutManager = LinearLayoutManager(this)
        scrollListener = object : EndlessRecyclerViewScrollListener(mLayoutManager) {
            override fun onLoadMore(totalItemCount: Int, view: RecyclerView?) {
                if ((tokenAdapter?.getList()?.size ?: 0) < allTokens.size) {
                    val startIndex = tokenAdapter?.getList()?.size ?: 0
                    var endIndex = startIndex.plus(20)
                    if (allTokens.size - endIndex < 20)
                        endIndex = allTokens.size
                    val listResultToken =
                        tokenAdapter?.getList()?.plus(allTokens.subList(startIndex, endIndex))
                    tokenAdapter?.submitList(listResultToken)
                    scrollListener?.onLoadDone()
                    page++
                }
            }
        }
        rootView.rvToken.apply {
            adapter = tokenAdapter
            layoutManager = mLayoutManager
            addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        }
    }

    override fun getViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onLanguageChanged() {
        super.onLanguageChanged()
        setText()
    }
}