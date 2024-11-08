package jp.co.yumemi.android.code_check.ui.visitHistory

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.components.AppSuiteScaffold
import jp.co.yumemi.android.code_check.components.BriefRepoCard
import jp.co.yumemi.android.code_check.data.room.models.VisitHistory
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitHistoryScreen(
    navController: NavController,
    visitHistories: StateFlow<MutableList<VisitHistory>>,
    deleteVisitHistory: (VisitHistory) -> Unit
) {
    val visitHistoryList by visitHistories.collectAsState()

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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                visitHistoryList.reversed().forEachIndexed {index, visitHistory ->
                    item {
                        if (index == 0 ||
                            visitHistory.addTime.hour !=
                            visitHistoryList.reversed()[index - 1].addTime.hour) {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier
                                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                            ) {
                                Text(text = "${visitHistory.addTime.year}." +
                                        "${visitHistory.addTime.monthValue}." +
                                        "${visitHistory.addTime.dayOfMonth} ",
                                    style = MaterialTheme.typography.headlineLarge
                                )
                                Text(text = "${visitHistory.addTime.hour}:00 ~ " +
                                        "${visitHistory.addTime.hour + 1}:00",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            BriefRepoCard(
                                visitHistoryItem = visitHistory,
                                icon = Icons.Default.Delete,
                                iconButtonOperation = deleteVisitHistory
                            )
                        } else {
                            BriefRepoCard(
                                visitHistoryItem = visitHistory,
                                icon = Icons.Default.Delete,
                                iconButtonOperation = deleteVisitHistory
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun VisitHistoryPreview() {
    AppTheme {
        val searchResults = remember { mutableListOf<VisitHistory>() }
        val fakeResults = MutableStateFlow(searchResults).asStateFlow()
        VisitHistoryScreen(
            navController = rememberNavController(),
            visitHistories = fakeResults,
            deleteVisitHistory = {}
        )
    }
}
