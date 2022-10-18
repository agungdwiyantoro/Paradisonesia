package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.merchant.MerchantRegisterResponse
import com.devfutech.paradisonesia.data.network.response.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by devfutech on 9/7/2021.
 */
interface MerchantService {
    @POST("merchants/register")
    @Multipart
    //@FormUrlEncoded
    suspend fun merchantRegister(
        @PartMap payload:Map<String,String>,
        @PartMap payloadKtpNumber:Map<String,Long>,
        //@Field("ktp_number") long: Long?
        //@Part ktp:MultipartBody.Part?,
       // @Part npwp:MultipartBody.Part?,
       // @Part siup:MultipartBody.Part?,
    ): BaseResponse<MerchantRegisterResponse>


    @GET("merchants/status")
    suspend fun merchantStatus(): BaseResponse<MerchantRegisterResponse>
}