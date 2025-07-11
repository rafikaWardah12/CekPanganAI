package com.example.cekpanganai.data.network.retrofit

import androidx.room.Query
import com.example.cekpanganai.data.network.response.FoodItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //Food By Id
    @GET("/foods/{id}")
    suspend fun getFoodById(
        @Path("id")
        id: String
    ): Response<FoodItemResponse>

    @GET("/foods")
    suspend fun searchFood(
        @retrofit2.http.Query("query") query: String
    ): Response<List<FoodItemResponse>>
}
