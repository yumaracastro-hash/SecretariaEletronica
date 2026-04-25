# Troubleshooting e FAQ - Secretária Eletrônica

## Problemas Comuns

### 1. Aplicativo Não Atende Chamadas Automaticamente

**Sintomas**:
- Chamadas recebidas não são atendidas
- Saudação não é reproduzida

**Soluções**:

1. **Verificar Permissões**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica > Permissões
   - Ativar: `Atender chamadas telefônicas`
   - Ativar: `Ler estado do telefone`

2. **Verificar Serviço**:
   - Abrir o aplicativo
   - Verificar se o serviço está rodando
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica > Permissões > Permissões Avançadas
   - Ativar: `Permissão de aplicativo especial`

3. **Verificar Saudação Ativa**:
   - Abrir o aplicativo
   - Ir para a aba "Saudações"
   - Verificar se uma saudação está marcada como ativa (com rádio selecionado)
   - Se não houver, selecionar uma

4. **Reiniciar o Dispositivo**:
   - Desligar e ligar novamente o dispositivo
   - Abrir o aplicativo novamente

5. **Verificar Logs**:
   - Abrir o aplicativo
   - Ir para: Configurações > Logs
   - Procurar por mensagens de erro

### 2. Erro de Permissão ao Gravar Saudação

**Sintomas**:
- Mensagem: "Permissão de áudio necessária"
- Botão de gravação não funciona

**Soluções**:

1. **Conceder Permissão de Microfone**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica > Permissões
   - Ativar: `Microfone`

2. **Verificar Microfone do Dispositivo**:
   - Testar o microfone com outro aplicativo (ex: Gravador de Voz)
   - Se não funcionar, o microfone pode estar com defeito

3. **Limpar Cache do Aplicativo**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica
   - Clicar em "Armazenamento"
   - Clicar em "Limpar Cache"

### 3. Histórico de Chamadas Vazio

**Sintomas**:
- Nenhuma chamada aparece no histórico
- Mesmo após receber chamadas

**Soluções**:

1. **Verificar Permissões**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica > Permissões
   - Ativar: `Ler histórico de chamadas`
   - Ativar: `Escrever no histórico de chamadas`

2. **Verificar se Chamadas Foram Recebidas**:
   - Pedir a alguém para ligar
   - Verificar se o aplicativo está aberto ou em background

3. **Reconstruir Banco de Dados**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica
   - Clicar em "Armazenamento"
   - Clicar em "Limpar dados"
   - Abrir o aplicativo novamente

### 4. Áudio Não Funciona

**Sintomas**:
- Saudação não é reproduzida
- Gravação não funciona
- Som muito baixo ou muito alto

**Soluções**:

1. **Verificar Volume**:
   - Pressionar os botões de volume no lado do dispositivo
   - Aumentar o volume para o máximo

2. **Verificar Modo de Som**:
   - Verificar se o dispositivo está em modo silencioso
   - Deslizar o interruptor de silencioso para ativar som

3. **Verificar Saída de Áudio**:
   - Ir para: Configurações > Som e vibração
   - Verificar se o áudio está saindo pelo alto-falante correto
   - Testar com outro aplicativo

4. **Reiniciar Serviço de Áudio**:
   - Abrir o aplicativo
   - Sair completamente
   - Desligar e ligar o dispositivo
   - Abrir o aplicativo novamente

### 5. Aplicativo Consome Muita Bateria

**Sintomas**:
- Bateria descarrega rapidamente
- Dispositivo fica quente

**Soluções**:

1. **Desativar Monitoramento Contínuo**:
   - Ir para: Configurações > Secretária Eletrônica
   - Desativar: "Monitoramento Contínuo"
   - Ativar apenas durante horários comerciais

2. **Otimizar Agendamento**:
   - Ir para: Configurações > Agendamento
   - Desativar agendamento automático se não for usar

3. **Limpar Histórico**:
   - Ir para: Histórico de Chamadas
   - Clicar em "Limpar Histórico"
   - Isso reduz o tamanho do banco de dados

4. **Desativar Logs**:
   - Ir para: Configurações > Logs
   - Desativar: "Registrar Logs Detalhados"

### 6. Aplicativo Trava ou Congela

**Sintomas**:
- Interface não responde
- Botões não funcionam
- Aplicativo fecha inesperadamente

**Soluções**:

1. **Forçar Parada**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica
   - Clicar em "Forçar Parada"
   - Abrir o aplicativo novamente

2. **Limpar Dados**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica
   - Clicar em "Armazenamento"
   - Clicar em "Limpar dados"
   - Abrir o aplicativo novamente

3. **Atualizar Android**:
   - Ir para: Configurações > Sobre o telefone
   - Clicar em "Atualizar sistema"
   - Instalar atualizações disponíveis

4. **Desinstalar e Reinstalar**:
   - Ir para: Configurações > Aplicativos > Secretária Eletrônica
   - Clicar em "Desinstalar"
   - Reinstalar o aplicativo

### 7. Saudação Não é Reproduzida Completamente

**Sintomas**:
- Saudação é cortada no meio
- Chamada é encerrada antes da saudação terminar

**Soluções**:

1. **Verificar Duração da Gravação**:
   - Ir para: Saudações
   - Verificar a duração mostrada para cada saudação
   - Se estiver vazia, a saudação não foi gravada

2. **Verificar Configurações de Tempo**:
   - Ir para: Configurações > Tempo de Atendimento
   - Aumentar o tempo de espera antes de encerrar a chamada

3. **Regravar Saudação**:
   - Ir para: Saudações
   - Clicar em "Gravar"
   - Falar a mensagem novamente
   - Clicar em "Salvar"

## Perguntas Frequentes (FAQ)

### P: O aplicativo funciona com WhatsApp?
**R**: Sim, o aplicativo pode monitorar chamadas de voz do WhatsApp se o número for o mesmo do celular. No entanto, a integração com a API do WhatsApp requer configurações adicionais.

### P: Posso usar o aplicativo com dois SIMs?
**R**: Sim, o aplicativo detecta de qual SIM a chamada está sendo recebida e registra essa informação no histórico.

### P: As saudações são armazenadas na nuvem?
**R**: Não, todas as saudações são armazenadas localmente no dispositivo. Você pode fazer backup manualmente.

### P: Posso editar uma saudação já gravada?
**R**: Não diretamente. Você precisa deletar a saudação antiga e gravar uma nova.

### P: O que acontece se eu desinstalar o aplicativo?
**R**: Todos os dados, incluindo saudações e histórico de chamadas, serão deletados. Recomenda-se fazer backup antes.

### P: Posso usar o aplicativo sem internet?
**R**: Sim, o aplicativo funciona completamente offline. Você não precisa de conexão com a internet.

### P: Como faço backup das saudações?
**R**: Ir para: Configurações > Backup > Criar Backup. O arquivo será salvo em `/storage/emulated/0/Android/data/com.secretaria.eletronica/files/backups/`

### P: Posso compartilhar saudações com outro dispositivo?
**R**: Sim, você pode fazer backup no dispositivo 1 e restaurar no dispositivo 2 usando os arquivos de backup.

### P: O aplicativo funciona com chamadas de vídeo?
**R**: Não, o aplicativo foi desenvolvido apenas para chamadas de voz.

### P: Posso usar o aplicativo enquanto estou em uma chamada?
**R**: Não, o aplicativo não pode atender automaticamente se você já está em uma chamada.

### P: Como desativar o aplicativo temporariamente?
**R**: Ir para: Configurações > Aplicativos > Secretária Eletrônica > Desativar. Ou simplesmente não selecionar nenhuma saudação como ativa.

### P: O aplicativo registra as conversas?
**R**: Não, o aplicativo apenas reproduz a saudação e encerra a chamada. Nenhuma conversa é registrada.

### P: Posso usar números internacionais?
**R**: Sim, o aplicativo suporta números internacionais com código de país (ex: +55 31 99983-0246).

### P: Como limpar o histórico de chamadas?
**R**: Ir para: Histórico de Chamadas > Clicar em "Limpar Histórico". Isso deletará todas as chamadas registradas.

### P: O aplicativo funciona em segundo plano?
**R**: Sim, o aplicativo continua monitorando chamadas mesmo com o aplicativo fechado.

### P: Como verificar os logs do aplicativo?
**R**: Ir para: Configurações > Logs. Os logs também são salvos em `/storage/emulated/0/Android/data/com.secretaria.eletronica/files/logs/`

## Contato e Suporte

Se o problema persistir após tentar as soluções acima:

1. **Verificar Logs**:
   - Abrir o aplicativo
   - Ir para: Configurações > Logs
   - Procurar por mensagens de erro

2. **Coletar Informações**:
   - Versão do Android
   - Modelo do dispositivo
   - Versão do aplicativo
   - Descrição detalhada do problema

3. **Contactar Suporte**:
   - Enviar os logs e informações para o desenvolvedor

## Dicas de Otimização

1. **Agendar Saudações**: Use o agendamento automático para ativar diferentes saudações em diferentes horários.

2. **Backup Regular**: Faça backup das saudações regularmente para não perder dados.

3. **Limpar Histórico**: Limpe o histórico de chamadas periodicamente para manter o banco de dados otimizado.

4. **Atualizar Android**: Mantenha o Android atualizado para melhor compatibilidade e segurança.

5. **Testar Regularmente**: Teste o aplicativo periodicamente para garantir que está funcionando corretamente.

---

**Última atualização**: 2026-04-21
