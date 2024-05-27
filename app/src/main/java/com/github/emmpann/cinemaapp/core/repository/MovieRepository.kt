package com.github.emmpann.cinemaapp.core.repository

import com.github.emmpann.cinemaapp.core.data.FavoriteMovie
import com.github.emmpann.cinemaapp.core.remote.response.DetailResponse
import com.github.emmpann.cinemaapp.core.remote.response.ResultApi
import com.github.emmpann.cinemaapp.core.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class MovieRepository(private val apiService: ApiService) {

    private var favoriteMovie = mutableListOf<DetailResponse>()

    init {
        if (favoriteMovie.isEmpty()) {
            favoriteMovie = FavoriteMovie.favoriteMovieList
        }
    }

    fun getNowPlayingMovie() = flow {
        try {
            val successResponse = apiService.getNowPlayingMovie("5f408b45f81e92ff95213749bb158f96")
            emit(ResultApi.Success(successResponse.results))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun getMovieDetail(id: Int) = flow {
        try {
            val successResponse = apiService.getMovieDetail(id, "5f408b45f81e92ff95213749bb158f96")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun getUpcomingMovie() = flow {
        try {
            val successResponse = apiService.getUpcomingMovie("5f408b45f81e92ff95213749bb158f96")
            emit(ResultApi.Success(successResponse.results))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun searchMovie(query: String) = flow {
        try {
            val successResponse = apiService.searchMovie(query)
            emit(ResultApi.Success(successResponse.results))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun addFavoriteMovie(item: DetailResponse) {
        FavoriteMovie.favoriteMovieList.add(item)
    }

    fun removeFavoriteMove(item: DetailResponse) {
        FavoriteMovie.favoriteMovieList.remove(item)
    }

    fun getAllFavoriteMovie() = FavoriteMovie.favoriteMovieList

    fun getFavoriteMovieById(id: Int): Flow<DetailResponse?> = flowOf(favoriteMovie.find {
        it.id == id
    })
}