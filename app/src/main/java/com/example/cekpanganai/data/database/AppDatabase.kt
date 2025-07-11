package com.example.cekpanganai.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cekpanganai.data.database.dao.FoodDao
import com.example.cekpanganai.data.database.dao.HistoryDao
import com.example.cekpanganai.data.database.dao.UserDao
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.data.database.entity.HistoryFoodsEntity
import com.example.cekpanganai.data.database.entity.ServingEntity
import com.example.cekpanganai.data.database.entity.UserEntity
import com.example.cekpanganai.ui.utils.Converters


@Database(
    entities = [FoodItemEntity::class, FoodsEntity::class, FoodTable::class, HistoryEntity::class, HistoryFoodsEntity::class, UserEntity::class],
    exportSchema = false,
    version = 13
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFoodDao(): FoodDao
    abstract fun getHistoryDao(): HistoryDao
    abstract fun getUserDao(): UserDao

}

