# InvSCP

Projeto InvSCP do Grupo 3 da disciplina "Integração 1" de Engenharia de Software, 2018/2

## Grupo

Este repositório pertence ao Grupo 3 (G3), composto pelos seguintes membros:

- João Pedro Arruda Vieira
- Larissa Chyevena Lopes de Mello
- Natália Lopes da Silva
- Nelson William Viana de Siqueira
- Sofia Martins Moraes

## Artefatos

Os artefatos gerados até então para o projeto são:

- [Design funcional](https://docs.google.com/document/d/1nb-8-6QRH8XUJ27V-cY2wrb1Bcw_Hqp_Xk9P6RiFZc0/edit?usp=sharing) (Documento com diagrama de classes de domínio, diagrama e especificação dos casos de uso e diagramas de sequência)
- [Dicionário de dados](https://docs.google.com/document/d/1jGyPurQ9goRa3sDFgHiyTznuFmWxTABtDzueL3RH-28/edit?usp=sharing) (Documento com diagrama conceitual de dados e especificação das entidades e dos relacionamentos)
- [Arquitetura do software](https://docs.google.com/document/d/1AXWEDpkgeEBsR76ApY3BCSF9lIS47gJlQP6BaQa2Fs4/edit?usp=sharing) (Documento com diagrama de componentes especificação dos componentes, comunicações e tecnologias da arquitetura)
- [Checklist do cliente](https://drive.google.com/open?id=1dBKv4idoGU25bl7y_wZlXkM9RMt4Qu-XvdGGHjHWJho) (Checklist mapeando os principais requisitos do cliente aos casos de uso e classes reponsáveis por atendê-los)

## Planejamento

As seguintes atividades estão planejadas para a implementação do projeto:

**Sprint 1**

| Data | Responsável(eis) | Descrição | Status |
|---|---|---|---|
| 30/10 | João Pedro | ~~Criar o script do banco de dados e os Models para Localização, Prédio, Sala, Departamento e Usuário~~ | Concluído |
| 30/10 | Larissa e Sofia | ~~Criar as Views para login e para manter e executar casos de uso relacionados aos Models acima~~ | Concluído |
| 06/11 | Nelson | Criar os Presenters para as Views acima e ~~implementar o controle de acesso e execução do script do banco~~ | Em andamento |
| 06/11 | Natália | Documentar casos de testes (total de pelo menos 5) | Em andamento |

**Sprint 2**

| Data | Responsável(eis) | Descrição | Status |
|---|---|---|---|
| 13/11 | João Pedro | Criar o script do banco de dados e os Models para Bem, Ordem de Serviço e Baixa | Pendente |
| 13/11 | Larissa e Sofia | Criar as Views para manter e executar casos de uso relacionados aos Models acima | Pendente |
| 20/11 | Nelson | Criar os Presenters para as Views acima | Pendente |
| 20/11 | Natália | Documentar mais casos de testes e implementar todos com JUnit (total de pelo menos 8) | Pendente |

**Sprint 3**

| Data | Responsável(eis) | Descrição | Status |
|---|---|---|---|
| 27/11 | João Pedro | Criar o script do banco de dados e os Models para Movimentação, Evento de movimentação (aceites, cancelamentos etc.) e Relatórios | Pendente |
| 27/11 | Larissa e Sofia | Criar as Views para manter e executar casos de uso relacionados aos Models acima | Pendente |
| 04/12 | Nelson | Criar os Presenters para as Views acima | Pendente |
| 04/12 | Natália | Documentar mais casos de testes e implementar todos com JUnit (total de pelo menos 10) | Pendente |

## Ambiente

O ambiente de desenvolvimento deve possuir o [JDK (Java SE Development Kit)](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) versão 8 ou superior, enquanto o ambiente de execução deve possuir o [JRE (Java SE Runtime Environment)](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) versão 8 ou superior.

### Banco de dados

O ambiente de execução deve estar executando o banco de dados PostgreSQL 10.5 no localhost.

Deve existir o usuário com nome "invscpAdmin" e senha "12345" (com autorização para efetuar login).
Além disso, deve existir um banco de dados nomeado "inventory" e cujo owner é o usuário "invscpAdmin".

Ao ser executado, o InvSCP detectará se o banco de dados "inventory" foi inicializado (teve todas as tabelas criadas). Caso contrário, o script "[createDatabase](src/main/resources/sql/createDatabase.sql)" será executado automaticamente para inicializá-lo.
