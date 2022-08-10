package com.devfutech.paradisonesia.data.network.response.merchant


import com.devfutech.paradisonesia.domain.model.merchant.MerchantStatus
import com.google.gson.annotations.SerializedName

data class MerchantRegisterResponse(
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_higlight")
    val isHiglight: Int?,
    @SerializedName("ktp")
    val ktp: String?,
    @SerializedName("merchant_level_id")
    val merchantLevelId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("npwp")
    val npwp: String?,
    @SerializedName("siup")
    val siup: String?,
    @SerializedName("status")
    val statuses: StatusResponse?,
    @SerializedName("status_id")
    val statusId: Int?
){
    fun toMerchantStatus() = MerchantStatus(
        id = id,
        isHiglight = isHiglight,
        ktp = ktp,
        merchantLevelId = merchantLevelId,
        name = name,
        note = note,
        npwp = npwp,
        siup = siup,
        status = statuses?.name,
        statusId = statusId
    )
}