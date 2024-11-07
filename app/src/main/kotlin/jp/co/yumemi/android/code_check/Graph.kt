package jp.co.yumemi.android.code_check

import android.content.Context
import jp.co.yumemi.android.code_check.data.room.AppDatabase
import jp.co.yumemi.android.code_check.data.room.repository.Repository

object Graph {
    lateinit var db: AppDatabase

    val repository by lazy {
        Repository(
            visitHistoryDao = db.visitHistoryDao()
        )
    }

    fun provide(context: Context) {
        db = AppDatabase.getDataBase(context)
    }
}