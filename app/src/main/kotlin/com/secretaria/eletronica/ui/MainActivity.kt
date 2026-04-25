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
                "Permissões necessárias não foram concedidas. O app pode não funcionar corretamente.",
                Toast.LENGTH_LONG
            ).show()
            Logger.w("MainActivity: Some permissions denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Logger.initialize(this)
            Logger.i("MainActivity: onCreate")
        } catch (e: Exception) {
            // Logger initialization failed, continue without logging
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Navigation com FragmentContainerView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as? NavHostFragment

        if (navHostFragment != null) {
            navController = navHostFragment.navController

            // Configurar BottomNavigationView com NavController
            binding.bottomNavigation.setupWithNavController(navController)
        } else {
            Logger.e("MainActivity: NavHostFragment not found")
        }

        // Solicitar permissões
        requestPermissions()
    }

    private fun requestPermissions() {
        try {
            val permissionsToRequest = mutableListOf<String>()

            // Adicionar permissões necessárias
            permissionsToRequest.addAll(PermissionManager.CALL_PERMISSIONS)
            permissionsToRequest.addAll(PermissionManager.AUDIO_PERMISSIONS)

            // Remover permissões já concedidas
            val permissionsToRequestFiltered = permissionsToRequest.filter {
                !PermissionManager.hasPermission(this, it)
            }.toTypedArray()

            if (permissionsToRequestFiltered.isNotEmpty()) {
                Logger.i("MainActivity: Requesting permissions: ${permissionsToRequestFiltered.joinToString()}")
                requestPermissionsLauncher.launch(permissionsToRequestFiltered)
            } else {
                // Todas as permissões já concedidas, iniciar serviço
                startCallMonitoringService()
            }
        } catch (e: Exception) {
            Logger.e("MainActivity: Error requesting permissions", e)
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            Logger.i("MainActivity: onDestroy")
        } catch (e: Exception) {
            // Ignore
        }
    }
}
