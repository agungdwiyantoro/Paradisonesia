package com.devfutech.paradisonesia.presentation.fragments.home_customer

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.category_product.CategoryProduct
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.domain.usecase.ProductUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCustomerViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : BaseViewModel() {
    private val _banner: MutableStateFlow<Resource<List<Banner>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val banner: MutableStateFlow<Resource<List<Banner>>>
        get() = _banner
    private val _categoryProduct: MutableStateFlow<Resource<List<CategoryProduct>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val categoryProduct: MutableStateFlow<Resource<List<CategoryProduct>>>
        get() = _categoryProduct
    private val _city: MutableStateFlow<Resource<List<City>>> = MutableStateFlow(Resource.Success(
        emptyList()))
    val city: MutableStateFlow<Resource<List<City>>>
        get() = _city

    init {
        getBanners()
        getListCategory()
        getListCity()
    }

    private fun getListCity() {
        _city.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListCity()
                .catch {
                    onError(it)
                    _city.value = Resource.Failure(defaultError(it))
                }.collect {
                    _city.value = Resource.Success(it)
                }
        }
    }

    private fun getListCategory() {
        _categoryProduct.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListCategoryProduct()
                .catch {
                    onError(it)
                    _categoryProduct.value = Resource.Failure(defaultError(it))
                }.collect {
                    _categoryProduct.value = Resource.Success(it)
                }
        }
    }

    private fun getBanners() {
        _banner.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListBanner()
                .catch {
                    onError(it)
                    _banner.value = Resource.Failure(defaultError(it))
                }.collect {
                    _banner.value = Resource.Success(it)
                }
        }
    }
}