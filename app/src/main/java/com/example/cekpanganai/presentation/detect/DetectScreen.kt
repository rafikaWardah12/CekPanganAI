package com.example.cekpanganai.presentation.detect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.CustomButton
import com.example.cekpanganai.presentation.component.CustomTopBar

@Composable
fun DetectScreen(
    modifier: Modifier = Modifier,
    detectViewModel: DetectViewModel = hiltViewModel()
) {
    Surface {
        Column(modifier.verticalScroll(rememberScrollState())) {
            CustomTopBar(
                title = stringResource(id = R.string.detect),
                navigateBack = { detectViewModel.onNavigateBack() })
            Text(text = "Detect Screen")
            CustomButton(
                text = stringResource(id = R.string.save),
                onClick = { detectViewModel.onNavigateToProcess() })
        }
    }
}