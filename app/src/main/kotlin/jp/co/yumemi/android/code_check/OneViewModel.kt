/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Parcelable
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
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

    private val client = HttpClient(Android)

    override fun onCleared() {
        super.onCleared()
        client.close()
    }

    // Delete all search results when the search bar is not expanded
    fun clearSearchResults() {
        _items.value = _items.value.toMutableList().apply {
            clear()
        }
    }

    // Clear the search error message
    fun clearErrorMessage() {
        _searchError.value = null
    }

    // 検索結果
    fun searchResults(inputText: String){

        viewModelScope.launch {

            try {
                val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

                val jsonBody = JSONObject(response.bodyAsText())
                val jsonItems = jsonBody.optJSONArray("items")!!

                // アイテムの個数分ループする
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)!!
                    val name = jsonItem.optString("full_name")
                    val ownerIconUrl = jsonItem.optJSONObject("owner")!!.optString("avatar_url")
                    val language = jsonItem.optString("language")
                    val stargazersCount = jsonItem.optLong("stargazers_count")
                    val watchersCount = jsonItem.optLong("watchers_count")
                    val forksCount = jsonItem.optLong("forks_count")
                    val openIssuesCount = jsonItem.optLong("open_issues_count")

                    _items.value = _items.value.toMutableList().apply {
                        add(
                            Item(
                                name = name,
                                ownerIconUrl = ownerIconUrl,
                                language = language,
                                stargazersCount = stargazersCount,
                                watchersCount = watchersCount,
                                forksCount = forksCount,
                                openIssuesCount = openIssuesCount
                            )
                        )
                    }
                }

                lastSearchDate = Date()
            } catch (e: Exception) {
                _searchError.value = "Search failed"
            }

        }
    }
}

@Parcelize
data class Item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable