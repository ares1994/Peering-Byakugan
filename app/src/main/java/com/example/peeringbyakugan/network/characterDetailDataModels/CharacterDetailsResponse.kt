package com.example.peeringbyakugan.network.characterDetailDataModels


import com.google.gson.annotations.SerializedName


data class CharacterDetailsResponse(

	@field:SerializedName("name_kanji")
	val nameKanji: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("request_cache_expiry")
	val requestCacheExpiry: Int? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("voice_actors")
	val voiceActors: List<VoiceActorsItem?>? = null,

	@field:SerializedName("request_hash")
	val requestHash: String? = null,

	@field:SerializedName("request_cached")
	val requestCached: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mangaography")
	val mangaography: List<MangaographyItem?>? = null,

	@field:SerializedName("nicknames")
	val nicknames: List<String?>? = null,

	@field:SerializedName("member_favorites")
	val memberFavorites: Int? = null,

	@field:SerializedName("animeography")
	val animeography: List<AnimeographyItem?>? = null
)