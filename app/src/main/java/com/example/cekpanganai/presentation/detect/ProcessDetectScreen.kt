package com.example.cekpanganai.presentation.detect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.component.Logo
import com.example.cekpanganai.ui.theme.CekPanganAITheme

@Composable
fun ProcessDetectScreen(modifier: Modifier = Modifier, detectViewModel: DetectViewModel = hiltViewModel()) {
    Scaffold {
        Surface(modifier.padding(it), color = MaterialTheme.colorScheme.surface)
        {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.scanner),
                        contentDescription = stringResource(
                            id = R.string.detect
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.contoh),
                        contentDescription = stringResource(
                            id = R.string.detect
                        )
                    )
                }
                Column {
                    Logo()
                    Text(
                        text = stringResource(id = R.string.description_process_detect),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )
                }
                Button(onClick = { detectViewModel.onNavigateToResult() }) {
                    Text(text = "Selanjutnya")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
        ProcessDetectScreen()
    }
}