package com.example.peeringbyakugan.network


import com.google.gson.annotations.SerializedName


data class SearchOnlyResultsItem(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("synopsis")
	val synopsis: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("rated")
	val rated: String? = null,

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("members")
	val members: Int? = null,

	@field:SerializedName("airing")
	val airing: Boolean? = null,

	@field:SerializedName("episodes")
	val episodes: Int? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)