package com.project.mvvm.views.home

import androidx.lifecycle.MutableLiveData
import com.project.mvvm.bases.BaseViewModel
import com.project.mvvm.bases.OutcomeState
import com.project.mvvm.repository.SongRepository
import com.project.mvvm.utilities.Event
import com.project.mvvm.views.home.models.Song

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