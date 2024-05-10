package com.example.imagesearch_rest_api.retrofit


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NetworkInterface {
    @GET("image")
    suspend fun searchImages(
        @Header("Authorization") apiKey : String,
        @QueryMap param : HashMap<String, String>
    ): ImageResponse
}