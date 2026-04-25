package com.secretaria.eletronica.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.secretaria.eletronica.R
import com.secretaria.eletronica.databinding.ActivityMainBinding
import com.secretaria.eletronica.service.CallMonitoringService
import com.secretaria.eletronica.util.Logger
import com.secretaria.eletronica.util.PermissionManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            Logger.i("MainActivity: All permissions granted")
            startCallMonitoringService()
        } else {
            Toast.makeText(
                this,
                "Permissões necessárias não foram concedidas",
                Toast.LENGTH_SHORT
            ).show()
            Logger.w("MainActivity: Some permissions denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.initialize(this)
        Logger.i("MainActivity: onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Configurar Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Solicitar permissões
        requestPermissions()

        // Iniciar serviço de monitoramento
        startCallMonitoringService()
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // Adicionar permissões necessárias
        permissionsToRequest.addAll(PermissionManager.CALL_PERMISSIONS)
        permissionsToRequest.addAll(PermissionManager.AUDIO_PERMISSIONS)
        permissionsToRequest.addAll(PermissionManager.STORAGE_PERMISSIONS)

        // Remover permissões já concedidas
        val permissionsToRequestFiltered = permissionsToRequest.filter {
            !PermissionManager.hasPermission(this, it)
        }.toTypedArray()

        if (permissionsToRequestFiltered.isNotEmpty()) {
            Logger.i("MainActivity: Requesting permissions: ${permissionsToRequestFiltered.joinToString()}")
            requestPermissionsLauncher.launch(permissionsToRequestFiltered)
        }
    }

    private fun startCallMonitoringService() {
        try {
            val serviceIntent = Intent(this, CallMonitoringService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
            Logger.i("MainActivity: Call monitoring service started")
        } catch (e: Exception) {
            Logger.e("MainActivity: Error starting service", e)
            Toast.makeText(
                this,
                "Erro ao iniciar serviço de monitoramento",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("MainActivity: onDestroy")
    }
}
