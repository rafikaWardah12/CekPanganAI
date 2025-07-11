package com.example.cekpanganai.presentation.result

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cekpanganai.common.navigation_utils.AppNavigator
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.data.database.FoodDataLocal
import com.example.cekpanganai.data.database.FoodDataLocal2
import com.example.cekpanganai.data.database.dao.FoodDao
import com.example.cekpanganai.data.database.dao.HistoryDao
import com.example.cekpanganai.data.database.entity.FoodItemEntity
import com.example.cekpanganai.data.database.entity.FoodTable
import com.example.cekpanganai.data.database.entity.FoodsEntity
import com.example.cekpanganai.data.database.entity.HistoryEntity
import com.example.cekpanganai.data.database.entity.HistoryWithFoods
import com.example.cekpanganai.data.database.entity.ServingEntity
import com.example.cekpanganai.domain.repository.ResultRepository
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.ui.utils.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val foodRepository: ResultRepository,
    private val foodDao: FoodDao,
    private val historyDao: HistoryDao
) : ViewModel() {
    fun onNavigateBack() = appNavigator.tryNavigateTo(Destination.DashboardScreen())
    fun onNavigateToHistory() = appNavigator.tryNavigateTo(Destination.HistoryScreen())

    //Get Food Item in one Screen
    private val _foodState = MutableStateFlow(ResultState())
    val foodState: StateFlow<ResultState> = _foodState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

    //Menyimpan hasil edit nya bukan dari database
    private val _editedAmounts = mutableStateMapOf<String, Int>()
    val editedAmounts: Map<String, Int> get() = _editedAmounts

    private val _clsNames = mutableStateListOf<String>("null")
    val clsNames: List<String> get() = _clsNames

    fun setClsNames(names: List<String>) {
        _clsNames.clear()
        _clsNames.addAll(names)
    }

    //    val ids = arrayOf("1", "2", "4")
    val ids: Array<String> get() = clsNames.toTypedArray()

    init {
        loadLocalSuggestions()
    }

    suspend fun getFood(event: ResultEvent) {

        when (event) {
            ResultEvent.AddFoodItemDialog -> TODO()
            is ResultEvent.DeleteFood -> {
                viewModelScope.launch {
                    foodDao.deleteFoods(event.food)
                }
            }

            ResultEvent.HideDialog -> {
                _foodState.update {
                    it.copy(isAddFoodItem = false)
                }
            }

            is ResultEvent.SaveResult -> {
                if (_foodState == null) {
                    return
                }
                viewModelScope.launch {
                    foodDao.insertFood(event.food)
                }
                _foodState.update {
                    it.copy(isAddFoodItem = false)
                }
            }

            is ResultEvent.ShowFood -> {
                viewModelScope.launch {
//                    foodDao.getAllFoodItem().collectLatest { foodAllItem ->
                    val foodList = foodDao.getFoodData(ids).collect { foodData ->
                        _foodState.update {
                            it.copy(
                                foodServings = foodData.map {
                                    FoodOriginalServing(
                                        id = it.id,
                                        serving = it.metric_serving_amount ?: 0
                                    )
                                },
                                foodData = foodData,
                                isLoading = false,
                                isError = false,
                                message = Message.Success(foodData)
                            )
                        }
                    }
                    Log.e("Food", if (foodList != null) "Cek Data" else "Data Masuk")
                }
            }

            else -> {
                _foodState.value = ResultState(isError = true, isLoading = false)
            }
        }
    }

    fun getTotalNutrition(foods: List<FoodTable>) {
        viewModelScope.launch {
//            val selectedFoods = _foodState.value.foodData.filter { it.id in ids }
            val totalNutrition = foods.reduce() { acc, foodTable ->
                acc.copy(
                    metric_serving_amount = (acc.metric_serving_amount
                        ?: 0) + (foodTable.metric_serving_amount ?: 0),
                    energy = (acc.energy ?: 0.0) + (foodTable.energy ?: 0.0),
                    karbohindrat = (acc.karbohindrat ?: 0.0) + (foodTable.karbohindrat ?: 0.0),
                    protein = (acc.protein ?: 0.0) + (foodTable.protein ?: 0.0),
                    lemak = (acc.lemak ?: 0.0) + (foodTable.lemak ?: 0.0),
                    lemak_jenuh = (acc.lemak_jenuh ?: 0.0) + (foodTable.lemak_jenuh ?: 0.0),
                    lemak_tak_jenuh_ganda = (acc.lemak_tak_jenuh_ganda
                        ?: 0.0) + (foodTable.lemak_tak_jenuh_ganda ?: 0.0),
                    lemak_tak_jenuh_tunggal = (acc.lemak_tak_jenuh_tunggal
                        ?: 0.0) + (foodTable.lemak_tak_jenuh_tunggal ?: 0.0),
                    kolestrol = (acc.kolestrol ?: 0.0) + (foodTable.kolestrol ?: 0.0),
                    gula = (acc.gula ?: 0.0) + (foodTable.gula ?: 0.0),
                    sodium = (acc.sodium ?: 0.0) + (foodTable.sodium ?: 0.0),
                    kalium = (acc.kalium ?: 0.0) + (foodTable.kalium ?: 0.0),
                    serat = (acc.serat ?: 0.0) + (foodTable.serat ?: 0.0)
                )
            }
            _foodState.update {
                it.copy(
                    totalNutrition = totalNutrition
                )
            }
        }
        Log.d("NutritionDebug", "All IDs: ${_foodState.value.foodData.map { it.id }}")
        Log.d("NutritionDebug", "Selected IDs: ${ids.toList()}")
    }

    fun recalculateNutritionAndUpdate(
        id: String,
        newAmount: Int,
//        originalAmount: Int?,
        isPortion: Boolean? = false
    ) {
        viewModelScope.launch {
            val foodItem = foodDao.getFoodById(id)
            foodItem?.let {

                var amount = 1
                if (isPortion == true) {
                    amount = it.metric_serving_amount!! * newAmount
                } else {
                    amount = newAmount
                }
                val updatedFood = recalculateNutrition(
                    originalAmount = it.metric_serving_amount,
                    newAmount = amount,
                    foodTable = it
                )

                val currentFoods = _foodState.value.foodData.map {
                    if (it.id == id) {
                        updatedFood
                    } else {
                        it;
                    }
                };
                getTotalNutrition(currentFoods)

                // Update state jika perlu
                _foodState.update { state ->
                    state.copy(foodData = currentFoods)
                }
            }
        }
    }


    fun recalculateNutrition(
        originalAmount: Int?,
        newAmount: Int,
        foodTable: FoodTable
    ): FoodTable {
        if (originalAmount == null) return foodTable

        val scaleFactor = newAmount.toFloat() / originalAmount.toDouble()

        return foodTable.copy(
            metric_serving_amount = newAmount,
            energy = foodTable.energy?.times(scaleFactor),
            lemak = foodTable.lemak?.times(scaleFactor),
            lemak_jenuh = foodTable.lemak_jenuh?.times(scaleFactor),
            lemak_tak_jenuh_ganda = foodTable.lemak_tak_jenuh_ganda?.times(scaleFactor),
            lemak_tak_jenuh_tunggal = foodTable.lemak_tak_jenuh_tunggal?.times(scaleFactor),
            kolestrol = foodTable.kolestrol?.times(scaleFactor),
            protein = foodTable.protein?.times(scaleFactor),
            karbohindrat = foodTable.karbohindrat?.times(scaleFactor),
            serat = foodTable.serat?.times(scaleFactor),
            gula = foodTable.gula?.times(scaleFactor),
            sodium = foodTable.sodium?.times(scaleFactor),
            kalium = foodTable.kalium?.times(scaleFactor),
            updated_at = Date()  // Update the timestamp
        )
    }

    fun deleteFoodDatabase(id: String) {
        viewModelScope.launch {
            foodRepository.deleteFoodbyId(id)
            _foodState.update {
                it.copy(
                    foodData = it.foodData.filterNot { foodTable -> foodTable.id == id }
                )
            }
        }
    }

    fun deleteFoodById(id: String) {
        _foodState.update { state ->
            val updatedFoodData = state.foodData.filterNot { foodTable -> foodTable.id == id }
            state.copy(foodData = updatedFoodData)
        }
    }

    fun editFood(oldId: String, selectedId: String) {
        viewModelScope.launch {
            _foodState.update { state ->
                val newFoodData = state.foodData.map {
                    if (it.id == oldId) {
                        val newData = foodDao.getFoodById(selectedId);
                        newData ?: it
                    } else {
                        it
                    }
                }
                state.copy(foodData = newFoodData)
            }
        }

    }

    fun loadLocalSuggestions() {
        viewModelScope.launch {
            foodDao.getAllFoodItem().collect() { suggestions ->
                _foodState.update { state ->
                    state.copy(
                        localSuggestions = suggestions.map { Option(it.id, it.food_name ?: "") }
                    )
                }
            }
//            foodRepository.getLocalFoodSuggestions().collect() { suggestions ->
//                _foodState.update {
//                    it.copy(
//                        localSuggestions = suggestions, // pastikan ini ada di ResultState
//                        isLoading = false,
//                        isError = false
//                    )
//                }
//                Log.e("Food", "Suggestions loaded: $suggestions")
//            }
        }
    }

    fun refreshSuggestionsFromAPI(query: String = "") {
        viewModelScope.launch {
            foodRepository.fetchSuggestionsFromApi(query).collectLatest { message ->
                when (message) {
                    is Message.Success -> {
                        foodRepository.saveFoodItemsToLocal(message.data)
                        loadLocalSuggestions()

                        _foodState.update {
                            it.copy(
                                isLoading = false,
                                isError = false
                            )
                        }
                    }

                    is Message.Error -> {
                        _foodState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                message = message
                            )
                        }
                    }

                    is Message.Loading -> {
                        _foodState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    suspend fun addHistory(
        title_food: String,
        description_food: String,
        image_path: String,
        bounding_box: List<BoundingBox>?,
    ) {
        viewModelScope.launch {
            val history = HistoryEntity(
                title_food = title_food,
                description_food = description_food,
                image_path = image_path,
                bounding_box = bounding_box,
                total_amount_serving = foodState.value.totalNutrition?.metric_serving_amount ?: 0,
                total_energy = foodState.value.totalNutrition?.energy,
                total_lemak = foodState.value.totalNutrition?.lemak,
                total_lemak_jenuh = foodState.value.totalNutrition?.lemak_jenuh,
                total_lemak_tak_jenuh_ganda = foodState.value.totalNutrition?.lemak_tak_jenuh_ganda,
                total_lemak_tak_jenuh_tunggal = foodState.value.totalNutrition?.lemak_tak_jenuh_tunggal,
                total_kolestrol = foodState.value.totalNutrition?.kolestrol,
                total_protein = foodState.value.totalNutrition?.protein,
                total_karbohindrat = foodState.value.totalNutrition?.karbohindrat,
                total_serat = foodState.value.totalNutrition?.serat,
                total_gula = foodState.value.totalNutrition?.gula,
                total_sodium = foodState.value.totalNutrition?.sodium,
                total_kalium = foodState.value.totalNutrition?.kalium,
                created_at = Date(),
                updated_at = Date(),
            )
            historyDao.insertHistoryWithFoods(
                history = history,
                foods = foodState.value.foodData
            )
        }
    }

    suspend fun seedInitialData() {
        val foodDataLocal = FoodDataLocal()
        val foodDataLocal2 = FoodDataLocal2()
        viewModelScope.launch {
//            foodDao.insertFood(foodDataLocal.foodEntity1)
//            foodDao.insertFood(foodDataLocal.foodEntity2)

//            foodDataLocal.foodItem1?.let { foodDao.insertFoodById(it) }
//            foodDataLocal.foodItem2?.let { foodDao.insertFoodById(it) }
//            foodDataLocal.foodItem3?.let { foodDao.insertFoodById(it) }

            foodDao.insertFoodTable(foodDataLocal2.dummyFood1)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood2)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood3)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood4)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood5)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood6)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood7)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood8)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood9)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood10)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood11)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood12)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood13)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood14)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood15)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood16)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood17)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood18)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood19)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood20)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood21)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood22)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood23)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood24)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood25)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood26)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood27)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood28)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood29)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood30)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood31)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood32)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood33)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood34)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood35)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood36)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood37)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood38)
            foodDao.insertFoodTable(foodDataLocal2.dummyFood39)

            _foodState.update {
                it.copy(
                    isLoading = false,
                    isError = false,
//                    listFood = listOf(foodDataLocal.foodEntity1, foodDataLocal.foodEntity2)
                )
            }
        }
    }

    fun AddFood(id: String) {
        viewModelScope.launch {
            val food: FoodTable? = foodDao.getFoodById(id)

            if (food != null) {
                _foodState.update {
                    it.copy(
                        foodData = it.foodData + food,
                        foodServings = it.foodServings + FoodOriginalServing(
                            id = food.id,
                            serving = food.metric_serving_amount ?: 0
                        )
                    )
                }
                getTotalNutrition(_foodState.value.foodData + food)
            }
        }
    }
}


data class FoodItemState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val foodItem: FoodItemEntity? = null,
)