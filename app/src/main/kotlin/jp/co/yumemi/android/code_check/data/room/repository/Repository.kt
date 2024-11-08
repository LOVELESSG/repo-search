package jp.co.yumemi.android.code_check.data.room.repository

import jp.co.yumemi.android.code_check.data.room.SearchHistoryDao
import jp.co.yumemi.android.code_check.data.room.VisitHistoryDao
import jp.co.yumemi.android.code_check.data.room.models.SearchHistory
import jp.co.yumemi.android.code_check.data.room.models.VisitHistory

class Repository(
    private val visitHistoryDao: VisitHistoryDao,
    private val searchHistoryDao: SearchHistoryDao
) {
    // Visit History Part
    val visitHistory = visitHistoryDao.getAllHistories()

    suspend fun insertVisitHistory(historyItem: VisitHistory) {
        visitHistoryDao.insert(historyItem)
    }

    suspend fun deleteVisitHistory(historyItem: VisitHistory) {
        visitHistoryDao.delete(historyItem)
    }

    // Search History Part
    val searchHistory = searchHistoryDao.getAllHistories()

    suspend fun insertSearchHistory(historyItem: SearchHistory){
        searchHistoryDao.insert(historyItem)
    }

    suspend fun deleteSearchHistory(historyItem: SearchHistory) {
        searchHistoryDao.delete(historyItem)
    }
}