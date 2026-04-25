package com.secretaria.eletronica.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.secretaria.eletronica.data.entity.GreetingEntity

@Dao
interface GreetingDao {
    @Query("SELECT * FROM greetings ORDER BY createdAt DESC")
    fun getAllGreetings(): LiveData<List<GreetingEntity>>

    @Query("SELECT * FROM greetings WHERE id = :id")
    suspend fun getGreetingById(id: Int): GreetingEntity?

    @Query("SELECT * FROM greetings WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveGreeting(): GreetingEntity?

    @Query("SELECT * FROM greetings WHERE isActive = 1")
    fun getActiveGreetingLive(): LiveData<GreetingEntity?>

    @Insert
    suspend fun insertGreeting(greeting: GreetingEntity): Long

    @Update
    suspend fun updateGreeting(greeting: GreetingEntity)

    @Delete
    suspend fun deleteGreeting(greeting: GreetingEntity)

    @Query("UPDATE greetings SET isActive = 0")
    suspend fun deactivateAllGreetings()

    @Query("UPDATE greetings SET isActive = 1 WHERE id = :id")
    suspend fun setActiveGreeting(id: Int)

    @Query("DELETE FROM greetings WHERE id = :id")
    suspend fun deleteGreetingById(id: Int)

    @Query("SELECT COUNT(*) FROM greetings")
    suspend fun getGreetingCount(): Int
}
