package com.example.cekpanganai.presentation.onBoarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R

@Composable
fun ImageItem(
    modifier: Modifier = Modifier, resource: Int,
) {
    Image(
        painterResource(id = resource),
        contentDescription = stringResource(id = R.string.onboarding),
        modifier = Modifier
            .fillMaxWidth()
            .size(320.dp),
        alignment = Alignment.Center
    )
    Spacer(modifier = Modifier.height(4.dp))
}