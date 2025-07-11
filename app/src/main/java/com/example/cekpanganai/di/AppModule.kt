package com.example.cekpanganai.di

import android.content.Context
import androidx.room.Room
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.AppNavigatorImpl
import com.example.cekpanganai.data.database.AppDatabase
import com.example.cekpanganai.data.database.dao.FoodDao
import com.example.cekpanganai.data.database.dao.HistoryDao
import com.example.cekpanganai.data.database.dao.UserDao
import com.example.cekpanganai.data.network.retrofit.ApiService
import com.example.cekpanganai.data.repository.ResultRepositoryImpl
import com.example.cekpanganai.domain.repository.ResultRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//interface AppModule {
//
//    @Singleton
//    @Binds
//    fun bindAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator
//
//}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

//    @Binds
//    @Singleton
//    abstract fun bindAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator

    companion object {
        @Provides
        @Singleton
        fun provideAppNavigator(): AppNavigator = AppNavigatorImpl()

        @Provides
        @Singleton
        fun provideResultRepository(
            appDatabase: AppDatabase,
            apiService: ApiService
        ): ResultRepository {
            return ResultRepositoryImpl(appDatabase.getFoodDao(), apiService)
        }

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "food_database"
            ).fallbackToDestructiveMigration().build()
        }

        @Provides
        fun provideFoodDao(appDatabase: AppDatabase): FoodDao {
            return appDatabase.getFoodDao()
        }

        @Provides
        fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
            return appDatabase.getHistoryDao()
        }

        @Provides
        fun provideUserDao(appDatabase: AppDatabase): UserDao {
            return appDatabase.getUserDao()
        }
    }
}
