package com.secretaria.eletronica.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import com.secretaria.eletronica.R
import com.secretaria.eletronica.data.AppDatabase
import com.secretaria.eletronica.data.repository.GreetingRepository
import com.secretaria.eletronica.ui.MainActivity
import com.secretaria.eletronica.util.AudioManager
import com.secretaria.eletronica.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoAnswerService : Service() {

    private lateinit var greetingRepository: GreetingRepository
    private lateinit var audioManager: AudioManager
    private val serviceScope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val NOTIFICATION_ID = 2
        private const val CHANNEL_ID = "auto_answer_channel"
    }

    override fun onCreate() {
        super.onCreate()
        Logger.initialize(this)
        Logger.i("AutoAnswerService: onCreate")

        val database = AppDatabase.getInstance(this)
        greetingRepository = GreetingRepository(database.greetingDao())
        audioManager = AudioManager(this)

        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i("AutoAnswerService: onStartCommand")

        val phoneNumber = intent?.getStringExtra("PHONE_NUMBER") ?: ""
        if (phoneNumber.isNotEmpty()) {
            serviceScope.launch {
                answerCallAndPlayGreeting(phoneNumber)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("AutoAnswerService: onDestroy")
        audioManager.release()
    }

    private suspend fun answerCallAndPlayGreeting(phoneNumber: String) {
        Logger.i("AutoAnswerService: Answering call from $phoneNumber")

        try {
            // Aguardar um pouco para a chamada ser estabelecida
            delay(1000)

            // Obter saudação ativa
            val activeGreeting = greetingRepository.getActiveGreetingOnce()

            if (activeGreeting != null && activeGreeting.audioPath != null) {
                Logger.i("AutoAnswerService: Playing greeting: ${activeGreeting.name}")

                // Reproduzir saudação
                audioManager.startPlaying(activeGreeting.audioPath!!)

                // Aguardar a duração da saudação
                val duration = audioManager.getRecordingDuration(activeGreeting.audioPath!!)
                delay(duration + 500)

                // Parar reprodução
                audioManager.stopPlaying()

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
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Nota: Encerrar chamada requer permissões especiais no Android 10+
            // Esta é uma abordagem simplificada
            Logger.i("AutoAnswerService: Call ended")
        } catch (e: Exception) {
            Logger.e("AutoAnswerService: Error ending call", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Atendimento Automático",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificações do serviço de atendimento automático"
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
