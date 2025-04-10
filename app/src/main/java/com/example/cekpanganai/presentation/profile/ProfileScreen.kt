package com.example.cekpanganai.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomTopBar
import com.example.cekpanganai.presentation.profile.component.CustomBoxField
import com.example.cekpanganai.presentation.profile.component.FieldCalculatedBox
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenTertiary
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.TextTertiary
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
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
                    painterResource(id = R.drawable.contoh),
                    contentDescription = "Gambar",
                    modifier
                        .background(color = PrimaryBackground),
                    alignment = Alignment.TopEnd,
                    contentScale = ContentScale.FillHeight
                )
            }
            Text(text = "Fika", style = MaterialTheme.typography.titleMedium)
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.medium)) {
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.gender),
                    value = "Perempuan"
                )
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.age),
                    value = "23"
                )
                HorizontalDivider(color = TextTertiary)
                FieldCalculatedBox()
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.height),
                    value = "151 cm",
                    isOutlined = true
                )
                CustomBoxField(
                    color = GreenTertiary,
                    title = stringResource(id = R.string.weight),
                    value = "45 kg",
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