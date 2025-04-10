package com.example.cekpanganai.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cekpanganai.MainViewModel
import com.example.cekpanganai.R
import com.example.cekpanganai.common.navigation_utils.Destination
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.theme.GreenPrimary
import com.example.cekpanganai.ui.theme.Outline
import com.example.cekpanganai.ui.theme.PrimaryBackground
import com.example.cekpanganai.ui.theme.TextSecondary


@Composable
fun CustomBottomNavBar(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val currentDestination = navController.currentDestination?.route

    Box(modifier = modifier.background(Color.Transparent)) {
        BottomAppBar(
            modifier = modifier
                .height(62.dp)
                .border(
                    1.dp,
                    Outline,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .background(Color.Transparent),
            containerColor = PrimaryBackground,
            tonalElevation = 2.dp,
            contentColor = PrimaryBackground
        ) {
            Item(
                icon = R.drawable.ic_dashboard,
                label = R.string.dashboard,
                isSelected = currentDestination == Destination.DASHBOARD_ROUTE,
                onClick = { mainViewModel.onNavigateToDashboard() },
            )
            Spacer(Modifier.weight(1f)) // Memberi ruang untuk FAB
            Item(
                icon = R.drawable.ic_history,
                label = R.string.history,
                isSelected = currentDestination == Destination.HISTORY_ROUTE,
                onClick = { mainViewModel.onNavigateToHistory() }
            )
        }
        FloatingActionButton(
            onClick = { mainViewModel.onNavigateToDetect() },
            containerColor = GreenPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-28).dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_detect),
                    contentDescription = stringResource(id = R.string.detect),
                    tint = Color.White
                )
                Text(
                    text = stringResource(id = R.string.detect),
                    style = MaterialTheme.typography.labelMedium,
                    color = PrimaryBackground
                )
            }
        }
    }
}

@Composable
private fun Item(
    icon: Int,
    label: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            .padding(horizontal = Padding.extraLarge),
        verticalArrangement = Arrangement.Center, // Tengah vertikal
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(
                id = label
            ),
            tint = if (isSelected) GreenPrimary else TextSecondary,
            modifier = modifier.size(22.dp)
        )
        Text(
            text = stringResource(id = label),
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) GreenPrimary else TextSecondary
        )
    }
}

@Preview
@Composable
private fun test() {
//    CustomBottomNavBar(navController = NavController())
}