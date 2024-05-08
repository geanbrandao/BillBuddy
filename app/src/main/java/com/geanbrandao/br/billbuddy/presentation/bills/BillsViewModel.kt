package com.geanbrandao.br.billbuddy.presentation.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BillsViewModel(
    private val appNavigator: AppNavigator,
    private val appDao: AppDao,
) : ViewModel() {

    init {
        getXPTO()
    }

    fun getXPTO() {
        viewModelScope.launch {
            val result = appDao.getUsersWithItems()
            println(result)
            // create one bill
//            val billSchemaVersion2Id = appDao.insertBill(
//                BillEntity(
//                    id = 0,
//                    name = "Schema Version 2",
//                    status = BillStatus.NEW,
//                )
//            )
//            // create three users
//            val willianId = appDao.insertUser(
//                UserEntity(
//                    userId = 0,
//                    name = "William",
//                    billId = billSchemaVersion2Id,
//                )
//            )
//            val robertaId = appDao.insertUser(
//                UserEntity(
//                    userId = 0,
//                    name = "Roberta",
//                    billId = billSchemaVersion2Id,
//                )
//            )
//            val pedroId = appDao.insertUser(
//                UserEntity(
//                    userId = 0,
//                    name = "Pedro",
//                    billId = billSchemaVersion2Id,
//                )
//            )
//            // create three items
//            val cocaId = appDao.insertItem(
//                ItemEntity(
//                    itemId = 0,
//                    name = "Coca-cola",
//                    value = 10f,
//                    billId = billSchemaVersion2Id,
//                )
//            )
//            val batataId = appDao.insertItem(
//                ItemEntity(
//                    itemId = 0,
//                    name = "Batata",
//                    value = 25f,
//                    billId = billSchemaVersion2Id,
//                )
//            )
//
//            val pizzaId = appDao.insertItem(
//                ItemEntity(
//                    itemId = 0,
//                    name = "Pizza",
//                    value = 49f,
//                    billId = billSchemaVersion2Id,
//                )
//            )
//            // create cross ref
//            // Willian consumiu a coca sozinho
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = willianId,
//                    itemId = cocaId,
//                    dividedValue = 10f,
//                )
//            )
//            // willian e roberta dividiram a batata
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = willianId,
//                    itemId = batataId,
//                    dividedValue = 12.5f,
//                )
//            )
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = robertaId,
//                    itemId = batataId,
//                    dividedValue = 12.5f,
//                )
//            )
//            // willian, roberta e pedro dividiram a pizza
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = willianId,
//                    itemId = pizzaId,
//                    dividedValue = 16.34f,
//                )
//            )
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = robertaId,
//                    itemId = pizzaId,
//                    dividedValue = 16.33f,
//                )
//            )
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = pedroId,
//                    itemId = pizzaId,
//                    dividedValue = 16.33f,
//                )
//            )
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