package com.secretaria.eletronica.util

import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Logger {

    private const val TAG = "SecretariaEletronica"
    private const val LOG_FILE_NAME = "app_logs.txt"
    private var logFile: File? = null

    fun initialize(context: Context) {
        try {
            val logsDir = File(context.getExternalFilesDir(null), "logs")
            if (!logsDir.exists()) {
                logsDir.mkdirs()
            }
            logFile = File(logsDir, LOG_FILE_NAME)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun d(message: String, throwable: Throwable? = null) {
        Log.d(TAG, message, throwable)
        writeToFile("DEBUG", message, throwable)
    }

    fun i(message: String, throwable: Throwable? = null) {
        Log.i(TAG, message, throwable)
        writeToFile("INFO", message, throwable)
    }

    fun w(message: String, throwable: Throwable? = null) {
        Log.w(TAG, message, throwable)
        writeToFile("WARN", message, throwable)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(TAG, message, throwable)
        writeToFile("ERROR", message, throwable)
    }

    private fun writeToFile(level: String, message: String, throwable: Throwable?) {
        try {
            if (logFile == null || !logFile!!.exists()) {
                return
            }

            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val logMessage = "[$timestamp] [$level] $message\n"

            logFile?.appendText(logMessage)

            // Limitar tamanho do arquivo de log
            if (logFile!!.length() > 5 * 1024 * 1024) { // 5MB
                logFile?.delete()
            }

            if (throwable != null) {
                logFile?.appendText("${throwable.stackTraceToString()}\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLogFile(): File? = logFile

    fun clearLogs() {
        try {
            logFile?.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
