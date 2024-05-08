package com.geanbrandao.br.billbuddy.presentation.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BillsViewModel(
    private val appNavigator: AppNavigator,
    private val appDao: AppDao,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val result = appDao.getBills()
            if (result.isEmpty()) {
                val id = appDao.insertBill(BillEntity(id = 0, name = "Schema Version 1"))
                appDao.insertUser(UserEntity(id = 0, name = "User 1", billId = id.toInt()))
            }
        }
    }

    fun handleNavigation(intent: BillsNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillsNavigationIntent.NavigateToBill -> {
                    appNavigator.navigateTo(route = intent.route)
                }
            }
        }
    }
}