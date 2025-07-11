package com.example.cekpanganai.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import java.util.Date

@Entity("history")
data class HistoryEntity(
    @PrimaryKey
    val id: String = NanoIdUtils.randomNanoId(),
    val title_food: String,
    val description_food: String?,
    val image_path: String?,
    val bounding_box: List<BoundingBox>?,
    val total_amount_serving: Int,
    val total_energy: Double?,
    val total_lemak: Double?,
    val total_lemak_jenuh: Double?,
    val total_lemak_tak_jenuh_ganda: Double?,
    val total_lemak_tak_jenuh_tunggal: Double?,
    val total_kolestrol: Double?,
    val total_protein: Double?,
    val total_karbohindrat: Double?,
    val total_serat: Double?,
    val total_gula: Double?,
    val total_sodium: Double?,
    val total_kalium: Double?,
    val created_at: Date,
    val updated_at: Date
)

@Entity("history_foods", primaryKeys = ["food_id", "history_id"])
data class HistoryFoodsEntity(
    val food_id: String,
    val history_id: String,
    val serving_size: Int
)

data class HistoryWithFoods(
    @Embedded val history: HistoryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = HistoryFoodsEntity::class,
            parentColumn = "history_id",
            entityColumn = "food_id"
        )
    )
    val foods: List<FoodTable>
)
