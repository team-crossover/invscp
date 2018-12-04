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
Ao atualizar os testes é preciso seguir as seguintes regras de atualização: A identificação do caso de teste utiliza a nomenclatura [CTMXX], onde M é a inicial do nome da classe Modelo a qual os testes estão referenciados, onde XX é o número do Caso de Teste, que é único. Ao criar novos Casos de Teste, XX deve ser igual ao número do maior identificador do documento + 1

### 3.Casos de teste

*Testes para classe Bem*
| Identificador | CTBem01 - Inserção de bem com usuário não logado|
|--|--|
| Objetivos | Testar se ao inserir um bem com o usuário não logado, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados|
| Entrada|   Dados requeridos de bem e usuário = null|
|Procedimentos| Ir até a tela de bem patrimonial; Clicar em adicionar novo bem patrimonial; Preencher todos os dados especificados; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|
---
| Identificador | CTBem02 - Inserção de bem já existente|
|--|--|
| Objetivos | Testar se ao inserir um bem já existente com, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema|
| Entrada|   Dados requeridos de bem que sejam os mesmos de um que já existe|
|Procedimentos| Ir até a tela de bem patrimonial; Clicar em adicionar novo bem patrimonial; Preencher todos os dados especificados; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|
---
| Identificador | CTBem03 - Inserção com um usuário sem departamento|
|--|--|
| Objetivos | Testar se ao inserir um bem em que o usuário não tenha um departamento, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema com um usuário sem departamento|
| Entrada|   Dados requeridos de bem;|
|Procedimentos| Logar com um usuário que não possua departamento; Ir até a tela de bem patrimonial; Clicar em adicionar novo bem patrimonial; Preencher todos os dados especificados; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|

---
| Identificador | CTBem04 - Inserção com um usuário que não é chefe de departamento|
|--|--|
| Objetivos | Testar se ao inserir um bem em que o usuário não seja  um chefe de departamento, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema com um usuário que não é chefe de departamento|
| Entrada|   Dados requeridos de bem;|
|Procedimentos| Logar com um usuário que não seja um chefe de departamento; Ir até a tela de bem patrimonial; Clicar em adicionar novo bem patrimonial; Preencher todos os dados especificados; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|

---
| Identificador | CTBem05 - Alterar um bem sem o id do Bem|
|--|--|
| Objetivos | Testar se ao alterar um bem em que o usuário não seja  um chefe de departamento, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema; 9. Já ter um bem patrimonial inserido|
| Entrada|   Dados requeridos para alteração de bem;|
|Procedimentos| Logar com um usuário; Ir até a tela de bem patrimonial; Escolher algum bem patrimonial na lista e clicar com mouse direito e depois clicar em alterar; Preencher todos os dados especificados; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|

---
| Identificador | CTBem06 - Alterar um bem sem o id do Bem|
|--|--|
| Objetivos | Testar se ao alterar um bem em que o usuário não seja  um chefe de departamento, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema; 9. Já ter um bem patrimonial inserido|
| Entrada|   Dados requeridos para alteração de bem;|
|Procedimentos| Logar com um usuário; Ir até a tela de bem patrimonial; Escolher algum bem patrimonial na lista e clicar com mouse direito e depois clicar em alterar; Preencher todos os dados especificados; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|

---
| Identificador | CTBem07 - Alterar um bem inexistente|
|--|--|
| Objetivos | Testar se ao alterar um bem em que não exista, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema;|
| Entrada|   Dados requeridos para alteração de bem;|
|Procedimentos| Logar com um usuário; Ir até a tela de bem patrimonial; Escolher algum bem patrimonial na lista e clicar com mouse direito e depois clicar em alterar; Preencher todos os dados especificados; clicar em confirmar; ter o id do bem igual à um id inexistente;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|

---
| Identificador | CTBem08 - Deletar um bem sem o id do Bem|
|--|--|
| Objetivos | Testar se ao alterar um bem em que o usuário não seja  um chefe de departamento, o sistema barre a operação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema; 9. Já ter um bem patrimonial inserido|
| Entrada|   Não há;|
|Procedimentos| Logar com um usuário; Ir até a tela de bem patrimonial; Escolher algum bem patrimonial na lista e clicar em deletar; clicar em confirmar;
|Resultado Esperado| Uma mensagem de erro na tela;|
|Pós-Condições |Deletar todos os itens da pré condição|

---
| Identificador | CTBem09 - Teste de depreciação válido|
|--|--|
| Objetivos | Testar o cálculo de depreciação|
| Pré-Condições| 1. Ter uma localização na base de dados; 2. Ter um prédio na base de dados; 3. Ter um funcionário na base de dados; 4. Ter um chefe de departamento na base de dados; 5. Ter um departamento na base de dados; 6. Ter uma sala na base de dados; 7. Ter um grupo de material na base de dados; 8. Estar logado no sistema; 9. Já ter um bem patrimonial inserido|
| Entrada| valor do bem = 1000; vida útil = 5 anos; taxa de depreciação = 0.2|
|Resultado Esperado| vetor com 6 posições: 1000; 800; 600; 400; 200; 0,1|
|Pós-Condições |Deletar todos os itens da pré condição|