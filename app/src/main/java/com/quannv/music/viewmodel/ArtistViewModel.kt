package com.quannv.music.viewmodel

import androidx.lifecycle.MutableLiveData
import com.quannv.music.bases.BaseViewModel
import com.quannv.music.bases.OutcomeState
import com.quannv.music.models.Artist
import com.quannv.music.repository.ArtistRepository
import com.quannv.music.utilities.Event

class ArtistViewModel(
    private val artistRepository: ArtistRepository
) : BaseViewModel() {

    private val _getArtistsResponse = MutableLiveData<Event<OutcomeState<List<Artist>>>>()
    val getArtistsResponse: MutableLiveData<Event<OutcomeState<List<Artist>>>>
        get() = _getArtistsResponse

    fun getAllArtists() {
        _getArtistsResponse.postValue(Event(OutcomeState.Loading))
        val myObserver = artistRepository.loadArtists()
        execute(myObserver, onSuccess = {
            _getArtistsResponse.postValue(Event(OutcomeState.Success(it)))
        }, onFailed = {
            it.printStackTrace()
            _getArtistsResponse.postValue(Event(OutcomeState.Error(it.message)))
        })
    }
}