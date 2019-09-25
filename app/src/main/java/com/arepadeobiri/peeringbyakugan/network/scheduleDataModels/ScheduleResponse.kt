package com.arepadeobiri.peeringbyakugan.network.scheduleDataModels


import com.google.gson.annotations.SerializedName

data class ScheduleResponse(

    @field:SerializedName("request_hash")
    val requestHash: String? = null,

    @field:SerializedName("request_cached")
    val requestCached: Boolean? = null,

    @field:SerializedName("request_cache_expiry")
    val requestCacheExpiry: Int? = null,

    @field:SerializedName("monday")
    val monday: List<DayItem?>? = null,

    @field:SerializedName("tuesday")
    val tuesday: List<DayItem?>? = null,

    @field:SerializedName("wednesday")
    val wednesday: List<DayItem?>? = null,

    @field:SerializedName("thursday")
    val thursday: List<DayItem?>? = null,

    @field:SerializedName("friday")
    val friday: List<DayItem?>? = null,

    @field:SerializedName("saturday")
    val saturday: List<DayItem?>? = null,

    @field:SerializedName("sunday")
    val sunday: List<DayItem?>? = null


)