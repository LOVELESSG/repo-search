package jp.co.yumemi.android.code_check.ui.homepage

import android.content.res.Configuration
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.Item
import jp.co.yumemi.android.code_check.components.ResultItem
import jp.co.yumemi.android.code_check.navigation.Screen
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

// This is the homepage of this app.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageScreen(
    navController: NavController,
    searchResults: StateFlow<MutableList<Item>>,
    searchRepo: (String) -> Unit,
    clearSearchResults: () -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    val resultsList by searchResults.collectAsState()

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
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
                        placeholder = { Text(stringResource(R.string.searchInputText_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        trailingIcon = {
                            if (expanded) {
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            if (text.isEmpty()){
                                                expanded = false
                                            } else {
                                                text = ""
                                            }
                                        },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Icon"
                                )
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
                // Show the search result
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    resultsList.forEach {
                        item {
                            ResultItem(item = it, navController = navController)
                        }
                    }
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
    AppTheme {
        HomepageScreen(
            navController = rememberNavController(),
            searchResults = fakeResults,
            searchRepo = { },
            clearSearchResults = {}
        )
    }
}