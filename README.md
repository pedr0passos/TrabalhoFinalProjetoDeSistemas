<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>README - Trabalho Final</title>
</head>
<body>

  <h1>Trabalho Final - Acesso de Usuários ao Sistema</h1>

  <p>Este é o repositório para o trabalho final da disciplina de Projeto de Sistemas. O projeto é uma aplicação Java que gerencia o acesso de usuários ao sistema, incluindo cadastro, autenticação, envio de notificações, alteração de senhas e gerenciamento de logs.</p>

  <h2>Autores</h2>
  <ul>
    <li><strong>Pedro Henrique Passos Rocha</strong></li>
    <li><strong>Catterina Vittorazzi Salvador</strong></li>
    <li><strong>João Victor Mascarenhas</strong></li>
  </ul>

  <h2>Sumário</h2>
  <ul>
    <li><a href="#descrição">Descrição</a></li>
    <li><a href="#funcionalidades">Funcionalidades</a></li>
    <li><a href="#tecnologias-utilizadas">Tecnologias Utilizadas</a></li>
    <li><a href="#padrões-de-projeto">Padrões de Projeto</a></li>
    <li><a href="#como-executar">Como Executar</a></li>
    <li><a href="#estrutura-do-projeto">Estrutura do Projeto</a></li>
  </ul>

  <h2 id="descrição">Descrição</h2>
  <p>O sistema gerencia usuários em um ambiente seguro, permitindo operações como cadastro, login, alteração de senhas, envio de notificações e listagem de dados. Além disso, ele suporta logs em formato CSV e JSON, permitindo auditoria de operações realizadas no sistema.</p>

  <h2 id="funcionalidades">Funcionalidades</h2>
  <ul>
    <li>Cadastro e autenticação de usuários.</li>
    <li>Autorização de usuários não cadastrados.</li>
    <li>Envio e visualização de notificações.</li>
    <li>Listagem de usuários e notificações.</li>
    <li>Alteração de senha.</li>
    <li>Geração de logs em formatos CSV e JSON.</li>
  </ul>

  <h2 id="tecnologias-utilizadas">Tecnologias Utilizadas</h2>
  <ul>
    <li><strong>Java 8+</strong></li>
    <li><strong>Java Swing</strong> para interface gráfica.</li>
    <li><strong>SQLite</strong> para persistência de dados.</li>
    <li><strong>JFreeChart</strong> para geração de gráficos.</li>
    <li><strong>Padrões de Projeto</strong> como DAO, Command, Observer, Adapter, Singleton, State, e MVP.</li>
  </ul>

  <h2 id="padrões-de-projeto">Padrões de Projeto</h2>
  <ul>
    <li><strong>DAO</strong>: Utilizado para a persistência de dados.</li>
    <li><strong>Observer</strong>: Aplicado para o sistema de notificações.</li>
    <li><strong>Command</strong>: Usado para encapsular ações de edição e visualização.</li>
    <li><strong>Adapter</strong>: Implementado para adaptar a saída de log entre os formatos CSV e JSON.</li>
    <li><strong>State</strong>: Gerencia diferentes estados como edição e visualização.</li>
    <li><strong>Singleton</strong>: Garante que certos componentes tenham apenas uma instância, como a conexão com o banco de dados.</li>
    <li><strong>MVP (Model-View-Presenter)</strong>: Usado para estruturar a aplicação de forma modular, separando a interface gráfica da lógica de negócios.</li>
  </ul>

  <h2 id="como-executar">Como Executar</h2>
  <ol>
    <li>Clone este repositório:</li>
    <pre><code>git clone https://github.com/pedr0passos/TrabalhoFinalProjetoDeSistemas.git</code></pre>
    <li>Importe o projeto em sua IDE Java (como Eclipse ou IntelliJ).</li>
    <li>Certifique-se de que todas as dependências estão instaladas (Java, SQLite, JFreeChart).</li>
    <li>Execute a classe principal localizada em <code>main/Main.java</code>.</li>
  </ol>

  <h2 id="estrutura-do-projeto">Estrutura do Projeto</h2>
  <pre><code>src/
│
├── command/             # Classes que encapsulam comandos do sistema
├── dao/                 # Data Access Object (DAO) para persistência de dados
├── logs/                # Arquivos de logs (CSV e JSON)
├── model/               # Modelos de dados (usuários, notificações, etc.)
├── observer/            # Implementação do padrão Observer para notificações
├── presenter/           # Lógica da camada de apresentação (MVP)
├── service/             # Serviços do sistema (validação, gerenciamento, etc.)
├── singleton/           # Padrão Singleton para instâncias únicas
├── state/               # Padrão State para estados da aplicação
└── view/                # Interface gráfica com Java Swing
  </code></pre>

  <h2>Licença</h2>
  <p>Este projeto é desenvolvido para fins acadêmicos como parte da disciplina de Projeto de Sistemas e não possui uma licença específica.</p>

</body>
</html>
