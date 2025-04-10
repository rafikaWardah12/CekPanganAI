package com.example.cekpanganai.presentation.component

import android.media.tv.TvContract.Channels.Logo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.cekpanganai.R
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.TextPrimary
import org.junit.Test

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//    Row {
    Text(text = stringResource(id = R.string.logo_firstname), style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Normal), color = TextPrimary)
    Text(text = stringResource(id = R.string.logo_lastname), style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Medium), color = GreenPrimary)
    }
}

@Preview (showBackground = true)
@Composable
private fun Test() {
    CekPanganAITheme {
        Logo()
    }
}