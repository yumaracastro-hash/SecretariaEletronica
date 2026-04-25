package com.secretaria.eletronica

import android.app.Application
import com.secretaria.eletronica.util.Logger

class SecretariaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            // Inicializar logger
            Logger.initialize(this)
            Logger.i("SecretariaApp: Application created")
        } catch (e: Exception) {
            // Continue without logging if initialization fails
            e.printStackTrace()
        }
    }
}
