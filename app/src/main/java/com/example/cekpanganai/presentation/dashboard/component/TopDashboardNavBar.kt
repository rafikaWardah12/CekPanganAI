package com.example.cekpanganai.presentation.dashboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.R
import com.example.cekpanganai.ui.utils.Spacing
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.GreenSecondary
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.TextDisable
import com.example.cekpanganai.ui.theme.TextPrimary
import com.example.cekpanganai.ui.utils.DateRange

@Composable
fun TopDashboardNavbar(
    modifier: Modifier = Modifier,
    onProfile: () -> Unit,
    selectedDateRange: DateRange,
    onSelectedDateRange: (DateRange) -> Unit,
    name: String,
) {
    var dropDown by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.large),
            modifier = modifier
                .clickable { onProfile() }
        ) {
            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .background(color = GreenPrimary)
                    .size(45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = stringResource(
                        id = R.string.profile
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.profile) + " " + name.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
        }
        Box {
            Text(
                text = when (selectedDateRange) {
                    DateRange.TODAY -> stringResource(id = R.string.current_day)
                    DateRange.LAST_3_DAYS -> stringResource(id = R.string.last_3_days)
                    DateRange.LAST_7_DAYS -> stringResource(id = R.string.last_week)
                    DateRange.LAST_30_DAYS -> stringResource(id = R.string.last_month)
                },
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { dropDown = true }
            )
            DropdownMenu(
                expanded = dropDown,
                onDismissRequest = { dropDown = false },
                containerColor = PrimaryBackground
            ) {
                DateRange.values().forEach {
                    DropdownMenuItem(text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                when (it) {
                                    DateRange.TODAY -> stringResource(id = R.string.current_day)
                                    DateRange.LAST_3_DAYS -> stringResource(id = R.string.last_3_days)
                                    DateRange.LAST_7_DAYS -> stringResource(id = R.string.last_week)
                                    DateRange.LAST_30_DAYS -> stringResource(id = R.string.last_month)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(modifier = modifier.height(Spacing.small))
                            Divider(thickness = 0.6.dp, color = Outline.copy(alpha = 0.5f))
                        }
                    }, onClick = {
                        dropDown = false
                        onSelectedDateRange(it)
                    })
                }

            }
        }
    }
}