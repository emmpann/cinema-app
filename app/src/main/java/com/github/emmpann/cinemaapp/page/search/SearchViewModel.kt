package com.github.emmpann.cinemaapp.page.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.github.emmpann.cinemaapp.core.remote.response.ResultApi
import com.github.emmpann.cinemaapp.core.remote.response.ResultsItem
import com.github.emmpann.cinemaapp.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    fun setQuery(query: String) {
        _query.value = query
    }

    val movies: LiveData<ResultApi<List<ResultsItem>>> = _query.switchMap {
        repository.searchMovie(it).asLiveData()
    }
}