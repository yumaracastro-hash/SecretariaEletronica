package com.secretaria.eletronica.util

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class AudioManager(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isRecording = false
    private var isPlaying = false

    fun startRecording(filePath: String): Boolean {
        return try {
            // Criar diretório se não existir
            val file = File(filePath)
            file.parentFile?.mkdirs()

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(filePath)
                prepare()
                start()
            }

            isRecording = true
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun stopRecording(): Boolean {
        return try {
            if (isRecording && mediaRecorder != null) {
                mediaRecorder?.apply {
                    stop()
                    release()
                }
                mediaRecorder = null
                isRecording = false
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun startPlaying(filePath: String): Boolean {
        return try {
            if (!File(filePath).exists()) {
                return false
            }

            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare()
                start()
            }

            isPlaying = true
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun stopPlaying(): Boolean {
        return try {
            if (isPlaying && mediaPlayer != null) {
                mediaPlayer?.apply {
                    stop()
                    release()
                }
                mediaPlayer = null
                isPlaying = false
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun pausePlaying(): Boolean {
        return try {
            if (isPlaying && mediaPlayer != null) {
                mediaPlayer?.pause()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun resumePlaying(): Boolean {
        return try {
            if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                mediaPlayer?.start()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getRecordingDuration(filePath: String): Long {
        return try {
            if (!File(filePath).exists()) {
                return 0L
            }

            val tempPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare()
            }

            val duration = tempPlayer.duration.toLong()
            tempPlayer.release()
            duration
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    fun isRecording(): Boolean = isRecording

    fun isPlaying(): Boolean = isPlaying

    fun release() {
        try {
            if (isRecording) {
                stopRecording()
            }
            if (isPlaying) {
                stopPlaying()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
