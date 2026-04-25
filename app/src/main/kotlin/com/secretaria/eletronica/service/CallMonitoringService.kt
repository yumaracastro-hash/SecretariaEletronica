package com.secretaria.eletronica.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.secretaria.eletronica.R
import com.secretaria.eletronica.data.AppDatabase
import com.secretaria.eletronica.data.entity.CallLogEntity
import com.secretaria.eletronica.data.repository.CallLogRepository
import com.secretaria.eletronica.data.repository.GreetingRepository
import com.secretaria.eletronica.ui.MainActivity
import com.secretaria.eletronica.util.ContactManager
import com.secretaria.eletronica.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CallMonitoringService : Service() {

    private var callLogRepository: CallLogRepository? = null
    private var greetingRepository: GreetingRepository? = null
    private var contactManager: ContactManager? = null
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "call_monitoring_channel"
    }

    override fun onCreate() {
        super.onCreate()
        try {
            Logger.initialize(this)
            Logger.i("CallMonitoringService: onCreate")
        } catch (e: Exception) {
            // Continue without logging
        }

        try {
            val database = AppDatabase.getInstance(this)
            callLogRepository = CallLogRepository(database.callLogDao())
            greetingRepository = GreetingRepository(database.greetingDao())
            contactManager = ContactManager(this)
        } catch (e: Exception) {
            Logger.e("CallMonitoringService: Error initializing repositories", e)
        }

        createNotificationChannel()

        try {
            startForeground(NOTIFICATION_ID, createNotification())
        } catch (e: Exception) {
            Logger.e("CallMonitoringService: Error starting foreground", e)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            Logger.i("CallMonitoringService: onStartCommand")

            val phoneNumber = intent?.getStringExtra("PHONE_NUMBER") ?: ""
            if (phoneNumber.isNotEmpty()) {
                handleIncomingCall(phoneNumber)
            }
        } catch (e: Exception) {
            Logger.e("CallMonitoringService: Error in onStartCommand", e)
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            serviceScope.cancel()
            Logger.i("CallMonitoringService: onDestroy")
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun handleIncomingCall(phoneNumber: String) {
        Logger.i("CallMonitoringService: Handling incoming call from $phoneNumber")

        serviceScope.launch {
            try {
                // Obter nome do contato
                val contactName = try {
                    contactManager?.getContactNameByNumber(phoneNumber)
                } catch (e: Exception) {
                    null
                }

                // Registrar chamada no banco de dados
                val callLog = CallLogEntity(
                    phoneNumber = phoneNumber,
                    contactName = contactName,
                    callDate = System.currentTimeMillis(),
                    simSlot = 1,
                    callType = "INCOMING"
                )

                callLogRepository?.insertCallLog(callLog)
                Logger.i("CallMonitoringService: Call logged - $phoneNumber")

                // Iniciar serviço de atendimento automático
                val autoAnswerIntent = Intent(this@CallMonitoringService, AutoAnswerService::class.java).apply {
                    putExtra("PHONE_NUMBER", phoneNumber)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(autoAnswerIntent)
                } else {
                    startService(autoAnswerIntent)
                }

            } catch (e: Exception) {
                Logger.e("CallMonitoringService: Error handling call", e)
            }
        }
    }

    private fun createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Monitoramento de Chamadas",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Notificações do serviço de monitoramento de chamadas"
                    setShowBadge(false)
                }

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        } catch (e: Exception) {
            Logger.e("CallMonitoringService: Error creating notification channel", e)
        }
    }

    private fun createNotification(): android.app.Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Secretária Eletrônica")
            .setContentText("Monitorando chamadas...")
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
