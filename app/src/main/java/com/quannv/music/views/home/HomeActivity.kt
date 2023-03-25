package com.quannv.music.views.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.quannv.music.R
import com.quannv.music.bases.BaseActivity
import com.quannv.music.databinding.ActivityHomeBinding
import com.quannv.music.utilities.clickWithDebounce
import com.quannv.music.utilities.toast
import com.quannv.music.views.viewmodel.AlbumViewModel
import com.quannv.music.views.viewmodel.ArtistViewModel
import com.quannv.music.views.viewmodel.SongViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    companion object {
        private const val MY_REQUEST_PERMISSION = 10
    }

    private val songViewModel: SongViewModel by viewModel()
    private val albumViewModel: AlbumViewModel by viewModel()
    private val artistViewModel: ArtistViewModel by viewModel()
    private lateinit var navListBtn: List<View>
    private lateinit var navListText: List<TextView>
    private lateinit var navListLine: List<View>
    private var homePagerAdapter: HomePagerAdapter? = null

    override fun setupView() {
        super.setupView()
        setText()
        setAdapter()
        checkPermission()
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
    }

    private fun setText() {
        with(binding) {
            tvSong.text = getString(R.string.str_songs)
            tvAlbum.text = getString(R.string.str_albums)
            tvArtist.text = getString(R.string.str_artists)
        }
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

    private fun getData() {
        songViewModel.getAllMusicFromDevice()
        albumViewModel.getAllAlbums()
        artistViewModel.getAllArtists()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            } else {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                )
            }
            ActivityCompat.requestPermissions(
                this,
                permissions,
                MY_REQUEST_PERMISSION
            )
        } else {
            getData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ( requestCode == MY_REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getData()
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