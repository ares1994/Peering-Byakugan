package com.arepadeobiri.peeringbyakugan.network.charactersDataModels


import com.google.gson.annotations.SerializedName


data class StaffItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("positions")
	val positions: List<String?>? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)