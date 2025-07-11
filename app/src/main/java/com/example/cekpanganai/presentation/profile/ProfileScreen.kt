package com.example.cekpanganai.presentation.profile

import android.app.Activity
import android.icu.text.DecimalFormat
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.profile.component.CardEditBMI
import com.example.cekpanganai.presentation.profile.component.CustomBoxField
import com.example.cekpanganai.presentation.profile.component.FieldCalculatedBox
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.GreenTertiary
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.TextTertiary
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by profileViewModel.userState.collectAsState()
    val bmiState by profileViewModel.bmiState.collectAsState()
    var editBmi by remember { mutableStateOf(false) }

    val name = profile.userById?.name
    val weight = profile.userById?.weight ?: 0.0
    val height = profile.userById?.height ?: 0.0
    val gender = profile.userById?.gender
    val age = profile.userById?.age
    val bmiScore = profile.userById?.bmi ?: 0.0
    val bmiCategory = bmiState.category?.labelResId

    var onHeight by remember { mutableDoubleStateOf(height) }
    var onWeight by remember { mutableDoubleStateOf(weight) }

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current as Activity

    LaunchedEffect(Unit) {
        profileViewModel.getUser()
        profileViewModel.getBmi(bmiScore)
    }

    if (editBmi) {
        CardEditBMI(
            onDismiss = { editBmi = false },
            weight = onHeight,
            height = onWeight,
            onSave = { h, w ->
                onHeight = h
                onWeight = w
                coroutine.launch {
                    val user = profileViewModel.userState.value.userById
                    if (user != null) {
                        val updateState = user.copy(height = h, weight = w)
                        profileViewModel.calculateBMI(updateState)
                        Log.e("bmi", "berhasil kalculasi")
                    } else {
                        Log.e("bmi", "User tidak ditemukan untuk update BMI")
                    }
                }
                Log.e("bmi", "height: $h and wight: $w")
            }
        )
    } else {
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(id = R.string.profile),
                isFilled = true,
                navigateBack = { profileViewModel.onNavigateBack() })
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Padding.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier
                    .height(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Image(
                    painterResource(id = R.drawable.profile),
                    contentDescription = stringResource(
                        id = R.string.profile
                    ),
                    modifier
                        .background(color = PrimaryBackground),
                    alignment = Alignment.TopEnd,
                    contentScale = ContentScale.FillHeight
                )
            }
            Text(text = "$name", style = MaterialTheme.typography.titleMedium)
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.medium)) {
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.gender),
                    value = "$gender"
                )
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.age),
                    value = "$age"
                )
                HorizontalDivider(color = TextTertiary)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Padding.medium))
                        .background(GreenPrimary)
                        .padding(Padding.large),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            Spacing.medium
                        )
                    ) {
                        if (bmiState == null) {
                            Text(
                                text = DecimalFormat("#.##").format(bmiScore ?: 0.0),
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$bmiCategory",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        bmiState.category?.let {
                            Text(
                                text = "Skor BMI",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = DecimalFormat("#.##").format(bmiScore ?: 0.0),
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(id = it.labelResId),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                    Text(
                        text = "Hitung Lagi",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable {
                                Log.e("profile-state", "$profile")
                                Log.e("profile-bmi", "$bmiScore")
                                editBmi = true

                            }
                            .padding(
                                start = Spacing.small,
                                top = Spacing.small,
                                bottom = Spacing.small
                            )
                    )
                }
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.height),
                    value = "$height cm",
                    isOutlined = true
                )
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.weight),
                    value = "$weight kg",
                    isOutlined = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
        ProfileScreen()
    }
}