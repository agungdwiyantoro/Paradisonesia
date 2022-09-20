package com.devfutech.paradisonesia.presentation.fragments.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.usecase.ProductDetailUseCase
import com.devfutech.paradisonesia.domain.usecase.ProductUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import com.facebook.internal.Mutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailUseCase: ProductDetailUseCase,
    private val state: SavedStateHandle
) : BaseViewModel() {


    /*
    private val _product: MutableStateFlow<Resource<List<Product>>> = MutableStateFlow(Resource.Success(listOf()))
    val product:MutableStateFlow<Resource<List<Product>>>
        get() = _product
     */

    private val _productDesc: MutableStateFlow<Resource<ProductDetail>> =
        MutableStateFlow(Resource.Success(null))
    val productDesc: MutableStateFlow<Resource<ProductDetail>>
        get() = _productDesc

    private val _productDetailImages: MutableStateFlow<Resource<List<ProductDetail.Images?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailImages: MutableStateFlow<Resource<List<ProductDetail.Images?>>>
        get() = _productDetailImages

    private val _productDetailSchedules: MutableStateFlow<Resource<List<ProductDetail.Schedules?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailSchedules: MutableStateFlow<Resource<List<ProductDetail.Schedules?>>>
        get() = _productDetailSchedules

    private val _productDetailSchedulesDays: MutableStateFlow<Resource<List<ProductDetail.Schedules.Days?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailSchedulesDays: MutableStateFlow<Resource<List<ProductDetail.Schedules.Days?>>>
        get() = _productDetailSchedulesDays

    private val _productDetailInclude: MutableStateFlow<Resource<List<ProductDetail.Include_Excludes?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailInclude: MutableStateFlow<Resource<List<ProductDetail.Include_Excludes?>>>
        get() = _productDetailInclude

    private val _productDetailFasilitasLayanan: MutableStateFlow<Resource<List<ProductDetail.Facilities?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailFasilitasLayanan: MutableStateFlow<Resource<List<ProductDetail.Facilities?>>>
        get() = _productDetailFasilitasLayanan

    private val _productDetailFaqs: MutableStateFlow<Resource<List<ProductDetail.Faqs?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailFaqs: MutableStateFlow<Resource<List<ProductDetail.Faqs?>>>
        get() = _productDetailFaqs

    private val _productDetailReviews: MutableStateFlow<Resource<List<ProductDetail.Reviews?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailReviews: MutableStateFlow<Resource<List<ProductDetail.Reviews?>>>
        get() = _productDetailReviews

    val index = state.get<String>("detailProduct").toString();
    init {
        //getProducts(mutableMapOf("page" to "1", "show" to "2", "sort_by" to "price", "sort_type" to "asc"))
        //getProducts()
        //val map = mapOf("2" to "")

        ProductDesc(index)
        ProductImages(index)
        ProductSchedules(index)
        ProductSchedulesDays(index)
        ProductInclude(index)
        ProductFasilitasLayanan(index)
        ProductFaqs(index)
        ProductReviews(index)

    }

    /*
    fun getProducts(){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetail()
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it)
                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }



    fun getProducts(map:Map<String,String>){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetail(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it)
                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }
*/

    fun ProductDesc(index : String){
        _productDesc.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetail(index)
                .catch { error->
                    onError(error)
                    _productDesc.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _productDesc.value = Resource.Success(it)
                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }

    fun ProductImages(index: String){
        _productDetailImages.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailImages(index)
                .catch { error ->
                    onError(error)
                    _productDetailImages.value = Resource.Failure(defaultError(error))
                }.collect{
                    _productDetailImages.value = Resource.Success(it)
                }
        }
    }

    fun ProductSchedules(index: String){
        _productDetailSchedules.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailSchedules(index)
                .catch { error ->
                    onError(error)
                    _productDetailSchedules.value = Resource.Failure(defaultError(error))
                }.collect{
                    _productDetailSchedules.value = Resource.Success(it)
                    Timber.tag("ProductSchedules").d("ProdSche" + it)
                }
        }
    }

    fun ProductSchedulesDays(index: String){
        _productDetailSchedulesDays.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailSchdulesDays(index)
                .catch { error ->
                    onError(error)
                    _productDetailSchedulesDays.value = Resource.Failure(defaultError(error))
                }.collect{
                    _productDetailSchedulesDays.value = Resource.Success(it)
                    Timber.tag("ProductSchedulesDays").d("ProdScheDays" + it)
                }
        }
    }

    fun ProductInclude(index: String){
        _productDetailInclude.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailIncludeExcludes(index)
                .catch { error ->
                    onError(error)
                    _productDetailInclude.value = Resource.Failure(defaultError(error))
                }.collect{
                    _productDetailInclude.value = Resource.Success(it)
                }
        }
    }

    fun ProductFasilitasLayanan(index: String){
        _productDetailFasilitasLayanan.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailFasilitasLayanan(index)
                .catch { error ->
                    onError(error)
                    _productDetailFasilitasLayanan.value = Resource.Failure(defaultError(error))
                }.collect{
                    _productDetailFasilitasLayanan.value = Resource.Success(it)
                }
        }
    }

    fun ProductFaqs(index: String){
        _productDetailFaqs.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailFaqs(index)
                .catch { error ->
                    onError(error)
                    _productDetailFaqs.value = Resource.Failure(defaultError(error))
                }.collect{
                    _productDetailFaqs.value = Resource.Success(it)
                }
        }
    }

    fun ProductReviews(index: String){
        _productDetailReviews.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetailReviews(index)
                .catch { error->
                    onError(error)
                    _productDetailReviews.value = Resource.Failure(defaultError(error))
                    Timber.tag("Product Detail Reviews").d("error")
                }.collect{
                    _productDetailReviews.value = Resource.Success(it)
                    Timber.tag("Product Detail Reviews").d("Success " + it)
                }
        }
    }

}