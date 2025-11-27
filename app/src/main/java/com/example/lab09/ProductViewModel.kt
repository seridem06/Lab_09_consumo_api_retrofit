package com.example.lab09

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab09.ProductModel
import com.example.lab09.ProductApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ProductApiService::class.java)

    var listaProductos = mutableStateListOf<ProductModel>()
    var productoSeleccionado = mutableStateOf<ProductModel?>(null)

    init {
        obtenerProductos()
    }

    fun obtenerProductos() {
        viewModelScope.launch {
            listaProductos.clear()
            val resultado = api.getAllProducts()
            listaProductos.addAll(resultado)
        }
    }

    fun obtenerProductoPorId(id: Int) {
        viewModelScope.launch {
            productoSeleccionado.value = api.getProductById(id)
        }
    }
}