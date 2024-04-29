package com.geanbrandao.br.billbuddy.presentation.navigation

import androidx.annotation.StringRes
import com.geanbrandao.br.billbuddy.R

private const val BILLS_ROUTE = "bills"
private const val CREATE_BILL_ROUTE = "createBill/{billId}"
private const val BILL_DETAILS_ROUTE = "billDetails"
private const val CREATE_BILL_ITEM = "createBillItem"
private const val CLOSE_BILL_ITEM = "closeBill"

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
) {
    data object Bills : Screen(BILLS_ROUTE, R.string.screen_bills_top_bar_title)
    data object Bill : Screen(CREATE_BILL_ROUTE, R.string.screen_create_bill_top_bar_title)
    data object BillDetails : Screen(BILL_DETAILS_ROUTE, R.string.screen_bill_details_top_bar_title)
    data object CreateItem : Screen(CREATE_BILL_ITEM, R.string.screen_create_item_top_bar_title)
    data object CloseBill : Screen(CLOSE_BILL_ITEM, R.string.screen_close_bill_top_bar_title)
}