package com.example.cekpanganai.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.cekpanganai.data.database.dao.FoodDao
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.network.dto.toFoodItem
import com.example.cekpanganai.data.network.dto.toFoodTable
import com.example.cekpanganai.data.network.response.FoodItemResponse
import com.example.cekpanganai.data.network.retrofit.ApiService
import com.example.cekpanganai.domain.repository.ResultRepository
import com.example.cekpanganai.ui.utils.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ResultRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val apiService: ApiService,
) : ResultRepository {
    override fun getLocalFoodSuggestions(): Flow<List<String>> {
        return foodDao.getAllFoodItem().map { foodTableList ->
            Log.e("Food", "Raw list: $foodTableList")
            foodTableList.map { it.food_name ?: "" }
        }
    }

    override fun fetchSuggestionsFromApi(query: String): Flow<Message<List<FoodTable>>> =
        flow {
            val response = apiService.searchFood(query)
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList<FoodItemResponse>()

                // Konversi FoodItemResponse ke FoodTable
                val foodTables = body.map { it.toFoodTable() }

                emit(Message.Success(foodTables))
            } else {
                emit(Message.Error("Failed with status code: ${response.code()}"))
            }
        }
            .catch {
                when (it) {
                    is HttpException -> emit(Message.Error(it.localizedMessage ?: "Unknown Error"))
                    is IOException -> emit(Message.Error(it.localizedMessage ?: "No Internet"))
                    else -> emit(Message.Error(it.localizedMessage ?: "Unknown Error Occurred"))
                }
            }
            .flowOn(Dispatchers.IO)


//    override fun fetchSuggestionsFromApi(query: String): Flow<Message<List<FoodItemResponse>>> =
//        flow {
//            val response = apiService.searchFood(query)
//            if (response.isSuccessful) {
//                val body = response.body() ?: emptyList()
//                emit(Message.Success(body))
//            } else {
//                emit(Message.Error("Failed with status code: ${response.code()}"))
//            }
//        }.catch {
//            when (it) {
//                is HttpException -> emit(Message.Error(it.localizedMessage ?: "Unknown Error"))
//                is IOException -> emit(Message.Error(it.localizedMessage ?: "No Internet"))
//                else -> emit(Message.Error(it.localizedMessage ?: "Unknown Error Occurred"))
//            }
//        }.flowOn(Dispatchers.IO)

    override suspend fun saveFoodItemsToLocal(foodItems: List<FoodTable>) {
        foodItems.forEach {
            foodDao.insertFoodTable(it)
        }
    }

    override suspend fun deleteFoodbyId(id: String) {
        foodDao.deleteFoodById(id)
    }
}


//    override fun getFoodById(id: String): Flow<Message<FoodItemEntity>> = flow {
//        val localResult = foodDao.getFoodById(id)
//        if (localResult != null) {
//            localResult.let {
//                emit(Message.Success(it))
//            }
//        } else {
//            emit(Message.Error("Food not found in database local"))
//            val apiResponse = apiService.getFoodById(id)
//            if (apiResponse.isSuccessful) {
//                val body = apiResponse.body()
//                if (body != null) {
//                    //Mapping dari data response ke entity
//                    val entity = body.toFoodItem()
//                    //Simpan di database local
//                    foodDao.insertFoodById(entity)
//                    Message.Success(entity)
//                    return@flow
//                }
//            }
//        }
//    }.catch {
//        when (it) {
//            is HttpException -> emit(Message.Error(it.localizedMessage ?: "Unknown Error"))
//            is IOException -> emit(Message.Error(it.localizedMessage ?: "No Internet"))
//            else -> emit(Message.Error(it.localizedMessage ?: "Unknown Error Occurred"))
//        }
//    }
//}