package com.example.cekpanganai.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val age: Int,
    val imgProfile: String?,
    val bmi: Double?,
    val gender: String,
    val weight: Double?,
    val height: Double?,
    val createdAt: Date?,
    val updatedAt: Date?,
)