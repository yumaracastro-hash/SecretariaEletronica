package com.secretaria.eletronica

import android.app.Application
import com.secretaria.eletronica.util.Logger

class SecretariaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inicializar logger
        Logger.initialize(this)
        Logger.i("SecretariaApp: Application created")
    }
}
