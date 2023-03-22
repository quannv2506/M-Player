package com.project.mvvm.applications

import android.app.Application
import android.webkit.WebView
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.gson.GsonBuilder
import com.project.mvvm.db.AppDatabase
import com.project.mvvm.networks.ServicesManager.Companion.getUnsafeOkHttpClient
import com.project.mvvm.repository.SongRepository
import com.project.mvvm.repository.TokenRepository
import com.project.mvvm.utilities.LogUtils
import com.project.mvvm.utilities.UserDefaults
import com.project.mvvm.utilities.Utils
import com.project.mvvm.views.home.HomeVM
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
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleListener(this))
        //koin module
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(okHttpModule, gsonModule, repositoryModule, viewModelModule))
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

    private val repositoryModule = module {
        single { AppDatabase.getInstance(this@MyApplication) }
        single { TokenRepository(get()) }
        single { SongRepository(get()) }
    }

    private val viewModelModule = module {
        viewModel { HomeVM(get()) }
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