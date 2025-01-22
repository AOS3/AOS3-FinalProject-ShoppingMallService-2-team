package com.nemodream.bangkkujaengi.admin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemodream.bangkkujaengi.admin.data.repository.AdminProductRepository
import com.nemodream.bangkkujaengi.customer.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminProductViewModel @Inject constructor(
    private val adminProductRepository: AdminProductRepository,
): ViewModel() {
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    fun loadProducts() = viewModelScope.launch {
        runCatching {
            val products = adminProductRepository.getProducts()
            val productsWithUrls = products.map { product ->
                val imageUrls = product.images.map { path ->
                    adminProductRepository.getImageUrl(path)
                }
                product.copy(images = imageUrls)
            }
            _products.value = productsWithUrls
        }
    }

    fun deleteProduct(productId: String) = viewModelScope.launch {
        runCatching {
            adminProductRepository.deleteProduct(productId)
        }.onSuccess {
            // 삭제된 데이터를 리스트에서 수정한다.
            // delete가 true 만 필터링
            val updatedProducts = _products.value?.filter { it.productId != productId } ?: emptyList()
            _products.value = updatedProducts
        }.onFailure {
            it.printStackTrace()
        }
    }
}