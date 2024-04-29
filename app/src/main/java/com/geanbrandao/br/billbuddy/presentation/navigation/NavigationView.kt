package com.geanbrandao.br.billbuddy.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationView(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            Text(text = "Bills")
        }
        composable("bill/{billId}") { // ou nada caso seja novo
            Text(text = "Bill")
        }
        composable("billBuddy") {
            Text(text = "Racha Conta")
        }
        composable("createItem") {
            Text(text = "Criar Item")
        }
        composable("closeBill") {
            Text(text = "Fechar Conta")
        }
    }
}