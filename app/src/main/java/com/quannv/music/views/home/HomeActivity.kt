package com.quannv.music.views.home

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.quannv.music.R
import com.quannv.music.bases.BaseActivity
import com.quannv.music.bases.OutcomeState
import com.quannv.music.databinding.ActivityHomeBinding
import com.quannv.music.utilities.LogUtils
import com.quannv.music.utilities.clickWithDebounce
import com.quannv.music.utilities.observeEventUnhandled
import com.quannv.music.utilities.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    companion object {
        private const val MY_REQUEST_PERMISSION = 10
    }

    private val songViewModel: SongViewModel by viewModel()
    private lateinit var navListBtn: List<View>
    private lateinit var navListText: List<TextView>
    private lateinit var navListLine: List<View>
    private var homePagerAdapter: HomePagerAdapter? = null

    override fun setupView() {
        super.setupView()
        setText()
        setAdapter()
    }

    override fun setupEventControl() {
        super.setupEventControl()
        navListBtn = listOf(
            binding.btnSong,
            binding.btnAlbum,
            binding.btnArtist
        )
        navListText = listOf(
            binding.tvSong,
            binding.tvAlbum,
            binding.tvArtist
        )
        navListLine = listOf(
            binding.lineSong,
            binding.lineAlbum,
            binding.lineArtist
        )
        navListBtn.forEachIndexed { index, view ->
            view.clickWithDebounce {
                updateCurrentTab(index)
            }
        }
        binding.imgMenu.clickWithDebounce {

        }
    }

    private fun updateCurrentTab(index: Int) {
        //line
        navListLine.forEach {
            it.visibility = View.INVISIBLE
        }
        navListLine[index].visibility = View.VISIBLE
        //text
        navListText.forEach {
            it.setTextColor(getColor(R.color.text_gray_color))
        }
        navListText[index].setTextColor(getColor(R.color.text_white_color))
        binding.viewPager.setCurrentItem(index, true)
    }

    override fun observeHandle() {
        super.observeHandle()
        //cách 1
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
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun setText() {

    }

    private fun setAdapter() {
        homePagerAdapter = HomePagerAdapter(this)
        binding.viewPager.apply {
            offscreenPageLimit = 3
            adapter = homePagerAdapter
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateCurrentTab(position)
                }
            })
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
            songViewModel.getAllMusicFromDevice()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                songViewModel.getAllMusicFromDevice()
            } else {
                toast("Bạn cần cấp quyền truy cập bộ nhớ")
            }
        }
    }

    override fun getViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onLanguageChanged() {
        super.onLanguageChanged()
        setText()
    }
}