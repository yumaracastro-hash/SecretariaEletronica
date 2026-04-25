# Guia de Início Rápido - Secretária Eletrônica

## Instalação Rápida (5 minutos)

### Pré-requisitos
- Android Studio 2023.1 ou superior instalado
- JDK 17 ou superior
- SDK Android 34 instalado
- Dispositivo Android 10+ ou emulador

### Passos

1. **Extrair o Projeto**
```bash
tar -xzf SecretariaEletronica.tar.gz
cd SecretariaEletronica
```

2. **Abrir no Android Studio**
- Abrir Android Studio
- File > Open > Selecionar a pasta `SecretariaEletronica`
- Aguardar sincronização do Gradle (2-5 minutos)

3. **Conectar Dispositivo**
- Conectar Samsung Galaxy S20 FE via USB
- Ativar "Depuração USB" em: Configurações > Opções do Desenvolvedor
- Ou usar um emulador Android

4. **Compilar e Instalar**
```bash
# Via Android Studio: Run > Run 'app'
# Ou via terminal:
./gradlew installDebug
```

5. **Primeira Execução**
- Abrir o aplicativo "Secretária Eletrônica"
- Conceder todas as permissões solicitadas
- O aplicativo criará automaticamente 10 saudações padrão

## Primeiros Passos (10 minutos)

### 1. Gravar uma Saudação

1. Abrir o aplicativo
2. Ir para a aba "Saudações"
3. Selecionar "Personalizado 1"
4. Clicar em "Gravar"
5. Falar a mensagem (ex: "Olá, você ligou para o número de João")
6. Clicar em "Parar"
7. Clicar em "Salvar"

### 2. Ativar a Saudação

1. Na lista de saudações, clicar no botão de rádio ao lado de "Personalizado 1"
2. Ou clicar em "Selecionar"
3. A saudação agora está ativa e será usada para próximas chamadas

### 3. Testar o Atendimento

1. Pedir a alguém para ligar para seu número
2. O aplicativo atenderá automaticamente
3. Reproduzirá a saudação gravada
4. Encerará a chamada automaticamente

### 4. Visualizar Histórico

1. Ir para a aba "Histórico de Chamadas"
2. Você verá todas as chamadas atendidas automaticamente
3. Informações incluem: número, data, hora e SIM utilizado

## Configurações Importantes

### Ativar Permissões Completas

Se o aplicativo não atender chamadas automaticamente:

1. Ir para: Configurações > Aplicativos > Secretária Eletrônica
2. Clicar em "Permissões"
3. Ativar:
   - Telefone
   - Microfone
   - Armazenamento
4. Ir para: Configurações > Aplicativos > Secretária Eletrônica > Permissões Avançadas
5. Ativar: "Permissão de aplicativo especial"

### Ativar Serviço em Background

1. Ir para: Configurações > Bateria > Otimização de Bateria
2. Selecionar "Secretária Eletrônica"
3. Clicar em "Não otimizar"

## Funcionalidades Principais

### Saudações Personalizáveis

O aplicativo vem com 10 slots de saudações:

| Slot | Categoria | Uso |
|------|-----------|-----|
| 1 | Horário de Almoço | Mensagem durante almoço |
| 2 | Fora do Horário Comercial | Mensagem após expediente |
| 3 | Finais de Semana | Mensagem nos fins de semana |
| 4 | Feriados | Mensagem em feriados |
| 5 | Férias | Mensagem durante férias |
| 6-10 | Personalizadas | Suas próprias mensagens |

### Agendamento Automático

O aplicativo pode ativar automaticamente diferentes saudações:

- **Horário de Almoço**: 12:00 - 13:00
- **Fora do Horário Comercial**: Após 18:00 ou Antes de 09:00
- **Finais de Semana**: Sábados e Domingos
- **Feriados**: Datas específicas (requer configuração)
- **Férias**: Períodos específicos (requer configuração)

### Backup Automático

O aplicativo cria backups automaticamente:

- Localização: `/storage/emulated/0/Android/data/com.secretaria.eletronica/files/backups/`
- Formato: ZIP compactado
- Máximo de 5 backups mantidos
- Inclui: Saudações gravadas + Banco de dados

## Troubleshooting Rápido

### Problema: Aplicativo não atende chamadas

**Solução**:
1. Verificar se uma saudação está ativa (com rádio selecionado)
2. Verificar se a permissão "Atender chamadas" está ativada
3. Reiniciar o dispositivo
4. Abrir o aplicativo novamente

### Problema: Não consegue gravar saudação

**Solução**:
1. Verificar se a permissão "Microfone" está ativada
2. Testar o microfone com outro aplicativo
3. Limpar cache: Configurações > Aplicativos > Secretária Eletrônica > Armazenamento > Limpar Cache

### Problema: Histórico vazio

**Solução**:
1. Verificar se chamadas foram recebidas
2. Verificar se o aplicativo tem permissão "Ler histórico de chamadas"
3. Pedir a alguém para ligar novamente

### Problema: Áudio muito baixo

**Solução**:
1. Aumentar o volume do dispositivo
2. Verificar se não está em modo silencioso
3. Ir para: Configurações > Som > Volume de Chamadas > Aumentar

## Próximos Passos

1. **Explorar Configurações**: Ir para Configurações para ajustar horários e períodos
2. **Testar Agendamento**: Configurar diferentes saudações para diferentes horários
3. **Fazer Backup**: Criar backup das saudações importantes
4. **Ler Documentação**: Consultar README.md para mais detalhes

## Contatos Suportados

O aplicativo suporta atendimento automático para:

- **Celular (Vivo)**: +55 31 99983-0246
- **WhatsApp (voz)**: +55 31 99983-0246
- **Telefone fixo (Claro)**: +55 31 3241-2123

Você pode adicionar mais números nas configurações.

## Dicas Úteis

1. **Teste Regularmente**: Teste o aplicativo periodicamente para garantir funcionamento
2. **Backup Semanal**: Faça backup das saudações semanalmente
3. **Atualize Android**: Mantenha o Android atualizado para melhor compatibilidade
4. **Limpe Histórico**: Limpe o histórico mensalmente para otimizar performance
5. **Monitore Bateria**: O monitoramento contínuo pode impactar bateria

## Suporte

- **Logs**: Configurações > Logs para visualizar histórico de operações
- **FAQ**: Consultar TROUBLESHOOTING.md para perguntas frequentes
- **Documentação**: Consultar README.md para documentação completa

---

**Versão**: 1.0.0  
**Última Atualização**: 2026-04-21  
**Tempo de Leitura**: ~10 minutos
