package com.secretaria.eletronica.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyManager
import com.secretaria.eletronica.service.CallMonitoringService
import com.secretaria.eletronica.util.Logger

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            Logger.initialize(context)
            Logger.i("CallReceiver: onReceive - ${intent.action}")

            when (intent.action) {
                TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                    handlePhoneStateChanged(context, intent)
                }
                Intent.ACTION_NEW_OUTGOING_CALL -> {
                    handleNewOutgoingCall(context, intent)
                }
            }
        } catch (e: Exception) {
            // Ignore errors in receiver
        }
    }

    private fun handlePhoneStateChanged(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) ?: ""

        Logger.i("CallReceiver: Phone state changed - $state, Number: $incomingNumber")

        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> {
                Logger.i("CallReceiver: Incoming call from $incomingNumber")
                if (incomingNumber.isNotEmpty()) {
                    startCallMonitoringService(context, incomingNumber)
                }
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                Logger.i("CallReceiver: Call answered")
            }
            TelephonyManager.EXTRA_STATE_IDLE -> {
                Logger.i("CallReceiver: Call ended")
            }
        }
    }

    private fun handleNewOutgoingCall(context: Context, intent: Intent) {
        val outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER) ?: ""
        Logger.i("CallReceiver: Outgoing call to $outgoingNumber")
    }

    private fun startCallMonitoringService(context: Context, phoneNumber: String) {
        try {
            val serviceIntent = Intent(context, CallMonitoringService::class.java).apply {
                putExtra("PHONE_NUMBER", phoneNumber)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        } catch (e: Exception) {
            Logger.e("CallReceiver: Error starting service", e)
        }
    }
}
