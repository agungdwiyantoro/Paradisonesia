package com.devfutech.paradisonesia.presentation.bottomsheet.filter

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.model.filter.SortFilter
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.usecase.ProductUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by devfutech on 10/8/2021.
 */
@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
): BaseViewModel(){

    /*
    private val _filter: MutableStateFlow<Resource<List<Filter>>> = MutableStateFlow(Resource.Success(listOf()))
    val filter: MutableStateFlow<Resource<List<Filter>>>
        get() = _filter
     */

    private val _filterx: MutableStateFlow<Resource<List<Filter>>> = MutableStateFlow(Resource.Success(listOf()))
    val filterx: MutableStateFlow<Resource<List<Filter>>>
        get() = _filterx

    private val _filter: MutableStateFlow<Resource<List<Product.Sub_category?>?>> = MutableStateFlow(Resource.Success(listOf()))
    val filter: MutableStateFlow<Resource<List<Product.Sub_category?>?>>
        get() = _filter

    private val _SortFilter: MutableStateFlow<Resource<List<SortFilter>>> = MutableStateFlow(Resource.Success(listOf()))
    val SortFilter: MutableStateFlow<Resource<List<SortFilter>>>
        get() = _SortFilter

/*
    fun getProvince(){
        _filter.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProvince()
                .catch { error->
                    onError(error)
                    _filter.value = Resource.Failure(defaultError(error))
                }.map {_filters ->
                   _filters.map { it.toFilter() }
                }.collect {
                    _filter.value = Resource.Success(it)
                }
        }
    }

    fun getCategory(){
        _filter.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListCategoryProduct()
                .catch { error->
                    onError(error)
                    _filter.value = Resource.Failure(defaultError(error))
                }.map {filters ->
                   filters.flatMap { it.toFilter()?: listOf() }
                }.collect {
                    _filter.value = Resource.Success(it)
                }
        }
    }
 */
    fun getProvince(){
        _filterx.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProvince()
                .catch { error->
                    onError(error)
                    _filterx.value = Resource.Failure(defaultError(error))
                }.map {_filters ->
                    _filters.map { it.toFilter() }
                }.collect {
                    _filterx.value = Resource.Success(it)
                }
        }
    }
    fun getCategory(){
        _filter.value = Resource.Loading()
        viewModelScope.launch {
            productUseCase.getListProductCategoryProduct()
                .catch { error->
                    onError(error)
                    _filter.value = Resource.Failure(defaultError(error))
                }.collect {
                    _filter.value = Resource.Success(it)
                }
        }
    }

    fun getSortList() {

        viewModelScope.launch {
            val listSortFilter: List<SortFilter> = mutableListOf(SortFilter(1, 2, 3, 4))
            _SortFilter.value = Resource.Success(listSortFilter)

            Timber.tag("FBSVModel").d("value " + _SortFilter.value)

        }
    }
}