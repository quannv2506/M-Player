package com.quannv.music.applications

import android.app.Application
import android.webkit.WebView
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.gson.GsonBuilder
import com.quannv.music.db.AppDatabase
import com.quannv.music.networks.ServicesManager.Companion.getUnsafeOkHttpClient
import com.quannv.music.repository.AlbumRepository
import com.quannv.music.repository.ArtistRepository
import com.quannv.music.repository.SongRepository
import com.quannv.music.repository.TokenRepository
import com.quannv.music.utilities.LogUtils
import com.quannv.music.utilities.UserDefaults
import com.quannv.music.utilities.Utils
import com.quannv.music.viewmodel.AlbumViewModel
import com.quannv.music.viewmodel.ArtistViewModel
import com.quannv.music.viewmodel.SongViewModel
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    companion object {
        private lateinit var sInstance: MyApplication

        fun getInstance(): MyApplication {
            return sInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
//        contentResolver
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleListener(this))
        //koin module
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    okHttpModule,
                    gsonModule,
                    contentResolverModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
        //Load util class
        Utils.load(this)
        //Load user default
        UserDefaults.standard.load(this)
        //Load saved language
        RxJavaPlugins.setErrorHandler { e ->
            e.printStackTrace()
            LogUtils.e(e.toString())
        }

        WebView(this).destroy()
    }

    private val contentResolverModule = module {
        single { this@MyApplication.contentResolver }
    }

    private val repositoryModule = module {
        single { AppDatabase.getInstance(this@MyApplication) }
        single { TokenRepository(get()) }
        single { SongRepository(get()) }
        single { AlbumRepository(get()) }
        single { ArtistRepository(get()) }
    }

    private val viewModelModule = module {
        viewModel { SongViewModel(get()) }
        viewModel { AlbumViewModel(get()) }
        viewModel { ArtistViewModel(get()) }
    }

    private val okHttpModule = module {
        fun provideOkHttpClient(): OkHttpClient {
            val client = getUnsafeOkHttpClient().newBuilder()
            client.addInterceptor {
                val original = it.request()
                val builder = original.newBuilder()
                val request = builder.method(original.method, original.body)
                    .build()
                it.proceed(request)
            }
            return client.build()
        }
        single { provideOkHttpClient() }
    }

    private val gsonModule = module {
        fun provideGsonBuilder(): GsonBuilder {
            return GsonBuilder()
        }
        single { provideGsonBuilder() }
    }
}