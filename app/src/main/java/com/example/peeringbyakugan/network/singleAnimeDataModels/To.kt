package com.example.peeringbyakugan.network.singleAnimeDataModels


import com.google.gson.annotations.SerializedName


data class To(

	@field:SerializedName("month")
	val month: Int? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("day")
	val day: Int? = null
)