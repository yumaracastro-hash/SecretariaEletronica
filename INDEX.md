# Índice do Projeto - Secretária Eletrônica

## Documentação

| Arquivo | Descrição | Tempo de Leitura |
|---------|-----------|-----------------|
| **QUICKSTART.md** | Guia de início rápido | 5-10 min |
| **README.md** | Documentação completa | 15-20 min |
| **DEVELOPMENT.md** | Guia para desenvolvedores | 20-30 min |
| **TROUBLESHOOTING.md** | FAQ e solução de problemas | 10-15 min |
| **PROJECT_SUMMARY.md** | Sumário executivo | 10-15 min |

## Estrutura do Código

### Data Layer (`app/src/main/kotlin/com/secretaria/eletronica/data/`)
- `AppDatabase.kt` - Configuração do Room Database
- `dao/GreetingDao.kt` - Operações com saudações
- `dao/CallLogDao.kt` - Operações com histórico de chamadas
- `entity/GreetingEntity.kt` - Modelo de dados de saudações
- `entity/CallLogEntity.kt` - Modelo de dados de chamadas
- `repository/GreetingRepository.kt` - Repositório de saudações
- `repository/CallLogRepository.kt` - Repositório de chamadas

### Service Layer (`app/src/main/kotlin/com/secretaria/eletronica/service/`)
- `CallMonitoringService.kt` - Monitoramento de chamadas
- `AutoAnswerService.kt` - Atendimento automático

### Receiver Layer (`app/src/main/kotlin/com/secretaria/eletronica/receiver/`)
- `CallReceiver.kt` - Receptor de eventos de chamadas
- `BootReceiver.kt` - Receptor de boot do dispositivo

### UI Layer (`app/src/main/kotlin/com/secretaria/eletronica/ui/`)
- `MainActivity.kt` - Activity principal
- `fragment/GreetingsFragment.kt` - Fragment de saudações
- `fragment/CallHistoryFragment.kt` - Fragment de histórico
- `adapter/GreetingAdapter.kt` - Adapter para lista de saudações
- `adapter/CallLogAdapter.kt` - Adapter para lista de chamadas
- `viewmodel/GreetingViewModel.kt` - ViewModel de saudações
- `viewmodel/CallLogViewModel.kt` - ViewModel de chamadas

### Utility Layer (`app/src/main/kotlin/com/secretaria/eletronica/util/`)
- `AudioManager.kt` - Gerenciamento de áudio
- `BackupManager.kt` - Gerenciamento de backup
- `ContactManager.kt` - Gerenciamento de contatos
- `Logger.kt` - Sistema de logging
- `PermissionManager.kt` - Gerenciamento de permissões
- `ScheduleManager.kt` - Agendamento automático

### Application (`app/src/main/kotlin/com/secretaria/eletronica/`)
- `SecretariaApp.kt` - Application class

## Recursos (Layouts e Configurações)

### Layouts (`app/src/main/res/layout/`)
- `activity_main.xml` - Layout da activity principal
- `fragment_greetings.xml` - Layout do fragment de saudações
- `fragment_call_history.xml` - Layout do fragment de histórico
- `item_greeting.xml` - Layout do item de saudação
- `item_call_log.xml` - Layout do item de chamada

### Valores (`app/src/main/res/values/`)
- `strings.xml` - Strings do aplicativo
- `colors.xml` - Paleta de cores
- `themes.xml` - Temas do Material Design

### Drawables (`app/src/main/res/drawable/`)
- `badge_background.xml` - Background para badges

### Navigation (`app/src/main/res/navigation/`)
- `nav_graph.xml` - Gráfico de navegação

### XML (`app/src/main/res/xml/`)
- `backup_descriptor.xml` - Configuração de backup
- `data_extraction_rules.xml` - Regras de extração de dados

## Configuração

### Build Files
- `build.gradle.kts` (root) - Configuração do projeto
- `app/build.gradle.kts` - Configuração do módulo app
- `settings.gradle.kts` - Configuração do Gradle
- `gradle.properties` - Propriedades do Gradle
- `app/proguard-rules.pro` - Regras de ProGuard

### Manifest
- `app/src/main/AndroidManifest.xml` - Configuração do aplicativo

## Estatísticas do Projeto

| Métrica | Valor |
|---------|-------|
| **Arquivos Kotlin** | 23 |
| **Arquivos XML** | 15 |
| **Arquivos Gradle** | 3 |
| **Arquivos de Documentação** | 5 |
| **Total de Linhas de Código** | ~2.500 |
| **Tamanho do Projeto** | ~31 KB (compactado) |

## Fluxo de Desenvolvimento Recomendado

1. **Leitura Inicial**: QUICKSTART.md (5-10 min)
2. **Instalação**: Seguir passos em QUICKSTART.md (5-10 min)
3. **Teste Básico**: Gravar e testar uma saudação (5 min)
4. **Documentação Completa**: README.md (15-20 min)
5. **Troubleshooting**: TROUBLESHOOTING.md conforme necessário
6. **Desenvolvimento**: DEVELOPMENT.md para modificações

## Checklist de Instalação

- [ ] Extrair arquivo do projeto
- [ ] Abrir no Android Studio
- [ ] Sincronizar Gradle
- [ ] Conectar dispositivo Android 10+
- [ ] Compilar projeto
- [ ] Instalar no dispositivo
- [ ] Abrir aplicativo
- [ ] Conceder permissões
- [ ] Gravar primeira saudação
- [ ] Testar atendimento automático

## Recursos Úteis

### Documentação Oficial
- [Android Developer](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [ViewModel Guide](https://developer.android.com/topic/libraries/architecture/viewmodel)

### Ferramentas Recomendadas
- Android Studio 2023.1+
- Android Emulator
- ADB (Android Debug Bridge)
- SQLite Browser (para inspecionar banco de dados)

## Contato e Suporte

Para dúvidas ou problemas:
1. Consultar TROUBLESHOOTING.md
2. Verificar logs do aplicativo
3. Revisar DEVELOPMENT.md para questões técnicas

---

**Versão**: 1.0.0  
**Data**: 2026-04-21  
**Linguagem**: Kotlin  
**Compatibilidade**: Android 10+
