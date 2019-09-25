package com.arepadeobiri.peeringbyakugan.network.charactersDataModels


import com.google.gson.annotations.SerializedName


data class CharactersResponse(

	@field:SerializedName("characters")
	val characters: List<CharactersItem?>? = null,

	@field:SerializedName("request_hash")
	val requestHash: String? = null,

	@field:SerializedName("request_cached")
	val requestCached: Boolean? = null,

	@field:SerializedName("staff")
	val staff: List<StaffItem?>? = null,

	@field:SerializedName("request_cache_expiry")
	val requestCacheExpiry: Int? = null
)