package jp.co.yumemi.android.code_check.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowWidthSizeClass
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.navigation.Screen

@Composable
fun AppSuiteScaffold(navController: NavController, innerPaddingValues: PaddingValues, content: @Composable () -> Unit) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val windowWidthClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    NavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize().padding(innerPaddingValues),
        navigationSuiteItems = navigationSuiteItems(currentDestination, navController),
        layoutType = if(windowWidthClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                currentWindowAdaptiveInfo()
            )
        },
        content = content
    )
}

@Composable
private fun navigationSuiteItems(
    currentDestination: NavDestination?,
    navController: NavController
): NavigationSuiteScope.() -> Unit = {
    item(
        selected = currentDestination?.hierarchy?.any {it.route == Screen.Home.route} == true,
        onClick = {
            navigateWithBackStackHandling(Screen.Home.route, navController)
        },
        label = { Text("Search") },
        icon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Page")
        }
    )
    item(
        selected = currentDestination?.hierarchy?.any {it.route == Screen.VisitHistory.route} == true,
        onClick = {
            navigateWithBackStackHandling(Screen.VisitHistory.route, navController)
        },
        label = { Text("History") },
        icon = {
            Icon(
                painter = painterResource(R.drawable.outline_footprint_24),
                contentDescription = "Visit History Page"
            )
        }
    )
}


fun navigateWithBackStackHandling(route: String, navController: NavController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}