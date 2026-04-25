package com.secretaria.eletronica.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "call_logs")
data class CallLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val phoneNumber: String,
    val contactName: String? = null,
    val callDate: Long = System.currentTimeMillis(),
    val callDuration: Long = 0L,
    val simSlot: Int = 1,
    val greetingUsed: String? = null,
    val callType: String = "INCOMING"
) : Serializable
