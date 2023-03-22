package com.project.mvvm.bases

sealed class OutcomeState<out T>{
    object Loading : OutcomeState<Nothing>()
    data class Error(val message: String?) : OutcomeState<Nothing>()
    data class Success<T>(val result: T) : OutcomeState<T>()
}
