package com.project.mvvm.utilities

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<out Event<T>>.observeEventUnhandled(
    owner: LifecycleOwner,
    onEventCallback: (T) -> Unit
) {
    observe(owner) {
        it?.getContentIfNotHandled()?.let(onEventCallback)
    }
}

fun <T> LiveData<out Event<T>>.observeEventPeekContent(
    owner: LifecycleOwner,
    onEventCallback: (T) -> Unit
) {
    observe(owner) {
        it?.peekContent()?.let(onEventCallback)
    }
}