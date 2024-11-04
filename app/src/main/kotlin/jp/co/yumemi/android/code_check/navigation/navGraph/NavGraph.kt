package jp.co.yumemi.android.code_check.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jp.co.yumemi.android.code_check.OneViewModel
import jp.co.yumemi.android.code_check.navigation.Screen
import jp.co.yumemi.android.code_check.ui.homepage.HomepageScreen
import jp.co.yumemi.android.code_check.ui.repoDetails.RepoDetailsScreen


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

        composable(
            route = "${Screen.RepoDetails.route}?name={name}&ownerIconUrl={ownerIconUrl}" +
                    "&language={language}&stargazersCount={stargazersCount}" +
                    "&watchersCount={watchersCount}&forksCount={forksCount}" +
                    "&openIssuesCount={openIssuesCount}",
            arguments = listOf(
                navArgument("name"){type = NavType.StringType},
                navArgument("ownerIconUrl"){type = NavType.StringType},
                navArgument("language"){type = NavType.StringType},
                navArgument("stargazersCount"){type = NavType.LongType},
                navArgument("watchersCount"){type = NavType.LongType},
                navArgument("forksCount"){type = NavType.LongType},
                navArgument("openIssuesCount"){type = NavType.LongType},
                )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val ownerIconUrl = backStackEntry.arguments?.getString("ownerIconUrl") ?: ""
            val language = backStackEntry.arguments?.getString("language") ?: ""
            val stargazersCount = backStackEntry.arguments?.getLong("stargazersCount") ?: 0L
            val watchersCount = backStackEntry.arguments?.getLong("watchersCount")  ?: 0L
            val forksCount = backStackEntry.arguments?.getLong("forksCount") ?: 0L
            val openIssuesCount = backStackEntry.arguments?.getLong("openIssuesCount") ?: 0L

            RepoDetailsScreen(
                name = name,
                ownerIconUrl = ownerIconUrl,
                language = language,
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount,
                navController = navController
            )
        }
    }
}