package com.example.imagesearch_rest_api.retrofit

import com.example.imagesearch_rest_api.data.ApiUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiUrl.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: NetworkInterface = retrofit.create(NetworkInterface::class.java)
}