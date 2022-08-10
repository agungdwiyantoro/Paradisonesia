package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("end_time")
    val endTime: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("schedule_id")
    val scheduleId: Int?,
    @SerializedName("start_time")
    val startTime: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)