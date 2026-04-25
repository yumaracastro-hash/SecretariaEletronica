package com.secretaria.eletronica.data.repository

import androidx.lifecycle.LiveData
import com.secretaria.eletronica.data.dao.CallLogDao
import com.secretaria.eletronica.data.entity.CallLogEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CallLogRepository(private val callLogDao: CallLogDao) {

    fun getAllCallLogs(): LiveData<List<CallLogEntity>> {
        return callLogDao.getAllCallLogs()
    }

    fun getCallLogsBySimSlot(simSlot: Int): LiveData<List<CallLogEntity>> {
        return callLogDao.getCallLogsBySimSlot(simSlot)
    }

    suspend fun getCallLogById(id: Int): CallLogEntity? {
        return withContext(Dispatchers.IO) {
            callLogDao.getCallLogById(id)
        }
    }

    suspend fun getRecentCallLogs(limit: Int = 50): List<CallLogEntity> {
        return withContext(Dispatchers.IO) {
            callLogDao.getRecentCallLogs(limit)
        }
    }

    suspend fun getCallLogsByNumber(phoneNumber: String): List<CallLogEntity> {
        return withContext(Dispatchers.IO) {
            callLogDao.getCallLogsByNumber(phoneNumber)
        }
    }

    suspend fun insertCallLog(callLog: CallLogEntity): Long {
        return withContext(Dispatchers.IO) {
            callLogDao.insertCallLog(callLog)
        }
    }

    suspend fun updateCallLog(callLog: CallLogEntity) {
        return withContext(Dispatchers.IO) {
            callLogDao.updateCallLog(callLog)
        }
    }

    suspend fun deleteCallLog(callLog: CallLogEntity) {
        return withContext(Dispatchers.IO) {
            callLogDao.deleteCallLog(callLog)
        }
    }

    suspend fun deleteCallLogById(id: Int) {
        return withContext(Dispatchers.IO) {
            callLogDao.deleteCallLogById(id)
        }
    }

    suspend fun deleteAllCallLogs() {
        return withContext(Dispatchers.IO) {
            callLogDao.deleteAllCallLogs()
        }
    }

    suspend fun getCallLogCount(): Int {
        return withContext(Dispatchers.IO) {
            callLogDao.getCallLogCount()
        }
    }

    suspend fun getCallLogCountInRange(startDate: Long, endDate: Long): Int {
        return withContext(Dispatchers.IO) {
            callLogDao.getCallLogCountInRange(startDate, endDate)
        }
    }
}
