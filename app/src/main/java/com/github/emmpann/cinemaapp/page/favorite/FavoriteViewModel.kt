package com.github.emmpann.cinemaapp.page.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.emmpann.cinemaapp.core.remote.response.DetailResponse
import com.github.emmpann.cinemaapp.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _favMovies = MutableLiveData<List<DetailResponse>>()

    fun getAllFavoriteMovie() {
        _favMovies.value = repository.getAllFavoriteMovie()
    }

    val favMovies: LiveData<List<DetailResponse>> get() = _favMovies
}