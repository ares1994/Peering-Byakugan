package com.example.peeringbyakugan.network.singleAnimeDataModels


import com.google.gson.annotations.SerializedName


data class Prop(

	@field:SerializedName("from")
	val from: From? = null,

	@field:SerializedName("to")
	val to: To? = null
)