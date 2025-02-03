package com.neds.raceapp.data.model

import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("race_id") val raceId: String,
    @SerializedName("meeting_name") val meetingName: String, // ✅ Use `meetingName` instead of `meeting_name`
    @SerializedName("race_number") val raceNumber: Int,
    @SerializedName("advertised_start") val advertisedStart: AdvertisedStart,
    @SerializedName("category_id") val categoryId: String
)

data class AdvertisedStart(
    @SerializedName("seconds") val seconds: Long
)