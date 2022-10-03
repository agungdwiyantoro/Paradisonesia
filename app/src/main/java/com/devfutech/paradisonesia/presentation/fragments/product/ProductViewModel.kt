package com.devfutech.paradisonesia.presentation.fragments.product

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.domain.model.PriceID
import com.devfutech.paradisonesia.domain.model.ReviewLihatSemua
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.domain.model.product.Product
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
class ProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val state: SavedStateHandle
) : BaseViewModel() {

    /*
    private val _product: MutableStateFlow<Resource<List<Product>>> = MutableStateFlow(Resource.Success(listOf()))
    val product:MutableStateFlow<Resource<List<Product>>>
        get() = _product
     */

    private val _productCity: MutableStateFlow<Resource<List<City>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productCity: MutableStateFlow<Resource<List<City>>>
        get() = _productCity

    private val _product: MutableStateFlow<Resource<List<Product>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val product: MutableStateFlow<Resource<List<Product>>>
        get() = _product

    val priceID = state.get<PriceID>("categoryProductID")

    init {
        //getProducts(mutableMapOf("page" to "1", "show" to "2", "sort_by" to "price", "sort_type" to "asc"))

        Timber.tag("pvmodel").d("id is " + priceID?.id)
        if(priceID?.id==0||priceID?.id==null){
            getProducts()
        }
        else if (priceID.subCategoryId!=null){
            getProductSubCategory(priceID.subCategoryId)
        }
        else{
            getProducts(priceID.id)
        }
    }

    fun getProducts(){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct()
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

    fun getProducts(index: Int?){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct()
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it.filter {
                        it.sub_category?.category?.id == index})

                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }

    fun getProductSubCategory(index: Int?){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct()
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it.filter {
                        it.sub_category?.id == index})

                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }

    fun getProductAllSearch(map: Map<String, String>, tvResult: AppCompatTextView, context: Context){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it)

                    tvResult.text = context.resources.getString(R.string.result, it.size)

                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }

    fun getProductByCategorySearch(map: Map<String, String>, tvResult: AppCompatTextView, context: Context){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it.filter {
                        it.sub_category?.category?.id == priceID?.id})

                    tvResult.text = context.resources.getString(R.string.result, it.filter {
                        it.sub_category?.category?.id == priceID?.id}.size)

                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }

    fun getProductByLocation(map: Map<String, String>, tvResult: AppCompatTextView, context: Context){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                }.collect{
                    _product.value = Resource.Success(it)

                    tvResult.text = context.resources.getString(R.string.result, it.size)
                }
        }
    }

    fun getProductSort(map: Map<String, String>){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Error")

                }.collect {
                    _product.value = Resource.Success(it.filter {
                        it.sub_category?.category?.id == priceID?.id
                    })
                    Timber.tag("AnjingProduct").d("Success" + it)
                }
        }
    }

    fun getProductsMap(map: Map<String, String>, tvResult: AppCompatTextView, context: Context){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("MapKontolProduct").d("MapError")

                }.collect {
                    _product.value = Resource.Success(it.filter {
                        it.sub_category?.category?.id == priceID?.id
                    })

                    tvResult.text = context.resources.getString(R.string.result, it.filter {
                        it.sub_category?.category?.id == priceID?.id}.size)

                    Timber.tag("MapAnjingProduct").d("Success" + it)
                }
        }
    }
}