package com.secretaria.eletronica.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.secretaria.eletronica.data.AppDatabase
import com.secretaria.eletronica.data.entity.GreetingEntity
import com.secretaria.eletronica.data.repository.GreetingRepository
import kotlinx.coroutines.launch

class GreetingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GreetingRepository
    val allGreetings: LiveData<List<GreetingEntity>>
    val activeGreeting: LiveData<GreetingEntity?>

    private val _recordingState = MutableLiveData<RecordingState>(RecordingState.IDLE)
    val recordingState: LiveData<RecordingState> = _recordingState

    private val _selectedGreeting = MutableLiveData<GreetingEntity?>()
    val selectedGreeting: LiveData<GreetingEntity?> = _selectedGreeting

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        val database = AppDatabase.getInstance(application)
        repository = GreetingRepository(database.greetingDao())
        allGreetings = repository.getAllGreetings()
        activeGreeting = repository.getActiveGreeting()
    }

    fun insertGreeting(greeting: GreetingEntity) {
        viewModelScope.launch {
            try {
                repository.insertGreeting(greeting)
                _message.postValue("Saudação criada com sucesso")
            } catch (e: Exception) {
                _message.postValue("Erro ao criar saudação: ${e.message}")
            }
        }
    }

    fun updateGreeting(greeting: GreetingEntity) {
        viewModelScope.launch {
            try {
                repository.updateGreeting(greeting)
                _message.postValue("Saudação atualizada com sucesso")
            } catch (e: Exception) {
                _message.postValue("Erro ao atualizar saudação: ${e.message}")
            }
        }
    }

    fun deleteGreeting(greeting: GreetingEntity) {
        viewModelScope.launch {
            try {
                repository.deleteGreeting(greeting)
                _message.postValue("Saudação deletada com sucesso")
            } catch (e: Exception) {
                _message.postValue("Erro ao deletar saudação: ${e.message}")
            }
        }
    }

    fun setActiveGreeting(id: Int) {
        viewModelScope.launch {
            try {
                repository.setActiveGreeting(id)
                _message.postValue("Saudação ativada com sucesso")
            } catch (e: Exception) {
                _message.postValue("Erro ao ativar saudação: ${e.message}")
            }
        }
    }

    fun selectGreeting(greeting: GreetingEntity) {
        _selectedGreeting.value = greeting
    }

    fun setRecordingState(state: RecordingState) {
        _recordingState.value = state
    }

    fun initializeDefaultGreetings() {
        viewModelScope.launch {
            try {
                val count = repository.getGreetingCount()
                if (count == 0) {
                    val defaultGreetings = listOf(
                        GreetingEntity(
                            name = "Horário de Almoço",
                            description = "Mensagem para horário de almoço"
                        ),
                        GreetingEntity(
                            name = "Fora do Horário Comercial",
                            description = "Mensagem para fora do horário comercial"
                        ),
                        GreetingEntity(
                            name = "Finais de Semana",
                            description = "Mensagem para finais de semana"
                        ),
                        GreetingEntity(
                            name = "Feriados",
                            description = "Mensagem para feriados"
                        ),
                        GreetingEntity(
                            name = "Férias",
                            description = "Mensagem para período de férias"
                        ),
                        GreetingEntity(
                            name = "Personalizado 1",
                            description = "Mensagem personalizada 1"
                        ),
                        GreetingEntity(
                            name = "Personalizado 2",
                            description = "Mensagem personalizada 2"
                        ),
                        GreetingEntity(
                            name = "Personalizado 3",
                            description = "Mensagem personalizada 3"
                        ),
                        GreetingEntity(
                            name = "Personalizado 4",
                            description = "Mensagem personalizada 4"
                        ),
                        GreetingEntity(
                            name = "Personalizado 5",
                            description = "Mensagem personalizada 5",
                            isActive = true
                        )
                    )

                    defaultGreetings.forEach { greeting ->
                        repository.insertGreeting(greeting)
                    }
                    _message.postValue("Saudações padrão inicializadas")
                }
            } catch (e: Exception) {
                _message.postValue("Erro ao inicializar saudações: ${e.message}")
            }
        }
    }

    enum class RecordingState {
        IDLE, RECORDING, PLAYING, PAUSED
    }
}
