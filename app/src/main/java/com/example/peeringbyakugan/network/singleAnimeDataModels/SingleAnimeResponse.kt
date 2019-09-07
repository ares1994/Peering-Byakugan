package com.example.peeringbyakugan.network.singleAnimeDataModels


import com.google.gson.annotations.SerializedName


data class SingleAnimeResponse(

	@field:SerializedName("title_japanese")
	val titleJapanese: String? = null,

	@field:SerializedName("favorites")
	val favorites: Int? = null,

	@field:SerializedName("broadcast")
	val broadcast: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("scored_by")
	val scoredBy: Int? = null,

	@field:SerializedName("premiered")
	val premiered: String? = null,

	@field:SerializedName("request_cache_expiry")
	val requestCacheExpiry: Int? = null,

	@field:SerializedName("title_synonyms")
	val titleSynonyms: List<String?>? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("opening_themes")
	val openingThemes: List<String?>? = null,

	@field:SerializedName("related")
	val related: Related? = null,

	@field:SerializedName("request_hash")
	val requestHash: String? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@field:SerializedName("popularity")
	val popularity: Int? = null,

	@field:SerializedName("members")
	val members: Int? = null,

	@field:SerializedName("request_cached")
	val requestCached: Boolean? = null,

	@field:SerializedName("title_english")
	val titleEnglish: String? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("airing")
	val airing: Boolean? = null,

	@field:SerializedName("episodes")
	val episodes: Int? = null,

	@field:SerializedName("aired")
	val aired: Aired? = null,

	@field:SerializedName("studios")
	val studios: List<StudiosItem?>? = null,

	@field:SerializedName("ending_themes")
	val endingThemes: List<String?>? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("synopsis")
	val synopsis: String? = null,

	@field:SerializedName("licensors")
	val licensors: List<LicensorsItem?>? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("trailer_url")
	val trailerUrl: String? = null,

	@field:SerializedName("producers")
	val producers: List<ProducersItem?>? = null,

	@field:SerializedName("background")
	val background: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)