package com.project.mvvm.views.home

import androidx.lifecycle.MutableLiveData
import com.project.mvvm.BuildConfig
import com.project.mvvm.bases.BaseViewModel
import com.project.mvvm.bases.OutcomeState
import com.project.mvvm.db.entity.Token
import com.project.mvvm.networks.ServicesManager
import com.project.mvvm.repository.TokenRepository
import com.project.mvvm.utilities.Event
import com.project.mvvm.utilities.LogUtils

class HomeVM(private val tokenRepository: TokenRepository) : BaseViewModel() {
    private val apiHome: APIHome = ServicesManager.builder(BuildConfig.BASE_URL_API)

    private val _getTokensState = MutableLiveData<Event<OutcomeState<List<Token>>>>()
    val getTokensState: MutableLiveData<Event<OutcomeState<List<Token>>>>
        get() = _getTokensState

    fun getList() {
        _getTokensState.postValue(Event(OutcomeState.Loading))
        val myRequest = apiHome.getList().map {
            val result = it.data ?: listOf()
            tokenRepository.insertTokens(result)
            result
        }
        execute(myRequest, onSuccess = {
            LogUtils.d("+++++++++CheckListTokens: ${it.size}")
            _getTokensState.postValue(Event(OutcomeState.Success(it)))
        }, onFailed = {
            it.printStackTrace()
            _getTokensState.postValue(Event(OutcomeState.Error(it.message)))
        })
    }

}