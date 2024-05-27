package com.github.emmpann.cinemaapp.core.remote.response

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("poster_path")
	val posterPath: String
)