package jp.co.yumemi.android.code_check.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.OneViewModel
import jp.co.yumemi.android.code_check.navigation.Screen
import jp.co.yumemi.android.code_check.ui.homepage.HomepageScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startScreen: String
) {
    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable(
            route = Screen.Home.route
        ) {
            val oneViewModel: OneViewModel = viewModel()

            HomepageScreen(
                navController = navController,
                searchResults = oneViewModel.items,
                searchRepo = oneViewModel::searchResults,
                clearSearchResults = oneViewModel::clearSearchResults
            )
        }
    }
}