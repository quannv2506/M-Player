package com.project.mvvm.views.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.PermissionUtils
import com.project.mvvm.bases.BaseActivity
import com.project.mvvm.bases.OutcomeState
import com.project.mvvm.databinding.ActivitySplashBinding
import com.project.mvvm.db.entity.Token
import com.project.mvvm.utilities.EndlessRecyclerViewScrollListener
import com.project.mvvm.utilities.LogUtils
import com.project.mvvm.utilities.observeEventUnhandled
import com.project.mvvm.utilities.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivitySplashBinding>() {
    companion object{
        private const val MY_REQUEST_PERMISSION = 10
    }

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
        homeVM.songTokensResponse.observeEventUnhandled(this) { state ->
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
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    override fun setupEventControl() {
        super.setupEventControl()
        rootView.refreshLayout.setOnRefreshListener {
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

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_REQUEST_PERMISSION
            )
        } else {
            homeVM.getAllMusicFromDevice()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_PERMISSION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                homeVM.getAllMusicFromDevice()
            } else {
                toast("Bạn cần cấp quyền truy cập bộ nhớ")
            }
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