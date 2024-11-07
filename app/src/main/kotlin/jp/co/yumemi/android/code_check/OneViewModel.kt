/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.data.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date

/**
 * Homepage で使う
 */
class OneViewModel: ViewModel() {

    private val _items = MutableStateFlow(mutableListOf<Item>())
    val items: StateFlow<MutableList<Item>> = _items.asStateFlow()

    private val _searchError = MutableStateFlow<String?>(null)
    val searchError: StateFlow<String?> = _searchError.asStateFlow()

    // The target item which will be shown in the detail screen
    private val _targetItem = MutableStateFlow<Item?>(null)
    val targetItem: StateFlow<Item?> = _targetItem.asStateFlow()

    private val client = HttpClient(Android)

    override fun onCleared() {
        super.onCleared()
        client.close()
    }

    // Delete all search results when the search bar is not expanded
    fun clearSearchResults() {
        _items.update { currentItems ->
            currentItems.toMutableList().apply {
                clear()
            }
        }
    }

    // Clear the search error message
    fun clearErrorMessage() {
        _searchError.value = null
    }

    fun selectTargetItem(item: Item) {
        _targetItem.value = item
    }

    // 検索結果
    fun searchResults(inputText: String){
        viewModelScope.launch {
            try {
                clearSearchResults()

                val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

                val jsonBody = JSONObject(response.bodyAsText())
                val jsonItems = jsonBody.optJSONArray("items") ?: return@launch
                val newItems = mutableListOf<Item>()

                // アイテムの個数分ループする
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    newItems.add(
                        Item(
                            name = jsonItem.optString("full_name"),
                            ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: "",
                            language = jsonItem.optString("language"),
                            stargazersCount = jsonItem.optLong("stargazers_count"),
                            watchersCount = jsonItem.optLong("watchers_count"),
                            forksCount = jsonItem.optLong("forks_count"),
                            openIssuesCount = jsonItem.optLong("open_issues_count")
                        )
                    )
                }

                _items.update { newItems }

                lastSearchDate = Date()
            } catch (e: Exception) {
                _searchError.value = "Search failed"
            }

        }
    }
}