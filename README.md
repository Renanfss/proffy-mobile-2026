# Proffy Mobile - Fluxo Esqueci Minha Senha

## Implementação da Validação e Navegação

- **Aluno:** Renan Ferreira da Silva
- **Turma:** SI 6°P

A validação do formulário na `ForgotPasswordActivity` foi implementada utilizando o utilitário compartilhado `ValidationUtils`, monitorando o campo em tempo real via `addTextChangedListener` e na perda de foco via `setOnFocusChangeListener`. O botão de ação altera seus estados (habilitado/desabilitado, cor e texto) dinamicamente com base na validade do e-mail. Ao enviar, a aplicação navega via `Intent` para a `FinallyActivity` passando os textos de confirmação dinâmicos via `putExtra`. Por fim, o botão "Voltar ao login" limpa a pilha de atividades (`FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`) para evitar retornos inconsistentes.
