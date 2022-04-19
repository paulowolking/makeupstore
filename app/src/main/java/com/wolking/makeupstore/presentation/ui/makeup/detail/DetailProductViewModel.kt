package com.wolking.makeupstore.presentation.ui.makeup.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.domain.product_favorite.repository.ProductFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val productFavoriteRepository: ProductFavoriteRepository
) : ViewModel() {

    var productIsFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun checkProductIsFavorite(productId: Int) = CoroutineScope(Dispatchers.IO).launch {
        val isFavorite = productFavoriteRepository.getProductById(productId) != null
        productIsFavorite.postValue(isFavorite)
    }

    fun favoriteProduct(product: Product, isFavorite: Boolean) =
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) {
                if (isFavorite) {
                    productFavoriteRepository.favorite(product)
                } else {
                    productFavoriteRepository.delete(product)
                }
            }
        }
}