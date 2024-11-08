package jp.co.yumemi.android.code_check.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "visit_histories")
data class VisitHistory (
    @ColumnInfo(name = "history_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
    val addTime: LocalDateTime
)


@Entity(tableName = "search_histories")
data class SearchHistory(
    @PrimaryKey
    val searchText: String
)