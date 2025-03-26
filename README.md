# BensTech - Inventário Seguro

## 📌 **Objetivo do Projeto**
O **BensTech** é um sistema de gestão de controle de bens desenvolvido para empresas que necessitam gerenciar seu patrimônio com segurança e eficiência. O sistema permite:

- ✅ **Cadastro e rastreamento de bens** (nome, tipo, preço, data de compra, vida útil, valor residual).  
- 📊 **Cálculo automático de depreciação** (método linear).  
- 🔍 **CRUD completo** (Create, Read, Update, Delete) para bens e categorias.  
- 📈 **Relatórios inteligentes** e gráficos para tomada de decisão.  
- 🔒 **Inventário seguro** com auditoria e histórico de alterações.  

---

## 🛠 **Tecnologias Utilizadas**
- **Backend**:  
  - `Java 21` (LTS com recursos modernos como Records e Pattern Matching).  
  - `Jakarta EE 10` (GlassFish 7.0.11 como servidor de aplicação).  

- **Frontend**:  
  - `PrimeFaces 14` (UI components para interfaces ricas e responsivas).  
  - `JavaScript` (Chart.js para visualização de dados).  

- **Banco de Dados**:  
  - `PostgreSQL` (SGBD robusto para armazenamento seguro dos dados).  

- **Ferramentas Adicionais**:  
  - `JPA (Hibernate)` para ORM.  
  - `Maven` para gerenciamento de dependências.  

---

## 🚀 **Como Executar o Projeto**
1. **Pré-requisitos**:  
   - Java 21 instalado.  
   - PostgreSQL configurado com um banco chamado `bens_tech`.  
   - GlassFish 7.0.11 em execução.  

2. **Configuração**:  
   ```bash
   git clone https://github.com/seu-usuario/BensTech.git
   cd BensTech
   ```
   
  - Edite src/main/resources/META-INF/persistence.xml com as credenciais do seu PostgreSQL.

3. **Deploy no GlassFish**:
    ```bash
    mvn clean package
    asadmin deploy target/BensTech.war
    ```
4. **Acesse**:
    Abra `http://localhost:8080/BensTech` no navegador.
   
---


## 📋 Funcionalidades Principais
|Funcionalidade	|Descrição|
|-----------------|----------|
|Cadastro de Bens|	Inclui todos os atributos necessários para gestão patrimonial.|
|Cálculo de Depreciação	| Fórmula: `(Preço de Compra - Valor Residual) / Vida Útil.`|
|Relatórios	| Exportação em PDF/Excel e gráficos interativos.|
|Auditoria	| Log de alterações para rastreabilidade.|

---

## 🤝 Contribuições

Contribuições são bem-vindas! Abra uma issue ou envie um pull request.

### Desenvolvido por: Gabriel Remigio de Sá
### 📧 Contato: gabrielrdes@outlook.com

---

### ✨ **Destaques do README**:
- **Foco no propósito**: Explicação clara do problema que o sistema resolve (gestão patrimonial segura).  
- **Tecnologias destacadas**: Java 21, GlassFish, PrimeFaces e PostgreSQL em evidência.  
- **Instruções rápidas**: Passos essenciais para executar o projeto.  
- **Tabela de funcionalidades**: Organização visual das features.  
- **Toques modernos**: Emojis e formatação Markdown para melhor legibilidade.  
