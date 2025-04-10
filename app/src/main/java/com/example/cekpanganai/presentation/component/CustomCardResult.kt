package com.example.cekpanganai.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.data.network.fakeData.FakeDataNutrition
import com.example.cekpanganai.domain.model.DataItemNutrition
import com.example.cekpanganai.presentation.result.component.HeaderResult
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.CaliumPrimary
import com.example.cekpanganai.ui.theme.CekPanganAITheme
import com.example.cekpanganai.ui.theme.ColestrolPrimary
import com.example.cekpanganai.ui.theme.GlukosaPrimary
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.SodiumPrimary
import com.example.cekpanganai.ui.theme.TextSecondary
import com.example.cekpanganai.ui.utils.LocalElevations

@Composable
fun CustomCardResult(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {}
) {
    var showFullInfo by remember { mutableStateOf(false) }
    val itemsShow = if (showFullInfo) FakeDataNutrition.result else FakeDataNutrition.result.take(3)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showFullInfo = !showFullInfo },
        colors = CardDefaults.cardColors(PrimaryBackground),
        elevation = CardDefaults.cardElevation(0.3.dp)
//        elevation = CardDefaults.cardElevation(LocalElevations.current.card)
    ) {
        Column(modifier.padding(Padding.small)) {
            header()
            Spacer(modifier = Modifier.height(5.dp))
            itemsShow.forEach { item ->
                ItemNutrition(
                    color = item.color,
                    label = item.label,
                    score = item.score,
                    unit = "KG"
                )
            }
            Icon(
                painter = if (showFullInfo) painterResource(id = R.drawable.ic_arrow_up) else painterResource(
                    id = R.drawable.ic_arrow_down
                ),
                contentDescription = if (showFullInfo) stringResource(id = R.string.hide) else stringResource(
                    id = R.string.show,
                ),
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CekPanganAITheme {
        CustomCardResult(header = { HeaderResult(label = "Fika", serving = "19", onClick = {}) })
    }
}