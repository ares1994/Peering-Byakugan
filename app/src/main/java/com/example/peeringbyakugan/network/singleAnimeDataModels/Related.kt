package com.example.peeringbyakugan.network.singleAnimeDataModels


import com.google.gson.annotations.SerializedName


data class Related(

	@field:SerializedName("Adaptation")
	val adaptation: List<AdaptationItem?>? = null
)