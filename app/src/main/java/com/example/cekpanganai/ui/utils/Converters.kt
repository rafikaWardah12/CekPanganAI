package com.example.cekpanganai.ui.utils

import androidx.room.TypeConverter
import com.example.cekpanganai.data.database.entity.FoodImageEntity
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.ServingEntity
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Date

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromFoodItemList(foodItems: List<FoodItemEntity>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FoodItemEntity>>() {}.type
        return gson.toJson(foodItems, type)
    }

    @TypeConverter
    fun toFoodItemList(foodItemsString: String?): List<FoodItemEntity>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FoodItemEntity>>() {}.type
        return gson.fromJson(foodItemsString, type)
    }

    @TypeConverter
    fun fromServingList(value: List<ServingEntity?>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toServingList(value: String?): List<ServingEntity?>? {
        val listType = object : TypeToken<List<ServingEntity?>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromFoodImageList(value: List<FoodImageEntity?>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toFoodImageList(value: String?): List<FoodImageEntity?>? {
        val listType = object : TypeToken<List<FoodImageEntity?>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBoundingBoxList(value: List<BoundingBox>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBoundingBoxList(value: String?): List<BoundingBox>? {
        return if (value == null) null else gson.fromJson(
            value,
            object : TypeToken<List<BoundingBox>>() {}.type
        )
    }
}
