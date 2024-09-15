# Trabalho Final - Projeto de Sistemas de Software
Este é o repositório para o trabalho final da disciplina de Projeto de Sistemas. O projeto é uma aplicação Java que gerencia o acesso de usuários ao sistema, incluindo cadastro, autenticação, envio de notificações, alteração de senhas e gerenciamento de logs.

Autores
Pedro Henrique Passos Rocha
Catterina Vittorazzi Salvador
João Victor Mascarenhas
Sumário
Descrição
Funcionalidades
Tecnologias Utilizadas
Padrões de Projeto
Como Executar
Estrutura do Projeto
Descrição
O sistema gerencia usuários em um ambiente seguro, permitindo operações como cadastro, login, alteração de senhas, envio de notificações e listagem de dados. Além disso, ele suporta logs em formato CSV e JSON, permitindo auditoria de operações realizadas no sistema.

Funcionalidades
Cadastro e autenticação de usuários.
Autorização de usuários não cadastrados.
Envio e visualização de notificações.
Listagem de usuários e notificações.
Alteração de senha.
Geração de logs em formatos CSV e JSON.
Tecnologias Utilizadas
Java 8+
Java Swing para interface gráfica.
SQLite para persistência de dados.
JFreeChart para geração de gráficos.
Padrões de Projeto como DAO, Command, Observer, Adapter, Singleton, State, e MVP.
Padrões de Projeto
DAO: Utilizado para a persistência de dados.
Observer: Aplicado para o sistema de notificações.
Command: Usado para encapsular ações de edição e visualização.
Adapter: Implementado para adaptar a saída de log entre os formatos CSV e JSON.
State: Gerencia diferentes estados como edição e visualização.
Singleton: Garante que certos componentes tenham apenas uma instância, como a conexão com o banco de dados.
MVP (Model-View-Presenter): Usado para estruturar a aplicação de forma modular, separando a interface gráfica da lógica de negócios.
