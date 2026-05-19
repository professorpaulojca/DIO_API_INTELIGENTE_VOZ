<h1 align="center">
  🎙️ DIO API Inteligente com Voz
</h1>

<p align="center">
  <strong>Gerenciamento financeiro potencializado por Inteligência Artificial e reconhecimento de fala</strong><br/>
  Construído com Spring Boot 3 · Spring AI 1.0 · OpenAI Whisper · GPT-4o-mini
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_AI-1.0.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/OpenAI-Whisper%20%7C%20GPT--4o--mini-412991?style=for-the-badge&logo=openai&logoColor=white"/>
  <img src="https://img.shields.io/badge/H2-Database-blue?style=for-the-badge&logo=h2&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
</p>

---

## 📖 Sobre o Projeto

Este projeto foi desenvolvido como parte do bootcamp da **[DIO – Digital Innovation One](https://www.dio.me/)** e demonstra como integrar **Spring AI** com os modelos da OpenAI para criar uma API REST inteligente capaz de:

- 🎤 **Transcrever áudio em texto** usando o modelo **Whisper-1** da OpenAI
- 🤖 **Conversar com IA** usando o modelo **GPT-4o-mini**
- 💰 **Gerenciar transações financeiras** (receitas e despesas) de forma simples e eficiente
- 🏷️ **Categorizar e consultar** lançamentos financeiros por categoria

A arquitetura segue os princípios da **Arquitetura Hexagonal (Ports & Adapters)**, garantindo um código desacoplado, testável e fácil de evoluir.

---

## 🏗️ Arquitetura

```
src/
└── main/java/io/budgeting/
    ├── domain/                         # Núcleo do negócio (entidades e tipos)
    │   ├── Transaction.java            # Record imutável de transação
    │   └── TransactionType.java        # Enum: INCOME / EXPENSE
    │
    ├── application/
    │   ├── port/                       # Interfaces (contratos) — Portas
    │   │   ├── AudioTranscriptionGateway.java
    │   │   └── TransactionRepository.java
    │   └── usecase/                    # Casos de uso — Regras de negócio
    │       ├── ListTransactionsByCategoryUseCase.java
    │       ├── PersistTransactionUseCase.java
    │       └── TranscribeAudioUseCase.java
    │
    └── infrastructure/                 # Adaptadores externos
        ├── ai/openai/                  # Adaptador: Spring AI + Whisper
        │   └── SpringAiAudioTranscriptionGateway.java
        ├── http/                       # Adaptador: Controllers REST
        │   ├── ChatModelController.java
        │   ├── TransactionController.java
        │   ├── GlobalExceptionHandler.java
        │   ├── request/TransactionRequest.java
        │   └── response/TransactionResponse.java
        └── persistence/               # Adaptador: JPA + H2
            ├── TransactionJpaEntity.java
            ├── TransactionJpaRepository.java
            └── TransactionPersistenceAdapter.java
```

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.3.5 | Framework base |
| Spring AI | 1.0.0 | Integração com modelos de IA |
| OpenAI Whisper-1 | — | Transcrição de áudio para texto |
| OpenAI GPT-4o-mini | — | Chat com inteligência artificial |
| Spring Data JPA | — | Persistência de dados |
| H2 Database | — | Banco em memória para desenvolvimento |
| Bean Validation | — | Validação de requisições |
| Maven | — | Gerenciamento de dependências |

---

## ⚙️ Pré-requisitos

- **Java 21** ou superior
- **Maven 3.9+**
- Uma **chave de API da OpenAI** — [Obtenha aqui](https://platform.openai.com/api-keys)

---

## 🔧 Configuração e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/professorpaulojca/DIO_API_INTELIGENTE_VOZ.git
cd DIO_API_INTELIGENTE_VOZ
```

### 2. Configure a chave da OpenAI

Defina a variável de ambiente antes de iniciar a aplicação:

**Linux / macOS:**
```bash
export OPENAI_API_KEY=sk-proj-sua-chave-aqui
```

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="sk-proj-sua-chave-aqui"
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`  
Console H2 disponível em: `http://localhost:8080/h2-console`

---

## 📡 Endpoints da API

### 🤖 Chat com IA

#### `GET /api/chat-model`
Envia um prompt para o GPT-4o-mini e recebe uma resposta.

```bash
curl "http://localhost:8080/api/chat-model?prompt=O+que+é+Spring+AI?"
```

**Resposta:**
```
Spring AI é um framework do ecossistema Spring que simplifica a integração com modelos de IA...
```

---

### 💰 Transações Financeiras

#### `POST /transactions`
Cria uma nova transação financeira manualmente.

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Salário mensal",
    "amount": 5000.00,
    "category": "Renda",
    "type": "INCOME"
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "description": "Salário mensal",
  "amount": 5000.00,
  "category": "Renda",
  "type": "INCOME",
  "createdAt": "2026-05-19T10:30:00"
}
```

---

#### `GET /transactions/{category}`
Lista todas as transações de uma categoria específica.

```bash
curl http://localhost:8080/transactions/Renda
```

**Resposta:**
```json
[
  {
    "id": 1,
    "description": "Salário mensal",
    "amount": 5000.00,
    "category": "Renda",
    "type": "INCOME",
    "createdAt": "2026-05-19T10:30:00"
  }
]
```

---

#### `POST /transactions/ai`
🎙️ Envia um arquivo de áudio, transcreve com o **Whisper-1** e retorna o texto transcrito.

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@/caminho/para/audio.mp3"
```

**Resposta:**
```
Gastei cinquenta reais no mercado hoje na categoria alimentação.
```

> **Formatos suportados:** mp3, mp4, mpeg, mpga, m4a, wav, webm  
> **Tamanho máximo:** 10 MB  
> **Idioma configurado:** Português (pt)

---

## 🧩 Como Funciona o Fluxo de Voz

```
┌─────────────┐     Arquivo de     ┌──────────────────────┐
│   Cliente   │ ──── áudio (.mp3) ──▶  POST /transactions/ai │
└─────────────┘                    └──────────┬───────────┘
                                              │
                                   TranscribeAudioUseCase
                                              │
                                   AudioTranscriptionGateway
                                              │
                                   ┌──────────▼───────────┐
                                   │  OpenAI Whisper-1    │
                                   │  (Spring AI)         │
                                   └──────────┬───────────┘
                                              │
                                   ┌──────────▼───────────┐
                                   │   Texto transcrito   │
                                   │  "Gastei R$50 no..." │
                                   └──────────────────────┘
```

---

## 🗄️ Banco de Dados (H2 Console)

Para visualizar os dados em tempo real, acesse:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:budgeting
Usuário:  sa
Senha:    (deixe em branco)
```

---

## 📁 Estrutura de Arquivos de Configuração

```properties
# application.properties (principais configurações)

spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4o-mini
spring.ai.openai.audio.transcription.options.model=whisper-1
spring.ai.openai.audio.transcription.options.language=pt

spring.datasource.url=jdbc:h2:mem:budgeting
spring.h2.console.enabled=true
```

---

## 🧪 Executando os Testes

```bash
./mvnw test
```

---

## 📚 Aprendizados do Projeto

- ✅ Integração do **Spring AI** com a API da OpenAI
- ✅ Uso do modelo **Whisper-1** para transcrição de voz
- ✅ Implementação de **Arquitetura Hexagonal** em Java 21
- ✅ Upload de arquivos multipart com Spring MVC
- ✅ Records Java como entidades de domínio imutáveis
- ✅ Tratamento global de exceções com `@ControllerAdvice`
- ✅ Configuração de Spring AI via `application.properties`

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma _issue_ ou enviar um _pull request_.

1. Faça um **fork** do projeto
2. Crie uma branch: `git checkout -b feature/minha-feature`
3. Commit suas mudanças: `git commit -m 'feat: adiciona minha feature'`
4. Push para a branch: `git push origin feature/minha-feature`
5. Abra um **Pull Request**

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<p align="center">
  Feito com ❤️ durante o bootcamp da <a href="https://www.dio.me/"><strong>DIO – Digital Innovation One</strong></a>
</p>

<p align="center">
  <a href="https://github.com/professorpaulojca">
    <img src="https://img.shields.io/badge/GitHub-professorpaulojca-181717?style=for-the-badge&logo=github"/>
  </a>
</p>
