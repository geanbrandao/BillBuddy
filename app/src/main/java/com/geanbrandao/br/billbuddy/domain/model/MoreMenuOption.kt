package com.geanbrandao.br.billbuddy.domain.model

import androidx.annotation.StringRes
import com.geanbrandao.br.billbuddy.R

enum class MoreMenuOption(@StringRes val labelId: Int) {
    DELETE(labelId = R.string.options_menu_label_remove),
}