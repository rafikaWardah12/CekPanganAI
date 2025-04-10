package com.example.cekpanganai.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.utils.Spacing

@Composable
fun HeaderCard(modifier: Modifier = Modifier, title: String, icon: Int) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center ) {
        Icon(painter = painterResource(id = icon), contentDescription = title, tint = BluePrimary )
        Spacer(modifier = Modifier.height(Spacing.large))
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}
