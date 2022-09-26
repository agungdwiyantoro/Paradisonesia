package com.devfutech.paradisonesia.presentation.fragments.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.PriceID
import com.devfutech.paradisonesia.domain.model.ReviewLihatSemua
import com.devfutech.paradisonesia.domain.model.banner.Banner
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

    private val _product: MutableStateFlow<Resource<List<Product>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val product: MutableStateFlow<Resource<List<Product>>>
        get() = _product

    val id = state.get<Int>("categoryProductID")

    init {
        //getProducts(mutableMapOf("page" to "1", "show" to "2", "sort_by" to "price", "sort_type" to "asc"))
        if(id==0){
            getProducts()
        }
        else{
            getProducts(id)
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

    fun getProductsMap(map: Map<String, String>){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProduct(map)
                .catch { error->
                    onError(error)
                    _product.value = Resource.Failure(defaultError(error))
                    Timber.tag("MapKontolProduct").d("MapError")

                }.collect {
                    _product.value = Resource.Success(it)

                    Timber.tag("MapAnjingProduct").d("Success" + it)
                }
        }
    }
}