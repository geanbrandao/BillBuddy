package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.domain.model.MoreMenuOption
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    name: String,
    groupName: String,
    initials: String,
    onRemovePerson: () -> Unit,
) {
    PersonItemView(
        modifier = modifier,
        name = name,
        groupName = groupName,
        initials = initials,
        onRemovePerson = onRemovePerson,
    )
}

@Composable
private fun PersonItemView(
    modifier: Modifier = Modifier,
    name: String = "",
    groupName: String = "",
    initials: String = "",
    onRemovePerson: () -> Unit = {},
) {
//    val initials = buildString {
//        val names = name.split(" ")
//        append(names.first().first())
//        if (names.size > 1) {
//            append(names.last().first())
//        }
//    }.uppercase()
    SwipeLeft(
        onDeleteClicked = onRemovePerson,
        shape = RectangleShape,
        content = {
            Column(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = PaddingTwo),
                ) {
                    ProfileAvatar(
                        initials = initials,
                        sizeDp = 48.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        strokeColor = MaterialTheme.colorScheme.primaryContainer,
                        letterColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(Modifier.size(PaddingOne))
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        if (groupName.isNotEmpty()) {
                            Text(
                                text = groupName,
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier
                            )
                        }
                    }
                    OptionMenuView(
                        options = listOf(MoreMenuOption.DELETE),
                        onOptionSelected = {
                            if (it == MoreMenuOption.DELETE) {
                                onRemovePerson()
                            }
                        }
                    )
                }
                HorizontalDivider()
            }
        }
    )
}

@Composable
private fun OptionMenuView(
    modifier: Modifier = Modifier,
    options: List<MoreMenuOption> = emptyList(),
    onOptionSelected: (MoreMenuOption) -> Unit = {},
) {
    val isExpanded = remember { mutableStateOf(false) }
    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(
            onClick = { isExpanded.value = true },
            modifier = Modifier
        ) {
            Icon(painter = painterResource(R.drawable.ic_more), contentDescription = "Mais opções")
        }
        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = {
                isExpanded.value = false
            }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(option.labelId)) },
                    onClick = {
                        onOptionSelected(option)
                        isExpanded.value = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Remover pessoa",
                        )
                    }
                )
            }
        }

    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = false)
@Composable
private fun PersonItemPreview() {
    BillBuddyTheme {
        Column {
            PersonItemView(name = "John Doe", groupName = "Friends", initials = "JD")
            PersonItemView(name = "Billy", initials = "B")
            PersonItemView(name = "Mat Murdock", groupName = "Friends", initials = "MM")
        }
    }
}