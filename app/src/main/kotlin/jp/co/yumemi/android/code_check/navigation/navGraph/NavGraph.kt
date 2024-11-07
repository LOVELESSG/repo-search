package jp.co.yumemi.android.code_check.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.OneViewModel
import jp.co.yumemi.android.code_check.navigation.Screen
import jp.co.yumemi.android.code_check.ui.homepage.HomepageScreen
import jp.co.yumemi.android.code_check.ui.repoDetails.RepoDetailsScreen
import jp.co.yumemi.android.code_check.ui.visitHistory.VisitHistoryScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startScreen: String
) {
    val oneViewModel: OneViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomepageScreen(
                navController = navController,
                searchError = oneViewModel.searchError,
                searchResults = oneViewModel.items,
                searchRepo = oneViewModel::searchResults,
                clearSearchResults = oneViewModel::clearSearchResults,
                clearSearchError = oneViewModel::clearErrorMessage,
                selectTargetItem = oneViewModel::selectTargetItem
            )
        }

        composable(
            route = Screen.RepoDetails.route,
        ) {
            RepoDetailsScreen(
                targetStateFlow = oneViewModel.targetItem,
                navController = navController
            )
        }

        composable(
            route = Screen.VisitHistory.route
        ) {
            VisitHistoryScreen(
                navController = navController
            )
        }
    }

}