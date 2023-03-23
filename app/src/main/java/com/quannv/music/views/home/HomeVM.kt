package com.quannv.music.views.home

import androidx.lifecycle.MutableLiveData
import com.quannv.music.bases.BaseViewModel
import com.quannv.music.bases.OutcomeState
import com.quannv.music.repository.SongRepository
import com.quannv.music.utilities.Event
import com.quannv.music.views.home.models.Song

class HomeVM(private val songRepository: SongRepository) : BaseViewModel() {

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