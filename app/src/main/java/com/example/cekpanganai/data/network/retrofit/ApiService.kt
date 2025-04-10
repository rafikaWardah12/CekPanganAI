package com.example.cekpanganai.data.network.retrofit

import com.example.cekpanganai.data.network.response.FoodsResponseDTO
import retrofit2.http.GET

interface ApiService {
    @GET("food")
    suspend fun getFood(): FoodsResponseDTO
}
