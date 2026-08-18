# Portfólio de Automação de Testes - Selenium & Java

Repositório dedicado à construção e consolidação de conhecimentos práticos em Quality Assurance, com foco em automação de testes web e verificação de sistemas.

## 🎯 Objetivo
Demonstrar a aplicação prática de frameworks de automação utilizando padrões de projeto estruturais e boas práticas de Engenharia de Software, criando uma base sólida para integrações futuras com outras ferramentas e ecossistemas de testes.

## 🏗️ Arquitetura (Monorepo)
Este repositório adota a arquitetura de Monorepo, estruturado em múltiplos módulos para isolar diferentes contextos de estudo e garantir a integridade das dependências:

- **`selenium-puro/`**: Módulo dedicado a testes utilizando o ecossistema nativo do Selenium WebDriver com JUnit, focado em interações diretas e padrões como *Page Objects*.
- **`selenium-cucumber/`**: Módulo dedicado à implementação de testes guiados por comportamento (BDD) utilizando Cucumber, aproximando a área técnica da área de negócios através da escrita de cenários em Gherkin.

## 🛠️ Stack Tecnológica
- **Linguagem:** Java
- **Core de Automação:** Selenium WebDriver
- **Framework BDD:** Cucumber
- **Orquestração de Testes:** JUnit
- **Gerenciamento e Build:** Maven

# 🚀 Portfólio de Automação de Testes e Engenharia de Qualidade (QA)

Bem-vindo ao meu repositório de portfólio em **Java e Automação de Testes**. Este projeto é estruturado em formato **Monorepo**, contemplando soluções completas para validação de APIs, interfaces web, testes unitários, testes parametrizados e relatórios gerenciais avançados.

---

## 🛠️ Tecnologias e Ecossistema Utilizados

* **Linguagem:** Java (JDK 21+)
* **Gerenciamento de Dependências e Build:** Apache Maven (Arquitetura Multi-module / Pai-Filho)
* **Testes de API:** REST Assured, Jackson (Deserialização de Objetos/Records)
* **Testes de Interface / Web:** Selenium WebDriver, Cucumber (BDD)
* **Testes Unitários e Mocking:** JUnit 5, Mockito
* **Relatórios e Métricas:** Allure Report (Allure JUnit5 / Maven Plugin)
* **Controle de Qualidade de Código & Padrões:** Arquitetura Limpa, Testes Data-Driven e Validação de SLAs de Resposta.

---

## 📁 Estrutura do Repositório (Monorepo)

```text
Portfolio-Selenium-Java/
├── api-rest-assured/       # Automação de APIs REST, contratos e SLAs
├── mobile-appium/          # Automação mobile (Appium)
├── selenium-cucumber/      # Testes end-to-end com BDD e Cucumber
├── selenium-puro/          # Automação web tradicional (Page Object Model)
└── unit-tests-mockito/     # Testes unitários isolados com Mockito

*Desenvolvido e mantido por Gustavo Pereira de Moraes Souza.*