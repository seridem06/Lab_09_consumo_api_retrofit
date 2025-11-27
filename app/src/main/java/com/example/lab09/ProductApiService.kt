package com.example.lab09

import com.example.lab09.ProductModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getAllProducts(): List<ProductModel>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductModel
}