package com.github.emmpann.cinemaapp.page.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.emmpann.cinemaapp.core.remote.response.ResultApi
import com.github.emmpann.cinemaapp.core.remote.response.ResultsItem
import com.github.emmpann.cinemaapp.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    val movies: LiveData<ResultApi<List<ResultsItem>>> =
        repository.getNowPlayingMovie().asLiveData()
}