package com.example.cekpanganai.presentation.result.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomDialog
import com.example.cekpanganai.presentation.component.HeaderCard
import com.example.cekpanganai.presentation.result.Option
import com.example.cekpanganai.presentation.result.ResultViewModel
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.TextSecondary
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing


@Composable
fun SearchFoodDialog(
    showDialog: Boolean,
    initialValue: String = "",
    suggestions: List<Option> = listOf(Option("0", "data kosong")),
    onDismiss: () -> Unit,
    onSave: (String) -> Unit = {},
    onSaveEdit: (String) -> Unit = { _ -> },
    onDelete: () -> Unit,
    isEditFood: Boolean = false,
) {
    var input by remember { mutableStateOf(initialValue) }
    var showEditPortionDialog by remember { mutableStateOf(false) }
    var showDeleteItem by remember { mutableStateOf(false) }

    var oldId by remember { mutableStateOf(initialValue) }
    var selectedId by remember { mutableStateOf(initialValue) }

    var selectedFoodId by remember { mutableStateOf<String?>(null) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(
                                id = R.string.close
                            ),
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Search Food",
                            tint = BluePrimary
                        )
                        Spacer(modifier = Modifier.width(Spacing.medium))
                        Text(
                            text = stringResource(id = R.string.add_food),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            containerColor = Color.White,
            text = {
                Column {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("Cari...") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val filteredSuggestions = suggestions.filter {
//                        it.contains(input, ignoreCase = true)
                        it.label.contains(input, ignoreCase = true)
                    }

                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 200.dp)
                    ) {
                        items(filteredSuggestions.take(10)) { suggestion ->
                            Text(
                                text = suggestion.label,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        input = suggestion.label
                                        selectedFoodId = suggestion.value
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }
                    if (suggestions.none { it.label.contains(input, ignoreCase = true) }) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = stringResource(id = R.string.not_found_database),
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            TextButton(onClick = { /* reload action */ }) {
                                Text("Muat Ulang?", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                if (isEditFood) {
                    Column {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(Padding.medium),
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomButton(
                                text = "Hapus Item",
                                onClick = { showDeleteItem = true },
                                isWarning = true,
                                isRounded = true,
                                isWide = true
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CustomButton(
                                    text = "Batal",
                                    onClick = { onDismiss() },
                                    isRounded = true,
                                    isOutline = true,
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                CustomButton(
                                    text = "Simpan",
                                    onClick = {
                                        if (selectedFoodId != null) {
                                            onSaveEdit(selectedFoodId!!)
                                            onDismiss()
                                        }
                                    },
                                    isRounded = true,
                                    isWide = true
                                )
                            }
                            Spacer(modifier = Modifier.height(Padding.medium))
                        }
//                        EditFood(
//                            onDismiss = onDismiss,
//                            onDeleteRequest = { showDeleteItem = true },
//                            onSaveEditRequest = { oldId = , newId}
//                        )
                    }
                } else {
                    Column {
                        CustomButton(
                            text = stringResource(id = R.string.next),
                            onClick = {
                                if (selectedFoodId != null) {
                                    onSave(selectedFoodId!!)
                                    onDismiss()
                                }
                            },
                            isRounded = true,
                            isWide = true
                        )
                    }
                }
            }
        )
    }

//    if (showEditPortionDialog && selectedFoodId != null) {
//        CardEditPortion(
//            onDismiss = { showEditPortionDialog = false },
//            onSave = { portion ->
//                onSavePortion(selectedFoodId!!, portion, true) // ← tambahkan boolean true
//                showEditPortionDialog = false
//                onDismiss()
//            },
//            showDialog = true
//        )
//    }

    if (showDeleteItem) {
        CardDelete(
            showDialog = true,
            onDismiss = { showDeleteItem = false },
            onDelete = {
                showDeleteItem = false
                onDelete()
            }
        )
    }
}

@Composable
internal fun EditFood(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDeleteRequest: () -> Unit,
    onSaveEditRequest: (String, String) -> Unit = { _, _ -> }
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Padding.medium),
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            text = "Hapus Item",
            onClick = { onDeleteRequest() },
            isWarning = true,
            isRounded = true,
            isWide = true
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomButton(
                text = "Batal",
                onClick = { onDismiss() },
                isRounded = true,
                isOutline = true,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            CustomButton(
                text = "Simpan",
                onClick = { },
                isRounded = true,
                isWide = true
            )
        }
        Spacer(modifier = Modifier.height(Padding.medium))
    }
}

@Preview
@Composable
private fun test() {
    CekPanganAITheme {
//        SearchFoodDialog(
//            showDialog = true,
//            onDismiss = { /*TODO*/ },
//            onSave = { "yes" },
//            isEditFood = false,
//            onDelete = {},
//            suggestions = listOf(),
////            getFoodIdByName = {""},
//        )
    }
}
