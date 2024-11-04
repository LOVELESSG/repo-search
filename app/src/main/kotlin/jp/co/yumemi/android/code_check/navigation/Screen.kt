package jp.co.yumemi.android.code_check.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
}