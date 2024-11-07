package jp.co.yumemi.android.code_check.data.room.repository

import jp.co.yumemi.android.code_check.data.room.VisitHistoryDao
import jp.co.yumemi.android.code_check.data.room.models.VisitHistory

class Repository(
    private val visitHistoryDao: VisitHistoryDao
) {
    val visitHistory = visitHistoryDao.getAllHistories()

    suspend fun insertVisitHistory(historyItem: VisitHistory) {
        visitHistoryDao.insert(historyItem)
    }

    suspend fun deleteVisitHistory(historyItem: VisitHistory) {
        visitHistoryDao.delete(historyItem)
    }
}