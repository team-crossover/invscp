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

As atividades planejadas para o projeto estão dispostas no diagrama Gantt abaixo:

![Diagrama](https://user-images.githubusercontent.com/6721656/48994200-17cc0580-f129-11e8-8e41-6d40c845d4db.png)

Legenda:

- ![Amarelo](https://via.placeholder.com/10x10/FFFF00/FFFF00)  João Pedro
- ![Laranja](https://via.placeholder.com/10x10/FF9900/FF9900)  Larissa
- ![Rosa](https://via.placeholder.com/10x10/FF00FF/FF00FF)  Natália
- ![Azul](https://via.placeholder.com/10x10/00CCFF/00CCFF)   Nelson
- ![Verde](https://via.placeholder.com/10x10/00FF00/00FF00)  Sofia
- ![Vermelho](https://via.placeholder.com/10x10/FF0000/FF0000)  Todos

## Ambientes

O ambiente de desenvolvimento deve possuir o [JDK (Java SE Development Kit)](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) versão 8 ou superior, enquanto o de execução deve possuir o [JRE (Java SE Runtime Environment)](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) versão 8 ou superior.

### Servidor

O SGBD PostgreSQL 10.5 deve estar executando na mesma máquina. Por padrão, o banco de dados deve possuir o nome "inventory" e pertencer ao usuário de nome "invscpAdmin" e senha "123445", com autorização para efetuar login.

No mesmo diretório do programa será criado um arquivo de configurações chamado ```server.properties``` no qual é possível especificar configurações como porta do servidor, host/porta/nome/usuário/senha/versão do banco de dados e preferências de logs.

### Cliente

Por padrão, o primeiro usuário a ser criado é o Chefe de Patrimônio, e possui o login "admin" e a senha "admin".

No mesmo diretório do programa será criado um arquivo de configurações chamado ```client.properties``` no qual é possível especificar configurações como host/porta do servidor e qual view utilizar (atualmente apenas 'SWING' é suportado).

## Build

Para gerar um novo arquivo .JAR executável do servidor ou do cliente, basta executar o comando do Maven ```mvn package -P executavel-unico``` no projeto apropriado.
