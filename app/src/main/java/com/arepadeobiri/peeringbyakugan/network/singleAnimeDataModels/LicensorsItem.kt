package com.arepadeobiri.peeringbyakugan.network.singleAnimeDataModels


import com.google.gson.annotations.SerializedName


data class LicensorsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)