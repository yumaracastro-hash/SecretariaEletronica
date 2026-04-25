package com.secretaria.eletronica.data.repository

import androidx.lifecycle.LiveData
import com.secretaria.eletronica.data.dao.GreetingDao
import com.secretaria.eletronica.data.entity.GreetingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GreetingRepository(private val greetingDao: GreetingDao) {

    fun getAllGreetings(): LiveData<List<GreetingEntity>> {
        return greetingDao.getAllGreetings()
    }

    fun getActiveGreeting(): LiveData<GreetingEntity?> {
        return greetingDao.getActiveGreetingLive()
    }

    suspend fun getActiveGreetingOnce(): GreetingEntity? {
        return withContext(Dispatchers.IO) {
            greetingDao.getActiveGreeting()
        }
    }

    suspend fun getGreetingById(id: Int): GreetingEntity? {
        return withContext(Dispatchers.IO) {
            greetingDao.getGreetingById(id)
        }
    }

    suspend fun insertGreeting(greeting: GreetingEntity): Long {
        return withContext(Dispatchers.IO) {
            greetingDao.insertGreeting(greeting)
        }
    }

    suspend fun updateGreeting(greeting: GreetingEntity) {
        return withContext(Dispatchers.IO) {
            greetingDao.updateGreeting(greeting)
        }
    }

    suspend fun deleteGreeting(greeting: GreetingEntity) {
        return withContext(Dispatchers.IO) {
            greetingDao.deleteGreeting(greeting)
        }
    }

    suspend fun setActiveGreeting(id: Int) {
        return withContext(Dispatchers.IO) {
            greetingDao.deactivateAllGreetings()
            greetingDao.setActiveGreeting(id)
        }
    }

    suspend fun deleteGreetingById(id: Int) {
        return withContext(Dispatchers.IO) {
            greetingDao.deleteGreetingById(id)
        }
    }

    suspend fun getGreetingCount(): Int {
        return withContext(Dispatchers.IO) {
            greetingDao.getGreetingCount()
        }
    }
}
