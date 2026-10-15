#  Buscador Clima

Aplicação web desenvolvida com **Java 21, Spring Boot, HTML, CSS e JavaScript** para consultar o **clima atual** e a **previsão do tempo** de cidades. O backend consome dados meteorológicos da **Open-Meteo**, disponibiliza endpoints REST e também serve o frontend.

###  Acesse o projeto

**[Abrir Buscador Clima](https://buscador-clima-api.onrender.com/index.html)**

> A aplicação está hospedada no Render. No plano gratuito, o primeiro acesso após um período de inatividade pode demorar um pouco.

##  Funcionalidades

- Consulta do clima atual por cidade.
- Exibição de temperatura, sensação térmica, umidade, velocidade do vento e condição climática.
- Previsão meteorológica com temperaturas máxima e mínima.
- Escolha do período de previsão de **1 a 7 dias**.
- Prévia dos próximos **3 dias** na página de clima atual.
- Ícones de acordo com a condição climática.
- Interface responsiva, com navegação entre as páginas.
- Mensagens de carregamento e tratamento de erros nas consultas.

##  Tecnologias utilizadas

**Backend:** Java 21, Spring Boot 4, Spring Web MVC, Maven, Springdoc OpenAPI e Lombok.

**Frontend:** HTML5, CSS3 e JavaScript (Fetch API e manipulação do DOM).

**Infraestrutura:** Docker e Render.

**Dados meteorológicos:** [Open-Meteo](https://open-meteo.com/).

##  Estrutura principal

```text
src/main/
├── java/
│   └── com/ruan/buscador_clima/
│       └── ...                  # Código da API REST
└── resources/
    ├── application.yml
    └── static/
        ├── index.html
        ├── clima.html
        ├── previsao.html
        ├── css/
        │   └── style.css
        └── js/
            ├── clima.js
            ├── previsao.js
            └── icones.js
```

A página inicial e a API são disponibilizadas pelo **mesmo servidor Spring Boot**. Por isso, o JavaScript utiliza caminhos relativos, como `/api/clima`.

##  Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/clima?cidade=João%20Pessoa` | Consulta as condições climáticas atuais de uma cidade. |
| `GET` | `/api/previsao?cidade=João%20Pessoa&dias=3` | Consulta a previsão do tempo para a quantidade de dias informada (1 a 7). |

### Exemplo de resposta — clima atual

```json
{
  "cidade": "João Pessoa",
  "pais": "Brasil",
  "temperaturaCelsius": 28.8,
  "sensacaoTermica": 31.0,
  "umidade": 75,
  "velocidadeVento": 12.0,
  "condicao": "Garoa",
  "consultadoEm": "2026-10-12T09:33:43"
}
```

*Valores ilustrativos para demonstrar o formato da resposta.*

### Exemplo de resposta — previsão

```json
{
  "cidade": "João Pessoa",
  "pais": "Brasil",
  "dias": [
    {
      "data": "2026-10-15",
      "condicao": "Predominantemente limpo",
      "temperaturaMaxima": 30.5,
      "temperaturaMinima": 24.2
    }
  ]
}
```

*Valores ilustrativos para demonstrar o formato da resposta.*

##  Como executar localmente

### Pré-requisitos

- Java 21
- Git
- Docker **(opcional)**

### 1. Clonar o repositório

```bash
git clone https://github.com/ruanxanel/buscador-clima-api.git
cd buscador-clima-api
```

### 2. Executar com Maven Wrapper

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

### 3. Acessar a aplicação

Abra no navegador:

```text
http://localhost:8080/index.html
```

### Alternativa: gerar e executar o JAR

```bash
./mvnw clean package
java -jar target/buscador-clima-0.0.1-SNAPSHOT.jar
```

### Alternativa: executar com Docker

```bash
docker build -t buscador-clima .
docker run --rm -p 8080:8080 buscador-clima
```

> Execute apenas uma instância da aplicação por vez na porta 8080.

##  Deploy

O projeto foi publicado no **Render**, com o frontend e a API REST no mesmo serviço. O Dockerfile utiliza uma construção em duas etapas: a primeira gera o JAR com Maven; a segunda executa o aplicativo com Java 21.

O `application.yml` permite configurar a porta por meio de uma variável de ambiente:

```yaml
server:
  port: ${PORT:8080}
```

**Link de produção:** https://buscador-clima-api.onrender.com/index.html

##  Conhecimentos praticados

- Desenvolvimento de APIs REST com Spring Boot.
- Consumo de APIs externas e tratamento de respostas.
- Integração entre frontend e backend.
- JavaScript assíncrono com `fetch()`, `.then()`, `.catch()` e `.finally()`.
- Manipulação dinâmica do DOM e validação de formulários.
- HTML semântico, CSS Grid, Flexbox e responsividade.
- Empacotamento com Maven e conteinerização com Docker.
- Deploy de uma aplicação Java.

##  Objetivo do projeto

Projeto desenvolvido para colocar em prática conceitos de **desenvolvimento back-end com Java e Spring Boot** e ampliar o aprendizado em **frontend**, construindo uma aplicação completa e acessível pela internet.

##  Autor

Desenvolvido por **Ruan Henrique**.

- [GitHub](https://github.com/ruanxanel)
- [LinkedIn](https://www.linkedin.com/in/ruan-henrique-0534a03ba/)