package com.arepadeobiri.peeringbyakugan.network.characterDetailDataModels


import com.google.gson.annotations.SerializedName


data class MangaographyItem(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)