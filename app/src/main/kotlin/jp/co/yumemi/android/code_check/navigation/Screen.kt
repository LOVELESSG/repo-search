package jp.co.yumemi.android.code_check.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object RepoDetails: Screen(route = "repo_details")
    data object VisitHistory: Screen(route = "visit_history")
}