package com.example.cekpanganai.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "food_table")
data class FoodTable(
    @PrimaryKey
    val id: String,
    val food_name: String?,
    var metric_serving_amount: Int?,
    val energy: Double?,
    val lemak: Double?,
    val lemak_jenuh: Double?,
    val lemak_tak_jenuh_ganda: Double?,
    val lemak_tak_jenuh_tunggal: Double?,
    val kolestrol: Double?,
    val protein: Double?,
    val karbohindrat: Double?,
    val serat: Double?,
    val gula: Double?,
    val sodium: Double?,
    val kalium: Double?,
    val created_at: Date?,
    val updated_at: Date?,
);