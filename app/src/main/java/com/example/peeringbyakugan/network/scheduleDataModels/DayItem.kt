package com.example.peeringbyakugan.network.scheduleDataModels


import com.google.gson.annotations.SerializedName


data class DayItem(

    @field:SerializedName("image_url")
	val imageUrl: String? = null,

    @field:SerializedName("mal_id")
	val malId: Int? = null,

    @field:SerializedName("synopsis")
	val synopsis: String? = null,

    @field:SerializedName("source")
	val source: String? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("licensors")
	val licensors: List<Any?>? = null,

    @field:SerializedName("url")
	val url: String? = null,

    @field:SerializedName("producers")
	val producers: List<ProducersItem?>? = null,

    @field:SerializedName("score")
	val score: Double? = null,

    @field:SerializedName("airing_start")
	val airingStart: String? = null,

    @field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

    @field:SerializedName("members")
	val members: Int? = null,

    @field:SerializedName("r18")
	val r18: Boolean? = null,

    @field:SerializedName("episodes")
	val episodes: Int? = null,

    @field:SerializedName("kids")
	val kids: Boolean? = null
)