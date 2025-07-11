package com.example.cekpanganai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.cekpanganai.data.database.AppDatabase
import com.example.cekpanganai.data.database.dao.FoodDao
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.database.entity.ServingEntity
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var foodDao: FoodDao
    private lateinit var database: AppDatabase

    // Reaktif terhadap perubahan intent
    private val intentState = mutableStateOf<Intent?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intentState.value = intent
        enableEdgeToEdge()
        setContent {
//            MainScreen()
            MainScreen(intentState)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("MainActivity", "onNewIntent called with: ${intent?.getStringExtra("navigateTo")}")
        intentState.value = intent
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CekPanganAITheme {
    }
}