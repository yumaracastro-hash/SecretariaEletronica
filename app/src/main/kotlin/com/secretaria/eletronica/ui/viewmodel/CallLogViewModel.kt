package com.secretaria.eletronica.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.secretaria.eletronica.data.AppDatabase
import com.secretaria.eletronica.data.entity.CallLogEntity
import com.secretaria.eletronica.data.repository.CallLogRepository
import kotlinx.coroutines.launch

class CallLogViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CallLogRepository
    val allCallLogs: LiveData<List<CallLogEntity>>

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _selectedCallLog = MutableLiveData<CallLogEntity?>()
    val selectedCallLog: LiveData<CallLogEntity?> = _selectedCallLog

    init {
        val database = AppDatabase.getInstance(application)
        repository = CallLogRepository(database.callLogDao())
        allCallLogs = repository.getAllCallLogs()
    }

    fun insertCallLog(callLog: CallLogEntity) {
        viewModelScope.launch {
            try {
                repository.insertCallLog(callLog)
            } catch (e: Exception) {
                _message.postValue("Erro ao registrar chamada: ${e.message}")
            }
        }
    }

    fun deleteCallLog(callLog: CallLogEntity) {
        viewModelScope.launch {
            try {
                repository.deleteCallLog(callLog)
                _message.postValue("Chamada deletada com sucesso")
            } catch (e: Exception) {
                _message.postValue("Erro ao deletar chamada: ${e.message}")
            }
        }
    }

    fun deleteAllCallLogs() {
        viewModelScope.launch {
            try {
                repository.deleteAllCallLogs()
                _message.postValue("Histórico de chamadas limpo")
            } catch (e: Exception) {
                _message.postValue("Erro ao limpar histórico: ${e.message}")
            }
        }
    }

    fun selectCallLog(callLog: CallLogEntity) {
        _selectedCallLog.value = callLog
    }

    fun getCallLogsByNumber(phoneNumber: String) {
        viewModelScope.launch {
            try {
                val logs = repository.getCallLogsByNumber(phoneNumber)
                _message.postValue("${logs.size} chamadas encontradas")
            } catch (e: Exception) {
                _message.postValue("Erro ao buscar chamadas: ${e.message}")
            }
        }
    }

    fun getCallLogCount() {
        viewModelScope.launch {
            try {
                val count = repository.getCallLogCount()
                _message.postValue("Total de chamadas: $count")
            } catch (e: Exception) {
                _message.postValue("Erro ao contar chamadas: ${e.message}")
            }
        }
    }
}
