# Secretária Eletrônica

Um aplicativo Android nativo desenvolvido em Kotlin que funciona como uma secretária eletrônica inteligente integrada a chamadas telefônicas e WhatsApp.

## Características Principais

### 1. Atendimento Automático de Chamadas
- Atende automaticamente chamadas de voz recebidas
- Detecta de qual SIM (SIM 1 ou SIM 2) a chamada está sendo recebida
- Suporta múltiplos números telefônicos:
  - Celular (Vivo): +55 31 99983-0246
  - WhatsApp (voz): +55 31 99983-0246
  - Telefone fixo via chip (Claro): +55 31 3241-2123

### 2. Mensagens de Saudação Personalizadas
- 10 opções de mensagens de saudação configuráveis
- Gravação de áudio para cada mensagem (até 60 segundos)
- Categorias predefinidas:
  - Horário de Almoço
  - Fora do Horário Comercial
  - Finais de Semana
  - Feriados
  - Férias
  - 5 Personalizadas

### 3. Fluxo de Atendimento
- Reprodução automática da saudação selecionada
- Sem gravação de recado
- Encerramento automático da chamada ao final da mensagem

### 4. Registro de Chamadas
- Salva automaticamente:
  - Número que ligou
  - Data e hora
  - SIM utilizado
  - Nome do contato (se disponível)
- Interface para visualizar histórico de chamadas atendidas

### 5. Interface do Usuário
- Tela principal com:
  - Seleção rápida da saudação ativa
  - Botão de gravação
  - Lista de chamadas recebidas
- Ícone de atalho na tela inicial (launcher)
- Design moderno com Material Design 3

### 6. Funcionalidades Extras
- Ativação automática da saudação com base em horário/dia
- Backup das mensagens gravadas
- Logs de funcionamento
- Serviços em background para monitoramento contínuo

## Requisitos Técnicos

### Compatibilidade
- **Dispositivo**: Samsung Galaxy S20 FE (ou similar)
- **Android**: 10 ou superior (API 29+)
- **Arquitetura**: ARM64

### Permissões Necessárias
- `READ_PHONE_STATE` - Ler estado do telefone
- `ANSWER_PHONE_CALLS` - Atender chamadas automaticamente
- `CALL_PHONE` - Fazer chamadas
- `READ_CALL_LOG` - Ler histórico de chamadas
- `WRITE_CALL_LOG` - Escrever no histórico de chamadas
- `READ_CONTACTS` - Ler contatos
- `RECORD_AUDIO` - Gravar áudio
- `MODIFY_AUDIO_SETTINGS` - Modificar configurações de áudio
- `READ_EXTERNAL_STORAGE` - Ler armazenamento externo
- `WRITE_EXTERNAL_STORAGE` - Escrever no armazenamento externo
- `INTERNET` - Acesso à internet
- `WAKE_LOCK` - Manter dispositivo ativo
- `VIBRATE` - Vibração do dispositivo
- `READ_SMS` - Ler SMS
- `RECEIVE_SMS` - Receber SMS
- `FOREGROUND_SERVICE` - Serviço em primeiro plano

### Arquitetura
- **Padrão**: MVVM (Model-View-ViewModel)
- **Banco de Dados**: Room Database
- **Persistência**: SQLite
- **Async**: Coroutines
- **Injeção de Dependência**: Manual (pode ser expandido com Hilt)

## Estrutura do Projeto

```
SecretariaEletronica/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/secretaria/eletronica/
│   │   │   │   ├── data/
│   │   │   │   │   ├── dao/
│   │   │   │   │   ├── entity/
│   │   │   │   │   ├── repository/
│   │   │   │   │   └── AppDatabase.kt
│   │   │   │   ├── receiver/
│   │   │   │   │   ├── CallReceiver.kt
│   │   │   │   │   └── BootReceiver.kt
│   │   │   │   ├── service/
│   │   │   │   │   ├── CallMonitoringService.kt
│   │   │   │   │   └── AutoAnswerService.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── adapter/
│   │   │   │   │   ├── fragment/
│   │   │   │   │   └── viewmodel/
│   │   │   │   └── util/
│   │   │   │       ├── AudioManager.kt
│   │   │   │       ├── BackupManager.kt
│   │   │   │       ├── ContactManager.kt
│   │   │   │       ├── Logger.kt
│   │   │   │       ├── PermissionManager.kt
│   │   │   │       └── ScheduleManager.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── values/
│   │   │   │   ├── drawable/
│   │   │   │   ├── navigation/
│   │   │   │   └── xml/
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Instalação e Compilação

### Pré-requisitos
- Android Studio 2023.1 ou superior
- JDK 17 ou superior
- SDK Android 34 ou superior
- Gradle 8.0 ou superior

### Passos para Compilação

1. **Clonar/Extrair o Projeto**
```bash
cd SecretariaEletronica
```

2. **Abrir no Android Studio**
```bash
# Ou abrir diretamente no Android Studio
```

3. **Sincronizar Gradle**
- Android Studio sincronizará automaticamente as dependências

4. **Compilar o Projeto**
```bash
./gradlew build
```

5. **Gerar APK**
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

6. **Instalar no Dispositivo**
```bash
# Via ADB
adb install app/build/outputs/apk/debug/app-debug.apk

# Ou conectar o dispositivo via USB e usar Android Studio
```

## Uso da Aplicação

### Primeira Execução
1. Abrir o aplicativo
2. Conceder todas as permissões solicitadas
3. O aplicativo criará automaticamente 10 saudações padrão

### Gravação de Saudação
1. Selecionar a saudação desejada
2. Clicar em "Gravar"
3. Falar a mensagem (máximo 60 segundos)
4. Clicar em "Parar" para finalizar
5. Clicar em "Salvar"

### Ativação de Saudação
1. Selecionar a saudação desejada
2. Clicar no botão de rádio ou em "Selecionar"
3. A saudação será ativada e usada para próximas chamadas

### Visualizar Histórico
1. Ir para a aba "Histórico de Chamadas"
2. Visualizar todas as chamadas atendidas automaticamente
3. Deletar chamadas individuais ou limpar todo o histórico

## Configurações Avançadas

### Agendamento Automático
O aplicativo pode ativar automaticamente saudações com base em:
- Horário do dia (horário comercial, almoço, etc.)
- Dia da semana (finais de semana)
- Feriados
- Período de férias

### Backup
- Backups automáticos são criados em `/storage/emulated/0/Android/data/com.secretaria.eletronica/files/backups/`
- Máximo de 5 backups mantidos automaticamente
- Formato: ZIP contendo gravações e banco de dados

### Logs
- Logs de funcionamento disponíveis em `/storage/emulated/0/Android/data/com.secretaria.eletronica/files/logs/`
- Máximo de 5MB por arquivo de log
- Útil para debugging e monitoramento

## Troubleshooting

### Permissões Negadas
- Ir para Configurações > Aplicativos > Secretária Eletrônica > Permissões
- Conceder todas as permissões necessárias
- Reiniciar o aplicativo

### Chamadas Não São Atendidas
- Verificar se o aplicativo tem permissão `ANSWER_PHONE_CALLS`
- Verificar se o serviço de monitoramento está ativo
- Verificar se uma saudação está ativa
- Verificar os logs do aplicativo

### Áudio Não Funciona
- Verificar se o microfone está funcionando
- Verificar se o volume está ativado
- Verificar permissão `RECORD_AUDIO`
- Testar a gravação de uma saudação

### Histórico de Chamadas Vazio
- Verificar se o aplicativo recebeu chamadas
- Verificar permissão `READ_CALL_LOG` e `WRITE_CALL_LOG`
- Verificar se o banco de dados foi criado corretamente

## Desenvolvimento Futuro

Possíveis melhorias:
- Integração com WhatsApp Business API
- Suporte a múltiplos idiomas
- Interface de agendamento visual
- Análise de chamadas e relatórios
- Integração com Google Calendar para feriados
- Suporte a chamadas de vídeo
- Notificações em tempo real
- Widget para tela inicial

## Licença

Este projeto é fornecido como está para uso pessoal no dispositivo do usuário.

## Suporte

Para questões ou problemas, consulte os logs do aplicativo em:
`/storage/emulated/0/Android/data/com.secretaria.eletronica/files/logs/`

## Notas Importantes

1. **Compatibilidade**: O aplicativo foi desenvolvido para Android 10+ e testado no Samsung Galaxy S20 FE
2. **Permissões**: Todas as permissões são necessárias para o funcionamento completo
3. **Background**: O serviço continua monitorando chamadas mesmo com o aplicativo fechado
4. **Bateria**: O monitoramento contínuo pode impactar a bateria do dispositivo
5. **Privacidade**: Todos os dados são armazenados localmente no dispositivo
6. **Backup**: Recomenda-se fazer backup regularmente das saudações gravadas

---

**Versão**: 1.0.0  
**Data**: 2026-04-21  
**Desenvolvido em**: Kotlin  
**Compatibilidade**: Android 10+
