package com.example.lab09.api

import com.example.lab09.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {

    // Función que trae todos los posts (lista)
    @GET("posts")
    suspend fun getUserPosts(): List<PostModel>

    // Función que trae un post específico por ID
    @GET("posts/{id}")
    suspend fun getUserPostById(@Path("id") id: Int): PostModel
}