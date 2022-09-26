package com.devfutech.paradisonesia.presentation.fragments.review_lihat_semua

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.PriceID
import com.devfutech.paradisonesia.domain.model.ReviewLihatSemua
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.model.review.Review
import com.devfutech.paradisonesia.domain.usecase.ProductDetailUseCase
import com.devfutech.paradisonesia.domain.usecase.ProductUseCase
import com.devfutech.paradisonesia.domain.usecase.ReviewUseCase
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
class ReviewLihatSemuaViewModel @Inject constructor(
    private val reviewUseCase: ReviewUseCase,
    private val state: SavedStateHandle
) : BaseViewModel() {


    /*
    private val _product: MutableStateFlow<Resource<List<Product>>> = MutableStateFlow(Resource.Success(listOf()))
    val product:MutableStateFlow<Resource<List<Product>>>
        get() = _product
     */

    private val _productDetailReviews: MutableStateFlow<Resource<List<Review>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val productDetailReviews: MutableStateFlow<Resource<List<Review>>>
        get() = _productDetailReviews

    val tempReviewID = state.get<ReviewLihatSemua>("ratingAverageRatingCount")
    val index = tempReviewID?.id.toString()
    init {
        //getProducts(mutableMapOf("page" to "1", "show" to "2", "sort_by" to "price", "sort_type" to "asc"))
        //getProducts()
        //val map = mapOf("2" to "")

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
    fun ProductReviews(index: String){
        _productDetailReviews.value = Resource.Loading()
        viewModelScope.launch {
            reviewUseCase.getListReview(index)
                .catch { error->
                    onError(error)
                    _productDetailReviews.value = Resource.Failure(defaultError(error))
                    Timber.tag("Product Detail Reviews NEW").d("error")
                }.collect{
                    _productDetailReviews.value = Resource.Success(it)
                    Timber.tag("Product Detail Reviews NEW").d("Success " + it)
                }
        }
    }

}