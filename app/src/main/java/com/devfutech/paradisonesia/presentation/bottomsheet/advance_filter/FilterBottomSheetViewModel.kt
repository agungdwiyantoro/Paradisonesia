package com.devfutech.paradisonesia.presentation.bottomsheet.advance_filter

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.usecase.ProductUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by devfutech on 10/8/2021.
 */
@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
): BaseViewModel(){
    private val _filter: MutableStateFlow<Resource<List<Filter>>> = MutableStateFlow(Resource.Success(listOf()))
    val filter: MutableStateFlow<Resource<List<Filter>>>
        get() = _filter

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
}