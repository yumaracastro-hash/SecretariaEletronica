# Guia de Desenvolvimento - Secretária Eletrônica

Este documento fornece informações para desenvolvedores que desejam estender ou modificar o aplicativo.

## Arquitetura da Aplicação

### Padrão MVVM

O aplicativo segue o padrão **Model-View-ViewModel (MVVM)**:

- **Model**: Camada de dados (Room Database, Repositories)
- **View**: Fragments e Activities (UI)
- **ViewModel**: Lógica de negócio e gerenciamento de estado

### Fluxo de Dados

```
UI (Fragment/Activity)
    ↓
ViewModel
    ↓
Repository
    ↓
DAO
    ↓
Room Database
```

## Estrutura de Diretórios

```
com/secretaria/eletronica/
├── data/
│   ├── AppDatabase.kt          # Configuração do Room Database
│   ├── dao/
│   │   ├── GreetingDao.kt      # DAO para saudações
│   │   └── CallLogDao.kt       # DAO para histórico de chamadas
│   ├── entity/
│   │   ├── GreetingEntity.kt   # Entidade de saudação
│   │   └── CallLogEntity.kt    # Entidade de chamada
│   └── repository/
│       ├── GreetingRepository.kt
│       └── CallLogRepository.kt
├── receiver/
│   ├── CallReceiver.kt         # Receptor de chamadas
│   └── BootReceiver.kt         # Receptor de boot
├── service/
│   ├── CallMonitoringService.kt    # Monitoramento de chamadas
│   └── AutoAnswerService.kt        # Atendimento automático
├── ui/
│   ├── MainActivity.kt
│   ├── adapter/
│   │   ├── GreetingAdapter.kt
│   │   └── CallLogAdapter.kt
│   ├── fragment/
│   │   ├── GreetingsFragment.kt
│   │   └── CallHistoryFragment.kt
│   └── viewmodel/
│       ├── GreetingViewModel.kt
│       └── CallLogViewModel.kt
└── util/
    ├── AudioManager.kt         # Gerenciamento de áudio
    ├── BackupManager.kt        # Backup de dados
    ├── ContactManager.kt       # Gerenciamento de contatos
    ├── Logger.kt               # Sistema de logs
    ├── PermissionManager.kt    # Gerenciamento de permissões
    └── ScheduleManager.kt      # Agendamento automático
```

## Componentes Principais

### 1. Room Database

**Arquivo**: `data/AppDatabase.kt`

```kotlin
@Database(
    entities = [GreetingEntity::class, CallLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun greetingDao(): GreetingDao
    abstract fun callLogDao(): CallLogDao
}
```

Para adicionar uma nova entidade:
1. Criar a classe de entidade com anotação `@Entity`
2. Criar o DAO correspondente
3. Atualizar o `AppDatabase` com a nova entidade
4. Incrementar a versão do banco de dados

### 2. ViewModels

**Exemplo**: `ui/viewmodel/GreetingViewModel.kt`

```kotlin
class GreetingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GreetingRepository
    
    fun insertGreeting(greeting: GreetingEntity) {
        viewModelScope.launch {
            repository.insertGreeting(greeting)
        }
    }
}
```

### 3. Serviços em Background

**Arquivo**: `service/CallMonitoringService.kt`

O serviço executa em background e monitora chamadas recebidas:

```kotlin
override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val phoneNumber = intent?.getStringExtra("PHONE_NUMBER") ?: ""
    if (phoneNumber.isNotEmpty()) {
        handleIncomingCall(phoneNumber)
    }
    return START_STICKY
}
```

### 4. Receptores de Broadcast

**Arquivo**: `receiver/CallReceiver.kt`

Recebe eventos de chamadas do sistema:

```kotlin
override fun onReceive(context: Context, intent: Intent) {
    when (intent.action) {
        TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
            handlePhoneStateChanged(context, intent)
        }
    }
}
```

## Adicionando Novas Funcionalidades

### Exemplo: Adicionar Nova Saudação com Categoria

1. **Atualizar a entidade**:
```kotlin
@Entity(tableName = "greetings")
data class GreetingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,  // Nova coluna
    // ... outros campos
)
```

2. **Atualizar o DAO**:
```kotlin
@Query("SELECT * FROM greetings WHERE category = :category")
suspend fun getGreetingsByCategory(category: String): List<GreetingEntity>
```

3. **Atualizar o Repository**:
```kotlin
suspend fun getGreetingsByCategory(category: String): List<GreetingEntity> {
    return withContext(Dispatchers.IO) {
        greetingDao.getGreetingsByCategory(category)
    }
}
```

4. **Atualizar o ViewModel**:
```kotlin
fun getGreetingsByCategory(category: String) {
    viewModelScope.launch {
        val greetings = repository.getGreetingsByCategory(category)
        // Usar os dados
    }
}
```

5. **Atualizar a UI**:
```kotlin
// No Fragment
viewModel.getGreetingsByCategory("lunch").observe(viewLifecycleOwner) { greetings ->
    adapter.submitList(greetings)
}
```

## Gerenciamento de Permissões

**Arquivo**: `util/PermissionManager.kt`

```kotlin
object PermissionManager {
    val CALL_PERMISSIONS = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ANSWER_PHONE_CALLS,
        // ...
    )
    
    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}
```

Para solicitar permissões no Fragment:
```kotlin
private val requestPermissionsLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    val allGranted = permissions.values.all { it }
    if (allGranted) {
        // Continuar com a ação
    }
}

requestPermissionsLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO))
```

## Sistema de Logging

**Arquivo**: `util/Logger.kt`

```kotlin
Logger.initialize(context)
Logger.d("Debug message")
Logger.i("Info message")
Logger.w("Warning message")
Logger.e("Error message", throwable)
```

Os logs são salvos em:
`/storage/emulated/0/Android/data/com.secretaria.eletronica/files/logs/app_logs.txt`

## Gerenciamento de Áudio

**Arquivo**: `util/AudioManager.kt`

```kotlin
val audioManager = AudioManager(context)

// Gravar
audioManager.startRecording("/path/to/file.3gp")
// ... falar
audioManager.stopRecording()

// Reproduzir
audioManager.startPlaying("/path/to/file.3gp")
// ... ouvir
audioManager.stopPlaying()
```

## Testes

### Testes Unitários

```kotlin
@Test
fun testGreetingRepository() {
    val greeting = GreetingEntity(
        name = "Test",
        description = "Test greeting"
    )
    
    // Testar inserção
    val id = repository.insertGreeting(greeting)
    assertNotNull(id)
}
```

### Testes de Integração

```kotlin
@RunWith(AndroidJUnit4::class)
class CallLogDaoTest {
    
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var database: AppDatabase
    private lateinit var callLogDao: CallLogDao
    
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        callLogDao = database.callLogDao()
    }
}
```

## Debugging

### Visualizar Banco de Dados

1. Abrir Android Studio
2. Ir para: View > Tool Windows > Device File Explorer
3. Navegar para: `/data/data/com.secretaria.eletronica/databases/`
4. Baixar o arquivo `secretaria_eletronica.db`
5. Usar uma ferramenta como SQLite Browser para inspecionar

### Visualizar Logs

```bash
adb logcat | grep "SecretariaEletronica"
```

### Debugger

1. Adicionar breakpoints no código
2. Executar em modo Debug (Shift+F9)
3. Usar o painel de Debug para inspecionar variáveis

## Performance

### Otimizações Implementadas

1. **Coroutines**: Operações assíncronas para não bloquear a UI
2. **LiveData**: Observação eficiente de mudanças de dados
3. **Room Database**: Queries otimizadas com índices
4. **ViewBinding**: Evita findViewById() repetido

### Melhorias Futuras

1. Implementar Paging para listas grandes
2. Usar WorkManager para tarefas agendadas
3. Implementar caching de contatos
4. Otimizar queries do banco de dados

## Segurança

### Boas Práticas Implementadas

1. **Permissões**: Solicitadas em tempo de execução
2. **Dados Sensíveis**: Armazenados localmente apenas
3. **Logs**: Não contêm informações sensíveis
4. **Backup**: Criptografado no Android 12+

### Recomendações

1. Não armazenar senhas ou tokens
2. Validar todas as entradas de usuário
3. Usar HTTPS para qualquer comunicação remota
4. Implementar criptografia para dados sensíveis

## Contribuindo

### Processo de Desenvolvimento

1. Criar uma branch para a feature: `git checkout -b feature/nova-funcionalidade`
2. Fazer commits com mensagens descritivas
3. Testar a funcionalidade
4. Criar um pull request
5. Aguardar revisão

### Padrões de Código

- Usar **camelCase** para variáveis e funções
- Usar **PascalCase** para classes
- Adicionar comentários para lógica complexa
- Seguir as convenções do Kotlin
- Manter linhas com máximo de 100 caracteres

## Recursos Úteis

- [Documentação Android](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [ViewModel Guide](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Coroutines Guide](https://kotlinlang.org/docs/coroutines-overview.html)

## Suporte

Para dúvidas ou problemas, consulte os logs do aplicativo ou abra uma issue no repositório.

---

**Última atualização**: 2026-04-21
