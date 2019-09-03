package com.example.peeringbyakugan.detailsData


import com.google.gson.annotations.SerializedName


data class Related(

	@field:SerializedName("Adaptation")
	val adaptation: List<AdaptationItem?>? = null
)