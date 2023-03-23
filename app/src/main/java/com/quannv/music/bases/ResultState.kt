package com.quannv.music.bases


data class ResultState<out T>(val status: State, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?): ResultState<T> {
            return ResultState(State.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T? = null): ResultState<T> {
            return ResultState(State.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): ResultState<T> {
            return ResultState(State.LOADING, data, null)
        }

    }
}

enum class State {
    SUCCESS,
    ERROR,
    LOADING
}