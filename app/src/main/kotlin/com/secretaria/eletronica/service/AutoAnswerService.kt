package com.secretaria.eletronica.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telecom.TelecomManager
import androidx.core.app.NotificationCompat
import com.secretaria.eletronica.data.AppDatabase
import com.secretaria.eletronica.data.repository.GreetingRepository
import com.secretaria.eletronica.ui.MainActivity
import com.secretaria.eletronica.util.AudioManager
import com.secretaria.eletronica.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoAnswerService : Service() {

    private var greetingRepository: GreetingRepository? = null
    private var audioManager: AudioManager? = null
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    companion object {
        private const val NOTIFICATION_ID = 2
        private const val CHANNEL_ID = "auto_answer_channel"
    }

    override fun onCreate() {
        super.onCreate()
        try {
            Logger.initialize(this)
            Logger.i("AutoAnswerService: onCreate")
        } catch (e: Exception) {
            // Continue without logging
        }

        try {
            val database = AppDatabase.getInstance(this)
            greetingRepository = GreetingRepository(database.greetingDao())
            audioManager = AudioManager(this)
        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error initializing", e)
        }

        createNotificationChannel()

        try {
            startForeground(NOTIFICATION_ID, createNotification())
        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error starting foreground", e)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            Logger.i("AutoAnswerService: onStartCommand")

            val phoneNumber = intent?.getStringExtra("PHONE_NUMBER") ?: ""
            if (phoneNumber.isNotEmpty()) {
                serviceScope.launch {
                    answerCallAndPlayGreeting(phoneNumber)
                }
            }
        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error in onStartCommand", e)
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            audioManager?.release()
            serviceScope.cancel()
            Logger.i("AutoAnswerService: onDestroy")
        } catch (e: Exception) {
            // Ignore
        }
    }

    private suspend fun answerCallAndPlayGreeting(phoneNumber: String) {
        Logger.i("AutoAnswerService: Answering call from $phoneNumber")

        try {
            // Aguardar um pouco para a chamada ser estabelecida
            delay(1000)

            // Obter saudação ativa
            val activeGreeting = greetingRepository?.getActiveGreetingOnce()

            if (activeGreeting != null && activeGreeting.audioPath != null) {
                Logger.i("AutoAnswerService: Playing greeting: ${activeGreeting.name}")

                // Reproduzir saudação
                audioManager?.startPlaying(activeGreeting.audioPath!!)

                // Aguardar a duração da saudação
                val duration = audioManager?.getRecordingDuration(activeGreeting.audioPath!!) ?: 5000L
                delay(duration + 500)

                // Parar reprodução
                audioManager?.stopPlaying()

                Logger.i("AutoAnswerService: Greeting finished, ending call")
            } else {
                Logger.w("AutoAnswerService: No active greeting found")
            }

            // Encerrar a chamada
            endCall()

        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error during auto answer", e)
        } finally {
            stopSelf()
        }
    }

    private fun endCall() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                telecomManager.endCall()
            }
            Logger.i("AutoAnswerService: Call ended")
        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error ending call", e)
        }
    }

    private fun createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Atendimento Automático",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Notificações do serviço de atendimento automático"
                    setShowBadge(false)
                }

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error creating notification channel", e)
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
            .setContentText("Atendendo chamada...")
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
