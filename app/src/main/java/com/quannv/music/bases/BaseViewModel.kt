package com.quannv.music.bases

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.quannv.music.R
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {
    private var disposable: CompositeDisposable? = null

    init {
        disposable = CompositeDisposable()
    }

    fun <RP> execute(
        apiService: Observable<RP>,
        onSuccess: ((response: RP) -> Unit),
        onFailed: ((error: Throwable) -> Unit)
    ) {
        disposable?.add(
            apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                }, { e ->
                    e.printStackTrace()
                    val ctx = ActivityUtils.getTopActivity()
                    val message =
                        when (e) {
                            is UnknownHostException -> {
                                ctx.resources?.getString(R.string.str_error_not_found_server)
                            }
                            else -> {
                                ctx.resources?.getString(R.string.str_error_occurs)
                            }
                        }
                    onFailed(Throwable(message))
                })
        )
    }

    fun <RP> execute(
        compositeDisposable: CompositeDisposable,
        apiService: Observable<RP>,
        onSuccess: ((response: RP) -> Unit),
        onFailed: ((error: Throwable) -> Unit)
    ) {
        compositeDisposable.clear()
        compositeDisposable.add(
            apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    //onNext
                    { t ->
                        onSuccess(t)
                    },
                    //onError
                    { e ->
                        e.printStackTrace()
                        val ctx = ActivityUtils.getTopActivity()
                        val message =
                            if (e is UnknownHostException) {
                                ctx.resources?.getString(R.string.str_error_not_found_server)
                            } else {
                                ctx.resources?.getString(R.string.str_error_occurs)
                            }
                        onFailed(Throwable(message))
                    })
        )
    }

    fun <RP> execute(
        apiService: Flowable<RP>,
        onSuccess: ((response: RP) -> Unit),
        onFailed: ((error: Throwable) -> Unit)
    ) {
        disposable?.add(
            apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                }, { e ->
                    e.printStackTrace()
                    val ctx = ActivityUtils.getTopActivity()
                    val message =
                        when (e) {
                            is UnknownHostException -> {
                                ctx.resources?.getString(R.string.str_error_not_found_server)
                            }
                            else -> {
                                ctx.resources?.getString(R.string.str_error_occurs)
                            }
                        }
                    onFailed(Throwable(message))
                })
        )
    }

    fun execute(
        apiService: Completable,
        onSuccess: () -> Unit = {},
        onFailed: ((error: Throwable) -> Unit)
    ) {
        disposable?.add(
            apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess.invoke()
                }, { e ->
                    e.printStackTrace()
                    val ctx = ActivityUtils.getTopActivity()
                    val message =
                        when (e) {
                            is UnknownHostException -> {
                                ctx.resources?.getString(R.string.str_error_not_found_server)
                            }
                            else -> {
                                ctx.resources?.getString(R.string.str_error_occurs)
                            }
                        }
                    onFailed(Throwable(message))
                })
        )
    }

    fun execute(
        compositeDisposable: CompositeDisposable,
        apiService: Completable,
        onSuccess: () -> Unit = {},
        onFailed: ((error: Throwable) -> Unit)
    ) {
        compositeDisposable.clear()
        compositeDisposable.add(
            apiService
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess.invoke()
                }, { e ->
                    e.printStackTrace()
                    val ctx = ActivityUtils.getTopActivity()
                    val message =
                        when (e) {
                            is UnknownHostException -> {
                                ctx.resources?.getString(R.string.str_error_not_found_server)
                            }
                            else -> {
                                ctx.resources?.getString(R.string.str_error_occurs)
                            }
                        }
                    onFailed(Throwable(message))
                })
        )
    }

}