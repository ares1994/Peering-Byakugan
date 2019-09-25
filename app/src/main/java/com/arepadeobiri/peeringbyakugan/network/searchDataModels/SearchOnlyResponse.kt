package com.arepadeobiri.peeringbyakugan.network.searchDataModels


import com.google.gson.annotations.SerializedName


data class SearchOnlyResponse(

	@field:SerializedName("request_hash")
	val requestHash: String? = null,

	@field:SerializedName("last_page")
	val lastPage: Int? = null,

	@field:SerializedName("request_cached")
	val requestCached: Boolean? = null,

	@field:SerializedName("request_cache_expiry")
	val requestCacheExpiry: Int? = null,

	@field:SerializedName("results")
	val results: List<SearchOnlyResultsItem?>? = null
)