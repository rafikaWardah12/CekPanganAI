package com.example.cekpanganai.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cekpanganai.data.database.entity.UserEntity
import java.util.concurrent.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1 ")
    suspend fun getUserById(id: String): UserEntity
}