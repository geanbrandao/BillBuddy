package com.geanbrandao.br.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geanbrandao.br.billbuddy.presentation.common.CustomTopAppBar
import com.geanbrandao.br.billbuddy.presentation.navigation.NavigationView
import com.geanbrandao.br.billbuddy.presentation.navigation.Screen
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val canNavigateBack = remember { mutableStateOf(false) }
            val screenTitle = remember { mutableIntStateOf(Screen.Bills.title) }
            val isTopAppBarVisible = remember { mutableStateOf(true) }
            onDestinationHandle(
                navController = navController,
                updateNavigateBack = { canNavigateBack.value = it },
                updateScreenTitle = { screenTitle.intValue = it },
                updateTopAppBarVisible = { isTopAppBarVisible.value = it }
            )
            // TODO temporário
            BillBuddyTheme(darkTheme = false) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = if (isTopAppBarVisible.value) {
                        {
                            CustomTopAppBar(
                                title = stringResource(id = screenTitle.intValue),
                                canNavigateBack = canNavigateBack.value,
                                onArrowBackClicked = { navController.popBackStack() }
                            )
                        }
                    } else {
                        { }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Row {
                            Button(onClick = { navController.navigate(Screen.Bills.route) }) {
                                Text(text = "1")
                            }
                            Button(onClick = { navController.navigate(Screen.Bill.route) }) {
                                Text(text = "2")
                            }
                            Button(onClick = { navController.navigate(Screen.BillDetails.route) }) {
                                Text(text = "3")
                            }
                            Button(onClick = { navController.navigate(Screen.CreateItem.route) }) {
                                Text(text = "4")
                            }
                            Button(onClick = { navController.navigate(Screen.CloseBill.route) }) {
                                Text(text = "5")
                            }
                        }
                        NavigationView(
//                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

private fun onDestinationHandle(
    navController: NavHostController,
    updateNavigateBack: (Boolean) -> Unit,
    updateScreenTitle: (Int) -> Unit,
    updateTopAppBarVisible: (Boolean) -> Unit,
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            Screen.Bills.route -> {
                updateNavigateBack(false)
                updateTopAppBarVisible(true)
                updateScreenTitle(Screen.Bills.title)
            }

            Screen.Bill.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(true)
                updateScreenTitle(Screen.Bill.title)
            }

            Screen.BillDetails.route -> {
                updateNavigateBack(false)
                updateTopAppBarVisible(false)
                updateScreenTitle(Screen.BillDetails.title)
            }

            Screen.CreateItem.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(true)
                updateScreenTitle(Screen.CreateItem.title)
            }

            Screen.CloseBill.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(true)
                updateScreenTitle(Screen.CloseBill.title)
            }

            else -> throw Exception("Something went wrong")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BillBuddyTheme {

    }
}