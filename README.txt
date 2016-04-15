Desafio LabSEC Java
===================

O objetivo é construir uma página de cadastro e login. Existem apenas dois
campos, "user" e "password", e dois botões, "login" e register". O resultado
da operação é mostrado em uma mensagem na mesma tela.


1. Ferramentas
--------------

Sugerimos fortemente usar Eclipse, especialmente "Eclipse IDE for Java EE
Developers"[1], que possui funções web embutidas.

O código também precisa de um servidor de aplicação, como Glassfish, Tomcat,
JBoss ou similar. Sugerimos Tomcat 7 [2] ou superior. Você precisa declarar
o servidor de aplicação nas configurações do seu Eclipse.

O projeto usa Maven para builds, mas já está configurado.

Para operações criptogŕáficas sugerimos fortemente o Bouncy Castle (já
declarado como dependência no Maven). Existem alternativas, mas esta é a
ferramenta usada no projeto.

Para começar, importe o projeto no Eclipse escolhendo o tipo "Existing Maven
Project", usando Run As -> Run On Server para testar.

[1] http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/mars2  
[2] http://tomcat.apache.org/download-70.cgi


2. Objetivos principais
-----------------------

A funcionalidade básica já está implementada. O usuário digita nome e senha,
"cadastra-se" em um HashMap em memória, e depois pode fazer login com esses
dados.

Você precisa:

[X] Adicionar persistência aos usuários cadastrados. Pode ser um arquivo de
texto com um usuário por linha, uma base SQLite, um serviço Postgres completo,
ou o que você preferir.

[X] Armazenar a senha de forma segura. Senhas em texto claro são um risco caso
a base de dados seja comprometida. Você precisa armazenar essas senhas de forma
segura. Tente encontrar a forma mais correta de fazer isso (dica: os problemas
são sutis e existem vários recursos sobre isso online).


3. Objetivos secundários
------------------------

[X] Use um sistema de controle de versão, como Git (preferível), Mercurial ou
SVN. Não esqueça de enviar o histórico (.git/.svn).

[ ] Adicione testes (já existe um esqueleto se quiser), tanto dos casos felizes
quando das exceções.

[ ] Comentários em partes complicadas.

[ ] Documentação nos métodos e classes públicos.

[ ] Organização do código, práticas de clean code.


4. Envio
--------

Envie o projeto completo para marinacoelho16@gmail.com até o dia 2016-04-27,
juntamente com um relatório curto (não precisa ter mais de uma página)
contendo:

[ ] Breve descrição do que foi implementado.

[ ] Escolhas feitas e suas justificativas (base de dados, algoritmos, etc).

[ ] Problemas encontrados e como foram superados.
