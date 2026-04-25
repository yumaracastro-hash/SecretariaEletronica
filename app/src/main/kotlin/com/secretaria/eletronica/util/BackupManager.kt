package com.secretaria.eletronica.util

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class BackupManager(private val context: Context) {

    fun createBackup(): File? {
        return try {
            val backupDir = File(context.getExternalFilesDir(null), "backups")
            if (!backupDir.exists()) {
                backupDir.mkdirs()
            }

            val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
            val backupFile = File(backupDir, "backup_$timestamp.zip")

            val greetingsDir = File(context.getExternalFilesDir(null), "greetings")
            val databaseFile = context.getDatabasePath("secretaria_eletronica.db")

            ZipOutputStream(backupFile.outputStream()).use { zos ->
                // Adicionar arquivos de saudação
                if (greetingsDir.exists()) {
                    greetingsDir.listFiles()?.forEach { file ->
                        zos.putNextEntry(ZipEntry("greetings/${file.name}"))
                        file.inputStream().use { fis ->
                            fis.copyTo(zos)
                        }
                        zos.closeEntry()
                    }
                }

                // Adicionar banco de dados
                if (databaseFile.exists()) {
                    zos.putNextEntry(ZipEntry("database/${databaseFile.name}"))
                    databaseFile.inputStream().use { fis ->
                        fis.copyTo(zos)
                    }
                    zos.closeEntry()
                }
            }

            Logger.i("Backup criado com sucesso: ${backupFile.absolutePath}")
            backupFile
        } catch (e: Exception) {
            Logger.e("Erro ao criar backup", e)
            null
        }
    }

    fun getBackupList(): List<File> {
        return try {
            val backupDir = File(context.getExternalFilesDir(null), "backups")
            if (backupDir.exists()) {
                backupDir.listFiles()?.toList() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Logger.e("Erro ao listar backups", e)
            emptyList()
        }
    }

    fun deleteBackup(backupFile: File): Boolean {
        return try {
            backupFile.delete()
        } catch (e: Exception) {
            Logger.e("Erro ao deletar backup", e)
            false
        }
    }

    fun deleteOldBackups(maxBackups: Int = 5) {
        try {
            val backups = getBackupList().sortedByDescending { it.lastModified() }
            if (backups.size > maxBackups) {
                backups.drop(maxBackups).forEach { deleteBackup(it) }
            }
        } catch (e: Exception) {
            Logger.e("Erro ao deletar backups antigos", e)
        }
    }
}
