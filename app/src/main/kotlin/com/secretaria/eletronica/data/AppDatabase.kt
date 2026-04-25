package com.secretaria.eletronica.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.secretaria.eletronica.data.dao.CallLogDao
import com.secretaria.eletronica.data.dao.GreetingDao
import com.secretaria.eletronica.data.entity.CallLogEntity
import com.secretaria.eletronica.data.entity.GreetingEntity

@Database(
    entities = [GreetingEntity::class, CallLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun greetingDao(): GreetingDao
    abstract fun callLogDao(): CallLogDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "secretaria_eletronica.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
