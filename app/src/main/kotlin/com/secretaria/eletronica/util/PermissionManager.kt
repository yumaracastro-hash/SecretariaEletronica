package com.secretaria.eletronica.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionManager {

    val CALL_PERMISSIONS: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS
        )
    }

    val AUDIO_PERMISSIONS: Array<String> = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    val ALL_PERMISSIONS: Array<String> = CALL_PERMISSIONS + AUDIO_PERMISSIONS

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        return permissions.all { hasPermission(context, it) }
    }

    fun hasCallPermissions(context: Context): Boolean {
        return hasPermissions(context, CALL_PERMISSIONS)
    }

    fun hasAudioPermissions(context: Context): Boolean {
        return hasPermissions(context, AUDIO_PERMISSIONS)
    }

    fun hasAllPermissions(context: Context): Boolean {
        return hasPermissions(context, ALL_PERMISSIONS)
    }
}
