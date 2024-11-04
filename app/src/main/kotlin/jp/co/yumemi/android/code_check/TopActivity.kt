/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.navigation.Screen
import jp.co.yumemi.android.code_check.navigation.navGraph.SetupNavGraph
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import java.util.*

class TopActivity : ComponentActivity() {

    companion object {
        lateinit var lastSearchDate: Date
    }

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                    startScreen = Screen.Home.route
                )
            }
        }
    }

}