package com.example.peeringbyakugan.detailsData


import com.google.gson.annotations.SerializedName


data class From(

	@field:SerializedName("month")
	val month: Int? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("day")
	val day: Int? = null
)