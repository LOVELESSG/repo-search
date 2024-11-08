package jp.co.yumemi.android.code_check.ui.homepage

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.components.AppSuiteScaffold
import jp.co.yumemi.android.code_check.components.ResultItem
import jp.co.yumemi.android.code_check.data.Item
import jp.co.yumemi.android.code_check.data.room.models.SearchHistory
import jp.co.yumemi.android.code_check.data.room.models.VisitHistory
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// This is the homepage of this app.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageScreen(
    navController: NavController,
    searchError: StateFlow<String?>,
    searchResults: StateFlow<MutableList<Item>>,
    searchHistories: StateFlow<MutableList<SearchHistory>>,
    searchRepo: (String) -> Unit,
    clearSearchResults: () -> Unit,
    clearSearchError: () -> Unit,
    selectTargetItem: (Item) -> Unit,
    addVisitHistory: (VisitHistory) -> Unit,
    addSearchHistory: (SearchHistory) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    val resultsList by searchResults.collectAsState()
    val searchErrorMessage by searchError.collectAsState()
    val searchHistoryList by searchHistories.collectAsState()

    /*val searchHistory = remember { mutableStateListOf(
        "First history",
        "Second History"
    ) }*/

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                }
            )
        }
    ) { innerPadding ->
        AppSuiteScaffold(navController, innerPadding) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Repo Search",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                SearchBar(
                    modifier = Modifier
                        .semantics { traversalIndex = 0f }
                        .padding(
                            when (expanded) {
                                false -> 16
                                true -> 0
                            }.dp,
                            8.dp
                        ),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = text,
                            onQueryChange = {
                                text = it
                            },
                            onSearch = {
                                if (text.isNotBlank()){
                                    val searchHistory = SearchHistory(text)
                                    addSearchHistory(searchHistory)
                                    searchRepo(text)
                                    focusManager.clearFocus()
                                } else {
                                    Toast.makeText(context, "Please input keyword", Toast.LENGTH_SHORT).show()
                                }
                            },
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = it
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.searchInputText_hint),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    )
                            },
                            trailingIcon = {
                                if (expanded) {
                                    IconButton(
                                        onClick = {
                                            clearSearchResults()
                                            if (text.isEmpty()){
                                                expanded = false
                                            } else {
                                                text = ""
                                            }
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close Icon"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = it
                        if (!expanded) {
                            clearSearchResults()
                            text = ""
                        }
                    }
                ) {
                    if (text.isEmpty()) { // if search bar is empty and has been expanded -> show search history
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            searchHistoryList.reversed().forEach {
                                item {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clickable { text = it.searchText }
                                            .padding(16.dp)
                                            .fillMaxWidth()

                                    ) {
                                        Icon(
                                            modifier = Modifier.padding(end = 8.dp),
                                            imageVector = Icons.Default.History,
                                            contentDescription = "Search History Icon"
                                        )
                                        Text(text = it.searchText)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                    } else { // Show the search results
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            resultsList.forEach {
                                item {
                                    ResultItem(
                                        item = it,
                                        navController = navController,
                                        selectTargetItem = selectTargetItem,
                                        addVisitHistory = addVisitHistory
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1.6f))
            }

            LaunchedEffect(searchErrorMessage) {
                searchErrorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    clearSearchError()
                }
            }
        }
    }
}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomepageScreenPreview() {
    val searchResults = remember { mutableListOf<Item>() }
    val fakeResults = MutableStateFlow(searchResults).asStateFlow()
    val searchHistory = remember { mutableListOf<SearchHistory>() }
    val fakeSearchHistory = MutableStateFlow(searchHistory).asStateFlow()
    val searchError: String? = null
    val fakeError = MutableStateFlow(searchError).asStateFlow()
    AppTheme {
        HomepageScreen(
            navController = rememberNavController(),
            searchResults = fakeResults,
            searchError = fakeError,
            searchHistories = fakeSearchHistory,
            searchRepo = { },
            clearSearchResults = {},
            clearSearchError = {},
            selectTargetItem = {},
            addVisitHistory = {},
            addSearchHistory = {}
        )
    }
}