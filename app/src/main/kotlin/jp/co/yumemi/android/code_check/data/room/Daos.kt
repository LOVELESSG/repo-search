package jp.co.yumemi.android.code_check.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.co.yumemi.android.code_check.data.room.models.VisitHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: VisitHistory)

    @Delete
    suspend fun delete(history: VisitHistory)

    @Query("SELECT * FROM visit_histories")
    fun getAllHistories(): Flow<List<VisitHistory>>
}