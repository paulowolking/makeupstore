package com.wolking.makeupstore.presentation.ui.makeup.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.domain.product_favorite.repository.ProductFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val productFavoriteRepository: ProductFavoriteRepository
) : ViewModel() {

    var products: MutableLiveData<List<Product>> = MutableLiveData()

    fun getFavoriteProducts() = CoroutineScope(Dispatchers.IO).launch {
        products.postValue(productFavoriteRepository.getProducts())
    }

    fun removeFavoriteProduct(product: Product) =
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) {
                productFavoriteRepository.delete(product)
            }
        }
}