package com.devfutech.paradisonesia.data.network.response.product


import com.devfutech.paradisonesia.domain.model.product.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    //@SerializedName("products")
    //val product: DataProductResponse?,
    //val products: List<DataProductResponse>? = listOf(),
    //@SerializedName("city_code")
    //val cityCode: String?,
   // @SerializedName("product_sub_category_id")
   // val productSubCategoryId: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("merchant_id")
    val merchant_id: Int?,
    @SerializedName("product_sub_category_id")
    val product_sub_category_id: Int?,
    @SerializedName("status_id")
    val status_id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("coordinate")
    val coordinate: String?,
    @SerializedName("cityCode")
    val cityCode: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("start_date")
    val start_date: String?,
    @SerializedName("end_date")
    val end_date: String?,
    @SerializedName("net_price")
    val net_price: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("maxPerson")
    val maxPerson: String?,
    @SerializedName("minPerson")
    val minPerson: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("isHighlight")
    val isHiglight: Int?,
    @SerializedName("updated_at")
    val updated_at: String?,
    @SerializedName("rating_average")
    val rating_average: String?,
    @SerializedName("reviews_count")
    val reviews_count: Int?,
    @SerializedName("city")
    val city : Product.City?,
    @SerializedName("sub_category")
    val sub_category: Product.Sub_category?,

    @SerializedName("page")
    val page: String?,
    @SerializedName("show")
    val show: String?,
    @SerializedName("sort_by")
    val sortBy: String?,
    @SerializedName("sort_type")
    val sortType: String?

) {
   // fun toProduct() = products?.map { product ->

    fun toProduct() = Product(
        id = id,
        merchant_id = merchant_id,
        product_sub_category_id = product_sub_category_id,
        status_id = status_id,
        name = name,
        description = description,
        address = address,
        coordinate = coordinate,
        cityCode = cityCode,
        duration = duration,
        start_date = start_date,
        end_date = end_date,
        net_price = net_price,
        price = price,
        unit = unit,
        thumbnail = thumbnail,
        maxPerson = maxPerson,
        minPerson = minPerson,
        note = note,
        isHiglight = isHiglight,
        updated_at = updated_at,
        rating_average = rating_average,
        reviews_count = reviews_count,
        city = city,
        sub_category = sub_category
        /*
       id = product?.id,
       merchant_id = product?.merchant_id,
       product_sub_category_id = product?.product_sub_category_id,
       status_id = product?.status_id,
       name = product?.name,
       description = product?.description,
       address = product?.address,
       coordinate = product?.coordinate,
       cityCode = product?.cityCode,
       duration = product?.duration,
       start_date = product?.start_date,
       end_date = product?.end_date,
       net_price = product?.net_price,
       price = product?.price,
       unit = product?.unit,
       thumbnail = product?.thumbnail,
       maxPerson = product?.maxPerson,
       minPerson = product?.minPerson,
       note = product?.note,
       isHiglight = product?.isHiglight,
       updated_at = product?.updated_at,
       rating_average = product?.rating_average,
       review_count = product?.review_count,
       city = product?.city,
       sub_category = product?.sub_category

         */
    )


}