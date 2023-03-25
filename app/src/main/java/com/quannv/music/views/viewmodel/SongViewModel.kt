package com.quannv.music.views.viewmodel

import androidx.lifecycle.MutableLiveData
import com.quannv.music.bases.BaseViewModel
import com.quannv.music.bases.OutcomeState
import com.quannv.music.repository.SongRepository
import com.quannv.music.utilities.Event
import com.quannv.music.models.Song

class SongViewModel(private val songRepository: SongRepository) : BaseViewModel() {

    private val _getSongsResponse = MutableLiveData<Event<OutcomeState<List<Song>>>>()
    val songTokensResponse: MutableLiveData<Event<OutcomeState<List<Song>>>>
        get() = _getSongsResponse

    fun getAllMusicFromDevice(){
        val myObservable = songRepository.loadSongs()
        execute(myObservable, onSuccess = {
            _getSongsResponse.postValue(Event(OutcomeState.Success(it)))
        }, onFailed = {
            it.printStackTrace()
            _getSongsResponse.postValue(Event(OutcomeState.Error(it.message)))
        })
    }

}