# Plano de Testes

### 1. Introdução
*1.1. Propósito*
Este documento contém informações necessárias para verificação dos requisitos e regras de negócio do sistema InvSCP. Aqui estão descritos os casos de teste que deverão ser implementados no sistema, com a ferramenta JUnit.

*1.2. Público Alvo*
Este documento está destinado aos envolvidos com a criação, execução e manutenção dos testes, e também aos envolvidos com a implementação direta do sistema.

*1.3. Escopo*
Neste documento está detalhado o projeto para os Casos de Teste do software InvSCP, ao nível de sistema, que poderão ser executados e validados de maneira manual ou automática, através do plugin de cobertura do Maven que é uma ferramenta que verifica o quanto o código está sendo testado. Ao final da execução dos testes unitários, o Cobertura gera um relatório contendo todas as classes do projeto e a porcentagem das linhas de código que foram testadas.
Assim é possível identificar as inconsistências do programa e também verificar todos os requisitos e regras de negócio que foram atendidos e se todas as regras e requisitos implementados foram devidamente testadas.

### 2. Atualização dos Testes
Ao atualizar os testes é preciso seguir as seguintes regras de atualização: A identificação do caso de teste utiliza a nomenclatura [CTMXX], onde M é o nome da classe Modelo a qual os testes estão referenciados, onde XX é o número do Caso de Teste, que é único. Ao criar novos Casos de Teste, XX deve ser igual ao número do maior identificador do documento + 1

### 3. Casos de teste

| Identificador | CTMov01 - Movimentação interna|
|--|--|
| Objetivos | Testar se uma movimentação de bens entre salas do mesmo patrimônio é considerada ‘interna’ |
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter 2 sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Ter um bem material na base de dados|
| Entrada|   O id da sala de origem e o id da sala de destino|
|Procedimentos| Logar no sistema; Ir para a tela de Bens; Escolher um bem; Clicar com mouse direito e escolher a opção mover; Escolher um sala de destino dentro do mesmo patrimônio. Clicar em salvar.|
|Resultado esperado| Uma mensagem de que agora o bem está em movimentação
|Pós-Condições |Deletar todos os itens da pré condição|

| Identificador | CTBem02 - Novo bem na sala de depósito |
|--|--|
| Objetivos | Testar se ao inserir um bem sem informar sua localização, ele irá automaticamente para a sala do depósito que está no departamento de patrimônio|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema|
| Entrada|   Dados requeridos de bem na inserção de um bem e escolher departamento de patrimônio no campo departamento|
|Procedimentos| Ir até a tela de bem patrimonial; Clicar em adicionar novo bem patrimonial; Preencher todos os dados especificados; E no campo departamento selecionar a opção que já vem carregada 'Departamento de patrimõnio'; clicar em confirmar;
|Resultado Esperado| O irá para sala de depósito|
|Pós-Condições |Deletar todos os itens da pré condição|

| Identificador | CTBem03 - Deletar um bem antigo (com mais de um mês)|
|--|--|
| Objetivos | Ao deletar um bem antigo, o qual sua data de cadastro seja superior a um mês, ele não pode ser excluído, ele deve ser baixado|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados;|
| Entrada|   Não há|
|Procedimentos| Logar no sistema; Ir clicar na opção Bens; Escolher um bem patrimonial da lista; Selecionar a opção deletar selecionado(s); Confirmar a operação
|Resultado Esperado| O bem será baixado|
|Pós-Condições |Deletar todos os itens da pré condição|

| Identificador | CTBem04 - Deletar um bem recente (com menos de um mês)|
|--|--|
| Objetivos | Ao deletar um bem recente, o qual sua data de cadastro seja inferior a um mês, ele pode ser excluído|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados;|
| Entrada|   Não há|
|Procedimentos| Logar no sistema; Ir clicar na opção Bens; Escolher um bem patrimonial da lista; Selecionar a opção deletar selecionado(s); Confirmar a operação
|Resultado Esperado| O bem será excluído|
|Pós-Condições |Deletar todos os itens da pré condição|

| Identificador | CTDep05 - Chefe principal obrigatório|
|--|--|
| Objetivos | Testar se o chefe de departamento está obrigatótio|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados;|
| Entrada | Nenhum valor para o chefe e nem para chefe substituto|
|Procedimentos| Logar no sistema; Selecionar a opção departamentos; Clicar em adicionar novo departamento; Informar o nome mas não informar chefe ou substituto
|Resultado Esperado| Mensagem de campo obrigatório para chefe e nenhuma mensagem para o chefe substituto|
|Pós-Condições |Deletar todos os itens da pré condição|

| Identificador | CTDep06 - Chefe substituto opcional|
|--|--|
| Objetivos | Testar se o chefe substituto está opcional.|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados;|
| Entrada | nome do departamento, chefe e nenhum chefe subtituto|
|Procedimentos| Logar no sistema; Selecionar a opção departamentos; Clicar em adicionar novo departamento; Informar o nome, informar chefe, mas não informar o chefe substituto
|Resultado Esperado| Departamento inserido com sucesso|
|Pós-Condições |Deletar todos os itens da pré condição|

| Identificador | CTDep07 - Usuários mantidos apenas por chefes de departamento e chefes de patrimônio|
|--|--|
| Objetivos | Testar se os usuários são mantidos apenas pelos chefes de departamento e chefes de patrimônio|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados;|
| Entrada 1| Dados requeridos para inserção de usuário|
|Resultado Esperado| Mensagem de campo obrigatório para chefe e nenhuma mensagem para o chefe substituto|
|Pós-Condições |Deletar todos os itens da pré condição|
