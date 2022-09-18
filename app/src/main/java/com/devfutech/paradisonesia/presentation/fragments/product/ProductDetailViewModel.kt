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
    private val _productDetailReviews: MutableStateFlow<Resource<List<ProductDetail.Reviews?>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailReviews: MutableStateFlow<Resource<List<ProductDetail.Reviews?>>>
        get() = _productDetailReviews

    private val _product: MutableStateFlow<Resource<ProductDetail>> =
        MutableStateFlow(Resource.Success(null))
    val product: MutableStateFlow<Resource<ProductDetail>>
        get() = _product

    init {
        //getProducts(mutableMapOf("page" to "1", "show" to "2", "sort_by" to "price", "sort_type" to "asc"))
        //getProducts()
        //val map = mapOf("2" to "")

        getProducts(state.get<String>("detailProduct").toString())

        ProductReviews(state.get<String>("detailProduct").toString())
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

    fun getProducts(index : String){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productDetailUseCase.getListProductDetail(index)
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