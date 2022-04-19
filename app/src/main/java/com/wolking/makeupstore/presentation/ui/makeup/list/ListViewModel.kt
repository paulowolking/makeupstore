package com.wolking.makeupstore.presentation.ui.makeup.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.data.remote.Resource
import com.wolking.makeupstore.domain.product.repository.ProductRepository
import com.wolking.makeupstore.presentation.ui.makeup.list.enums.TypeFilterProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _filter: MutableLiveData<TypeFilterProduct> =
        MutableLiveData(TypeFilterProduct.NONE)

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private var _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> get() = _error

    private var _products: MutableLiveData<List<Product>> = MutableLiveData()
    private var _productsBackupCleanFilter = ArrayList<Product>()
    val products: LiveData<List<Product>> =
        Transformations.switchMap(_filter) { filter ->
            Transformations.map(_products) { items ->
                when {
                    filter.equals(TypeFilterProduct.BIGGESTPRICE) -> {
                        items.toList().sortedByDescending { it.price }
                    }
                    filter.equals(TypeFilterProduct.LOWESTPRICE) -> {
                        items.toList().sortedBy { it.price }
                    }
                    filter.equals(TypeFilterProduct.CRESCENT) -> {
                        items.toList().sortedBy { it.name }
                    }
                    filter.equals(TypeFilterProduct.DECREASING) -> {
                        items.toList().sortedByDescending { it.name }
                    }
                    else -> {
                        items
                    }
                }
            }
        }

    fun getProducts() = CoroutineScope(Dispatchers.IO).launch {
        when (val response = productRepository.getProducts()) {
            is Resource.Loading -> {
                _loading.postValue(true)
            }
            is Resource.Success -> {
                _loading.postValue(false)
                _products.postValue(response.data)
                _productsBackupCleanFilter.addAll(response.data)
            }
            is Resource.Error -> {
                _loading.postValue(false)
                _error.postValue(true)
                Log.e("Erro:", response.toString())
            }
        }
    }

    fun setTypeFilterProduct(type: TypeFilterProduct) {
        _filter.value = type
    }

    fun cleanFilters() {
        _filter.value = TypeFilterProduct.NONE
        _products.postValue(_productsBackupCleanFilter)
    }
}