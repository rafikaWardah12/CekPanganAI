package com.example.cekpanganai.presentation.result

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cekpanganai.MainActivity
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.dto.toDataItemResult
import com.example.cekpanganai.data.network.fakeData.FakeDataNutrition
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomCardResult
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.detect.DetectViewModel
import com.example.cekpanganai.presentation.detect.camera.BoundingBox
import com.example.cekpanganai.presentation.detect.captureOnlyImageCanvas
import com.example.cekpanganai.presentation.result.component.CardEditName
import com.example.cekpanganai.presentation.result.component.CardEditPortion
import com.example.cekpanganai.presentation.result.component.CardTotalResult
import com.example.cekpanganai.presentation.result.component.HeaderResult
import com.example.cekpanganai.presentation.result.component.SearchFoodDialog
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.BlueSecondary
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.SecondaryBackground
import com.example.cekpanganai.ui.theme.TextPlaceHolder
import com.example.cekpanganai.ui.theme.TextSecondary
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.utils.decodeBitmapFromUriString
import com.example.cekpanganai.ui.utils.exportImageToDownloads
import com.example.cekpanganai.ui.utils.formatDate
import com.example.cekpanganai.ui.utils.moveFileToPermanentFolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    resultViewModel: ResultViewModel = hiltViewModel(),
    latestIntent: Intent?,
    detectViewModel: DetectViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val context = LocalContext.current as Activity

    val detectResult by detectViewModel.detectionState.collectAsState()
    val imageUriString = detectResult.imageUri
    val boxList = detectResult.boxes

    var newImageUriString by remember { mutableStateOf<Uri?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val resultState by resultViewModel.foodState.collectAsState()
    val items = resultState.foodData

    val totalNutrition = resultState.totalNutrition?.toDataItemResult() ?: emptyList()

    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNestedScrollInteropConnection()
    var showEditTitle by remember { mutableStateOf(false) }
    var showEditPortionId by remember { mutableStateOf<String?>(null) }
    var showEditFood by remember { mutableStateOf<String?>(null) }
    var showAddFood by remember { mutableStateOf(false) }

    var deleteFoodId by remember { mutableStateOf<String?>(null) }

    var titleFood by remember { mutableStateOf<String>("") }
    var descriptionFood by remember { mutableStateOf<String>("") }

    var saveToHistory by remember { mutableStateOf(false) }

    if (showEditTitle) {
        CardEditName(
            onDismiss = { showEditTitle = false },
            onSave = { title, description ->
                titleFood = title
                descriptionFood = description
                ; showEditTitle = false;
            },
            titleFood = titleFood,
            descriptionFood = descriptionFood
        )
    }
    if (showEditPortionId != null) {
        val foodItem = resultState.foodData.find { it.id == showEditPortionId }
        val servingOriginal = resultState.foodServings.find { it.id == showEditPortionId }
        CardEditPortion(
            onDismiss = { showEditPortionId = null },
            originalServingPortionAmount = servingOriginal?.serving ?: 100,
            initialValue = foodItem?.metric_serving_amount?.toString() ?: "0",
            onSave = { inputValue, isPortion ->
                foodItem?.let {
                    resultViewModel.recalculateNutritionAndUpdate(
                        id = it.id,
//                        originalAmount = it.metric_serving_amount,
                        newAmount = inputValue,
                        isPortion = isPortion
                    )
                }
            },
            showDialog = true
        )
    }

    if (showEditFood != null) {
        SearchFoodDialog(
            showDialog = true,
            onDismiss = { showEditFood = null },
            onSaveEdit = { newId ->
                resultViewModel.editFood(showEditFood!!, newId);
                showEditFood = null
                Log.d("id-result", "OldId: $showEditFood. NewId: $newId")
            }, onDelete = {
                deleteFoodId = showEditFood
                showEditFood = null
            }, isEditFood = true,
            suggestions = resultState.localSuggestions.filterNot { sugg -> resultState.foodData.any { it.id == sugg.value } }
        )
    }

    if (showAddFood) {
        SearchFoodDialog(
            showDialog = true,
            onDismiss = { showAddFood = false },
            onSave = { id -> resultViewModel.AddFood(id) },
            onDelete = { /*TODO*/ },
            suggestions = resultState.localSuggestions.filterNot { sugg -> resultState.foodData.any { it.id == sugg.value } },
        )
    }
    LaunchedEffect(detectResult) {
        Log.d("DEBUG", "imageUriString = ${detectResult.imageUri}")
        Log.d("DEBUG", "BoundingBoxList = ${detectResult.boxes}")
    }

    LaunchedEffect(boxList) {
        if (!boxList.isNullOrEmpty()) {
            val clsNames = boxList.map { it.clsName }
            resultViewModel.setClsNames(clsNames)
            Log.d("intent", "clsName from intent(boxList): $boxList")
            Log.d("intent", "clsName from intent(clasName): $clsNames")
        }
    }

    LaunchedEffect(true) {
        resultViewModel.getFood(ResultEvent.ShowFood)
        delay(500)
        titleFood = resultViewModel.foodState.value.foodData.firstOrNull()?.food_name ?: "No Food"
        descriptionFood =
            resultViewModel.foodState.value.foodData.joinToString(separator = ", ") { description ->
                description.food_name ?: ""
            }
        Log.e(
            "Food",
            (if (items.isEmpty()) "Data Kosong3" else resultState.foodData.size).toString()
        )
    }

    LaunchedEffect(deleteFoodId) {
        deleteFoodId?.let { id ->
            // Hapus item dari UI saja, tanpa menghapus dari database
            resultViewModel.deleteFoodById(id)

            // Update total nutrisi berdasarkan data yang sudah ada
            val food = resultViewModel.foodState.value.foodData
            resultViewModel.getTotalNutrition(food)

            // Reset deleteFoodId setelah penghapusan
            deleteFoodId = null
        }
    }

    LaunchedEffect(showEditFood) {
        delay(600)
        val food = resultViewModel.foodState.value.foodData
        resultViewModel.getTotalNutrition(food)
    }

    Surface() {
        Column {
            CustomTopBar(
                title = stringResource(id = R.string.result),
                navigateBack = { resultViewModel.onNavigateBack() },
                isFilled = true
            )
            Box(Modifier.nestedScroll(nestedScrollConnection)) {
                Column(Modifier.verticalScroll(scrollState)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .background(color = MaterialTheme.colorScheme.surface)
                            .graphicsLayer {
                                alpha =
                                    1f - (scrollState.value.toFloat() / scrollState.maxValue / 2)
                                translationY = 0.5f * scrollState.value
                            }
                    ) {
                        if (imageUriString != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUriString),
                                contentDescription = stringResource(
                                    id = R.string.result
                                ),
                                modifier
                                    .fillMaxSize()
                                    .background(color = PrimaryBackground),
                                alignment = Alignment.Center,
                                contentScale = ContentScale.FillWidth
                            )
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height

                                for (box in boxList) {
                                    val left = box.x1 * canvasWidth
                                    val top = box.y1 * canvasHeight
                                    val right = box.x2 * canvasWidth
                                    val bottom = box.y2 * canvasHeight

                                    drawRect(
                                        color = Color.Red,
                                        topLeft = Offset(left, top),
                                        size = Size(right - left, bottom - top),
                                        style = Stroke(width = 4f)
                                    )

                                    drawContext.canvas.nativeCanvas.apply {
                                        drawText(
                                            box.clsName,
                                            left,
                                            top - 10f,
                                            Paint().apply {
                                                color = android.graphics.Color.RED
                                                textSize = 40f
                                                style = Paint.Style.FILL
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Card(
                        modifier = modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.extraLarge),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    )
                    {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Padding.medium, vertical = Padding.large),
                            verticalArrangement = Arrangement.spacedBy(Spacing.small)
                        ) {
                            Column() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(Spacing.small),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable { showEditTitle = true }
                                    ) {
                                        Text(
                                            text = titleFood,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_edit),
                                            contentDescription = stringResource(
                                                id = R.string.edit
                                            ),
                                            tint = BluePrimary,
                                        )
                                    }
                                    Row {
                                        Text(
                                            text = if (items.isNotEmpty()) formatDate(items.first().updated_at) else "loading",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                Text(
                                    text = descriptionFood ?: "Null"
                                )
                            }
                            HorizontalDivider(thickness = 0.5.dp, color = TextPlaceHolder)
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = modifier.fillMaxWidth()
                            ) {
                                CustomButton(
                                    text = stringResource(id = R.string.add),
                                    onClick = { showAddFood = true },
                                    buttonColor = BlueSecondary,
                                    contentColor = BluePrimary,
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_add),
                                            contentDescription = stringResource(id = R.string.add),
                                            modifier = Modifier.size(18.dp),
                                        )
                                    },
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                            items.forEach {
                                CustomCardResult(
                                    header = {
                                        HeaderResult(
                                            label = it.food_name ?: "Null",
                                            serving = it.metric_serving_amount.toString(),
                                            onClickPortion = { showEditPortionId = it.id },
                                            onClickFood = { showEditFood = it.id },
                                            notAction = false,
                                        )
                                    },
                                    itemsShow = it.toDataItemResult()
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(300.dp))
                }
                CardTotalResult(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .nestedScroll(nestedScrollConnection),
                    onSave = {
                        imageUriString?.let {
                            newImageUriString = moveFileToPermanentFolder(context, it)
                            Log.d("moved", "ImageUri lama: $imageUriString")
                            Log.d("moved", "File image sudah dipindah $newImageUriString")
                        }
                        if (newImageUriString != null) {
                            coroutineScope.launch {
                                resultViewModel.addHistory(
                                    titleFood,
                                    descriptionFood,
                                    newImageUriString.toString(),
                                    bounding_box = boxList,
                                )
                                resultViewModel.onNavigateToHistory()
                            }
                        }
                    },
                    itemsShow = totalNutrition,
                    metricServingAmount = resultState.totalNutrition?.metric_serving_amount
                        ?: 0,
                    noAction = false,
                )
                Log.e("history", saveToHistory.toString())
            }
        }
    }
}


@Preview
@Composable
private fun test() {
    CekPanganAITheme {
//        ResultScreen(resultState = )
    }
}