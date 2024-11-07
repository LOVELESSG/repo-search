package jp.co.yumemi.android.code_check.ui.visitHistory

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.components.AppSuiteScaffold
import jp.co.yumemi.android.code_check.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitHistoryScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Visit History"
                    )
                }
            )
        }
    ) { innerPadding ->
        AppSuiteScaffold(navController = navController, innerPaddingValues = innerPadding) {
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun VisitHistoryPreview() {
    AppTheme {
        VisitHistoryScreen(navController = rememberNavController())
    }
}