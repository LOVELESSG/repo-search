package jp.co.yumemi.android.code_check.ui.repoDetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Polyline
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import jp.co.yumemi.android.code_check.Item
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.components.InformationCard
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(
    targetStateFlow: StateFlow<Item?>,
    navController: NavController
) {
    val targetState = targetStateFlow.collectAsState()
    val target = targetState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "go back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = target?.ownerIconUrl,
                        contentDescription = "Owner Icon",
                        alignment = Alignment.Center
                    )
                    Surface(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        if (target != null) {
                            Text(
                                text = target.name,
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        InformationCard(
                            icon = Icons.Default.Star,
                            description = "Stars",
                            cardName = "Stars",
                            cardData = target?.stargazersCount,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                        )
                        InformationCard(
                            icon = Icons.Default.RemoveRedEye,
                            description = "Watchers",
                            cardName = "Watchers",
                            cardData = target?.watchersCount,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                        )
                        InformationCard(
                            icon = Icons.Default.Polyline,
                            description = "Forks",
                            cardName = "Forks",
                            cardData = target?.forksCount,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (target != null) {
                            InformationCard(
                                icon = Icons.Default.Code,
                                description = "Coding Languages",
                                cardName = stringResource(R.string.written_language, target.language),
                                cardData = null,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(1f)
                            )
                        }
                        InformationCard(
                            icon = Icons.Default.Adjust,
                            description = "Open Issues",
                            cardName = "Open Issues",
                            cardData = target?.openIssuesCount,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RepoDetailsScreenPreview() {
    AppTheme {
        val target: Item? = null
        val fakeTarget = MutableStateFlow(target).asStateFlow()
        RepoDetailsScreen(
            targetStateFlow = fakeTarget,
            navController = rememberNavController()
        )
    }
}