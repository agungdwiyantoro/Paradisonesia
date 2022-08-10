package com.devfutech.paradisonesia.domain.model.merchant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by devfutech on 9/17/2021.
 */
@Parcelize
data class MerchantStatus(
    val id: Int?,
    val isHiglight: Int?,
    val ktp: String?,
    val merchantLevelId: Int?,
    val name: String?,
    val note: String?,
    val npwp: String?,
    val siup: String?,
    val status: String?,
    val statusId: Int?
) : Parcelable {
    fun isDraf() = status == "Draft"
    fun isReview() = status == "Submit for Review"
    fun isAccepted() = status == "Accepted"
    fun isRejected() = status == "Rejected"
}
