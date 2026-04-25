package com.secretaria.eletronica.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.secretaria.eletronica.data.entity.CallLogEntity

@Dao
interface CallLogDao {
    @Query("SELECT * FROM call_logs ORDER BY callDate DESC")
    fun getAllCallLogs(): LiveData<List<CallLogEntity>>

    @Query("SELECT * FROM call_logs WHERE id = :id")
    suspend fun getCallLogById(id: Int): CallLogEntity?

    @Query("SELECT * FROM call_logs ORDER BY callDate DESC LIMIT :limit")
    suspend fun getRecentCallLogs(limit: Int = 50): List<CallLogEntity>

    @Query("SELECT * FROM call_logs WHERE phoneNumber = :phoneNumber ORDER BY callDate DESC")
    suspend fun getCallLogsByNumber(phoneNumber: String): List<CallLogEntity>

    @Query("SELECT * FROM call_logs WHERE simSlot = :simSlot ORDER BY callDate DESC")
    fun getCallLogsBySimSlot(simSlot: Int): LiveData<List<CallLogEntity>>

    @Insert
    suspend fun insertCallLog(callLog: CallLogEntity): Long

    @Update
    suspend fun updateCallLog(callLog: CallLogEntity)

    @Delete
    suspend fun deleteCallLog(callLog: CallLogEntity)

    @Query("DELETE FROM call_logs WHERE id = :id")
    suspend fun deleteCallLogById(id: Int)

    @Query("DELETE FROM call_logs")
    suspend fun deleteAllCallLogs()

    @Query("SELECT COUNT(*) FROM call_logs")
    suspend fun getCallLogCount(): Int

    @Query("SELECT COUNT(*) FROM call_logs WHERE callDate >= :startDate AND callDate <= :endDate")
    suspend fun getCallLogCountInRange(startDate: Long, endDate: Long): Int
}
