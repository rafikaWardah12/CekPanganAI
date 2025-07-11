package com.example.cekpanganai.presentation.component

import android.app.Dialog
import android.graphics.drawable.Icon
import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cekpanganai.R
import com.example.cekpanganai.presentation.result.component.BodyEditName
import com.example.cekpanganai.ui.theme.BluePrimary
import com.example.cekpanganai.ui.theme.Shapes
import com.example.cekpanganai.ui.theme.TextPlaceHolder
import com.example.cekpanganai.ui.utils.Padding
import com.example.cekpanganai.ui.utils.Spacing

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    header: @Composable ColumnScope.() -> Unit,
    body: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = Shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = TextPlaceHolder, shape = Shapes.extraSmall),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                Modifier
                    .padding(Padding.medium)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { onDismiss() }, modifier.align(Alignment.End)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = stringResource(
                            id = R.string.close
                        ),
                    )
                }
                header()
                Spacer(modifier = Modifier.height(Spacing.large))
                body()
                CustomButton(
                    text = stringResource(id = R.string.save),
                    onClick = { onConfirm() },
                    isWide = true,
                    isRounded = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = stringResource(id = R.string.save)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(Spacing.large))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun test() {
    CustomDialog(onDismiss = { /*TODO*/ }, onConfirm = {}, header = {
        HeaderCard(title = stringResource(id = R.string.edit_name), icon = R.drawable.ic_edit)
    }, body = { BodyEditName() })
}