package com.quannv.music.viewmodel

import androidx.lifecycle.MutableLiveData
import com.quannv.music.bases.BaseViewModel
import com.quannv.music.bases.OutcomeState
import com.quannv.music.models.Album
import com.quannv.music.repository.AlbumRepository
import com.quannv.music.utilities.Event

class AlbumViewModel(
    private val albumRepository: AlbumRepository
) : BaseViewModel() {

    private val _getAlbumsResponse = MutableLiveData<Event<OutcomeState<List<Album>>>>()
    val getAlbumsResponse: MutableLiveData<Event<OutcomeState<List<Album>>>>
        get() = _getAlbumsResponse

    fun getAllAlbums() {
        _getAlbumsResponse.postValue(Event(OutcomeState.Loading))
        val myObserver = albumRepository.loadAlbums()
        execute(myObserver, onSuccess = {
            _getAlbumsResponse.postValue(Event(OutcomeState.Success(it)))
        }, onFailed = {
            it.printStackTrace()
            _getAlbumsResponse.postValue(Event(OutcomeState.Error(it.message)))
        })
    }
}