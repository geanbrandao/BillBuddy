package com.geanbrandao.br.billbuddy.presentation.bills

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.domain.usecase.UseCases
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyBillsUiState"

@KoinViewModel
class BillsViewModel(
    private val appNavigator: AppNavigator,
    private val state: SavedStateHandle,
    private val useCases: UseCases,
    private val appDao: AppDao,
) : ViewModel() {

    val uiState = state.getStateFlow(key = KEY_UI_STATE, BillsUiState())

    init {
        getXPTO()
    }

    fun getXPTO() {
        viewModelScope.launch {
            val result = appDao.getUsersWithItemsAndDividedValues(2)
            println(result)
//            result.sortedBy { it.userId }.forEach { userWithItems ->
//                val userName = userWithItems.userName
//                val value = userWithItems.value
//                val item =  userWithItems.itemName
//                println("User: $userName, $item custou: $value")
//            }
//            val listByUser = result.groupBy { it.userId }
//            listByUser.forEach { userId: Long, u: List<UserWithItemDividedValue> ->
//                val sum = u.sumOf { it.value.toDouble() }
//                val value = String.format("%.2f", sum)
//                println("User: ${u[0].userName} gastou $value nos seguintes items: ")
//                println(u.joinToString { it.itemName })
//            }

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

//            val costelaId = appDao.insertItem(
//                ItemEntity(
//                    itemId = 0,
//                    name = "Costelinha",
//                    value = 98f,
//                    billId = 2,
//                )
//            )
//
//            val caipirinhaId = appDao.insertItem(
//                ItemEntity(
//                    itemId = 0,
//                    name = "Caipirinha",
//                    value = 18f,
//                    billId = 2,
//                )
//            )

//            // create cross ref
            // willian e pedro dividiram a costelinha
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = 2,
//                    itemId = costelaId,
//                    dividedValue = 49f,
//                )
//            )
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = 3,
//                    itemId = costelaId,
//                    dividedValue = 49f,
//                )
//            )
//            // roberta consumiu a caipirinha sozinha
//            appDao.insertUserItemCrossRef(
//                UserItemCrossRef(
//                    userId = 1,
//                    itemId = caipirinhaId,
//                    dividedValue = 18f,
//                )
//            )
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

    fun getBills() {
        viewModelScope.launch {
            useCases.getBillsUseCase().collect {
                state[KEY_UI_STATE] = uiState.value.copy(bills = it)
            }
        }
    }
    fun handleNavigation(intent: BillsNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillsNavigationIntent.NavigateToBill -> {
                    appNavigator.navigateTo(route = intent.route)
                }

                is BillsNavigationIntent.NavigateToBillDetails -> {
                    appNavigator.navigateTo(route = intent.route)
                }
            }
        }
    }
}