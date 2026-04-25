package com.secretaria.eletronica.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.secretaria.eletronica.service.CallMonitoringService
import com.secretaria.eletronica.util.Logger

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Logger.i("BootReceiver: onReceive - ${intent.action}")

        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            Logger.i("BootReceiver: Device boot completed, starting monitoring service")
            startMonitoringService(context)
        }
    }

    private fun startMonitoringService(context: Context) {
        try {
            val serviceIntent = Intent(context, CallMonitoringService::class.java)
            context.startService(serviceIntent)
            Logger.i("BootReceiver: Monitoring service started")
        } catch (e: Exception) {
            Logger.e("BootReceiver: Error starting service", e)
        }
    }
}
