package com.example.cekpanganai.presentation.profile

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.BoxItem
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomInputText
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.TextPrimary

@Composable
fun FormProfileScreen(
    modifier: Modifier = Modifier,
    formProfileViewModel: FormProfileViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

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
                CustomInputText(label = stringResource(id = R.string.name), isRequired = true, value = name, onValue = { name = it})
                CustomInputText(label = stringResource(id = R.string.age), isRequired = true, value = age, onValue = { age = it })
                CustomInputText(
                    isRequired = true,
                    isBox = true,
                    boxItem = listOf(
                        BoxItem(stringResource(id = R.string.gender_boy), R.drawable.boy),
                        BoxItem(stringResource(id = R.string.gender_girl), R.drawable.girl)
                    ),
                    label = stringResource(id = R.string.gender),
                )
                CustomInputText(
                    label = stringResource(id = R.string.height),
                    isRequired = true,
                    isUnit = "Cm",
                    value = height,
                    onValue = { height = it}
                )
                CustomInputText(
                    label = stringResource(id = R.string.weight),
                    isRequired = true,
                    isUnit = "Kg",
                    value = weight,
                    onValue = { weight = it }
                )
                CustomButton(
                    text = stringResource(id = R.string.save),
                    onClick = { formProfileViewModel.onNavigateToBMIScore() },
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