package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne

@Composable
fun WarningSnackbar(
    message: String
) {
    WarningSnackbarView(message)
}

@Composable
private fun WarningSnackbarView(
    message: String = "",
) {
    Snackbar(
        containerColor = Color(0xFFFFA100),
        contentColor = Color.Black,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_warning), contentDescription = null)
            Text(text = message, modifier = Modifier.weight(1f).padding(start = PaddingOne))
        }
    }
}

@Preview
@Composable
private fun WarningSnackbarPreview() {
    BillBuddyTheme {
        WarningSnackbarView("Algo aconteceu")
    }
}