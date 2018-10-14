# InvSCP (Protótipo)

Protótipo para testar a arquitetura do projeto InvSCP da disciplina "Integração 1" de Engenharia de Software, 2018/2

## Ambiente

Para que o projeto funcione, é necessário que esteja sendo executado o PostgreSQL 10.5 no localhost.

Deve existir o usuário com nome "invscpAdmin", senha "12345" e autorização para efetuar login.
Além disso, deve existir o banco de dados com nome "inventory" e o owner deve ser o usuário "invscpAdmin".

O script [banco.sql](banco.sql) contém os comandos SQL para a criação do scheme do banco de dados.
