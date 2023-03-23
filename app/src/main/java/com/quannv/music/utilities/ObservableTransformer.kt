package com.quannv.music.utilities

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit

fun <T> zipWithFlatMapObservable(): ObservableTransformer<T, Long> {
    return ObservableTransformer { observable ->
        observable.zipWith(
            Observable.range(COUNTER_START, ATTEMPTS),
            { _: T, u: Int -> u })
            .flatMap { t -> Observable.timer(t * 5L, TimeUnit.SECONDS) }
    }

}

fun <T> zipWithFlatMapFlowable(): FlowableTransformer<T, Long> {
    return FlowableTransformer { flowable ->
        flowable.zipWith(
            Flowable.range(COUNTER_START, ATTEMPTS),
            { _: T, u: Int -> u })
            .flatMap { t -> Flowable.timer(t * 5L, TimeUnit.SECONDS) }
    }

}

private const val COUNTER_START = 1
private const val ATTEMPTS = 5