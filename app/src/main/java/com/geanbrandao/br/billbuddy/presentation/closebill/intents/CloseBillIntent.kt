package com.geanbrandao.br.billbuddy.presentation.closebill.intents

import android.graphics.Bitmap

sealed class CloseBillIntent {
    data class OnServiceTaxPercentageChange(val value: String) : CloseBillIntent()
    data class OnShareBillResume(val bitmap: Bitmap) : CloseBillIntent()
}