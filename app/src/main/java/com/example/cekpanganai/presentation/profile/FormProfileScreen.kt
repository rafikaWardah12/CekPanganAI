package com.example.cekpanganai.presentation.profile

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.data.database.entity.UserEntity
import com.example.cekpanganai.presentation.component.BoxItem
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.TextPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun FormProfileScreen(
    modifier: Modifier = Modifier,
    formProfileViewModel: FormProfileViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current as Activity

//    val formProfile by formProfileViewModel.profileState.collectAsState()
//    var name = formProfile.userById?.name
//    var age = formProfile.userById?.age
//    var gender = formProfile.userById?.gender ?: ""
//    var height = formProfile.userById?.height
//    var weight = formProfile.userById?.weight

    Surface() {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.headline_form_profile),
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(Spacing.large))
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.small)) {
                CustomInputText(
                    label = stringResource(id = R.string.name),
                    isRequired = true,
                    value = name,
                    onValue = { name = it })
                CustomInputText(
                    label = stringResource(id = R.string.age),
                    isRequired = true,
                    value = age,
                    onValue = { age = it },
                    isNumber = true
                )
                CustomInputText(
                    isRequired = true,
                    isBox = true,
                    boxItem = listOf(
                        BoxItem(stringResource(id = R.string.gender_boy), R.drawable.boy),
                        BoxItem(stringResource(id = R.string.gender_girl), R.drawable.girl)
                    ),
                    label = stringResource(id = R.string.gender),
                    onBoxSelected = { selectedGender ->
                        gender = selectedGender.lowercase()
                        Log.d("Profile", "Gender selected $gender")
                    }
                )
                CustomInputText(
                    label = stringResource(id = R.string.height),
                    isRequired = true,
                    isUnit = "Cm",
                    value = height,
                    onValue = { height = it },
                    isNumber = true
                )
                CustomInputText(
                    label = stringResource(id = R.string.weight),
                    isRequired = true,
                    isUnit = "Kg",
                    value = weight,
                    onValue = { weight = it },
                    isNumber = true
                )
                CustomButton(
                    text = stringResource(id = R.string.save),
                    onClick = {
                        coroutine.launch {
                            if (name.isNotBlank() && age.isNotBlank() && gender.isNotBlank() && weight.isNotBlank() && height.isNotBlank()) {
                                val user = UserEntity(
                                    id = 1,
                                    name = name,
                                    age = age.toInt(),
                                    gender = gender,
                                    weight = weight.toDouble(),
                                    height = height.toDouble(),
                                    bmi = null,
                                    imgProfile = null,
                                    createdAt = Date(),
                                    updatedAt = Date()
                                )
//                                val data = formProfileViewModel.addUser(
//                                    name = name,
//                                    age = age.toInt(),
//                                    gender = gender,
//                                    weight = weight.toDouble(),
//                                    height = height.toDouble()
//                                )
                                formProfileViewModel.addUser(user)
//                                profileViewModel.calculateBMI(
//                                    heightCm = height.toDouble(),
//                                    weightKg = weight.toDouble()
//                                )
                                delay(100)
                                profileViewModel.calculateBMI(user)
                                delay(400)
                                formProfileViewModel.onNavigateToBMIScore()
                                Log.d("profile", "Data Profile berhasil ditambahkan $user")
                            } else {
                                Toast.makeText(
                                    context,
                                    "Mohon lengkapi semua form terlebih dahulu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    isWide = true,
                    isRounded = true
                )
            }
        }
    }
}

@Preview
@Composable
private fun Test() {
    CekPanganAITheme {
        FormProfileScreen()
    }
}