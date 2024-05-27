package com.github.emmpann.cinemaapp.page.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.github.emmpann.cinemaapp.core.remote.response.DetailResponse
import com.github.emmpann.cinemaapp.core.remote.response.ResultApi
import com.github.emmpann.cinemaapp.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _movieId = MutableLiveData<Int>()

    private val _currentMovie = MutableLiveData<DetailResponse>()

    fun setMovieId(id: Int) {
        this._movieId.value = id
    }

    fun setCurrentMovie(item: DetailResponse) {
        _currentMovie.value = item
    }

    val movieDetail: LiveData<ResultApi<DetailResponse>> = _movieId.switchMap {
        repository.getMovieDetail(it).asLiveData()
    }

    private var _favoriteMovie = MutableLiveData<DetailResponse>()
    val favoriteMovie: LiveData<DetailResponse> get() = _favoriteMovie

    fun getFavoriteMovie() {
        viewModelScope.launch {
            _favoriteMovie.value =
                _movieId.value?.let { repository.getFavoriteMovieById(it).firstOrNull() }
        }
    }

    fun addMovieFavorite() {
        viewModelScope.launch {
            _currentMovie.value?.let { repository.addFavoriteMovie(it) }
        }
    }

    fun removeMovieFavorite() {
        viewModelScope.launch {
            _currentMovie.value?.let { repository.removeFavoriteMove(it) }
        }
    }
}