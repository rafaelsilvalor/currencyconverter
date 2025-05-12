## About

### English

🚀 **My Journey with Oracle Next Education (ONE)**
I’m proud to be part of the 100% online and free Oracle Next Education (ONE) program, a partnership between Alura and Oracle. This course has empowered me to solidify and expand my programming knowledge—complementing my Systems Analysis and Development studies—by tackling hands-on challenges that mirror real-world scenarios. Even when revisiting familiar topics, ONE continually deepens my understanding and prepares me for the tech job market.

💻 **This Project**
This repository houses my Java-based Currency Converter CLI, built as a capstone challenge for ONE. Leveraging the Exchange Rate API and optimized libraries such as Gson and Reflections, the application presents an interactive terminal menu that lets you:

* Convert between any two currencies in a single command or via an intuitive prompt
* Save and paginate favorite currency pairs
* View a history of past conversions
* Switch languages at runtime using internationalized `.properties` bundles

Each feature follows SOLID design principles and TDD best practices, offering a robust, extensible foundation for future enhancements—whether that’s a graphical overlay UI, web-API integration.

### Português (Brasil)

🚀 **Minha Jornada com a Oracle Next Education (ONE)**
Tenho orgulho de fazer parte do programa 100% online e gratuito Oracle Next Education (ONE), uma parceria entre a Alura e a Oracle. Este curso me capacitou a consolidar e expandir meu conhecimento em programação—complementando meus estudos em Análise e Desenvolvimento de Sistemas—por meio de desafios práticos que refletem cenários do mundo real. Mesmo ao revisitar tópicos familiares, o ONE aprofunda continuamente meu entendimento e me prepara para o mercado de tecnologia.

💻 **Sobre Este Projeto**
Este repositório contém meu CLI de Conversor de Moedas em Java, desenvolvido como desafio final para o ONE. Utilizando a Exchange Rate API e bibliotecas otimizadas como Gson e Reflections, a aplicação apresenta um menu interativo no terminal que permite:

* Converter entre quaisquer duas moedas em um único comando ou por meio de um prompt intuitivo
* Salvar e paginar pares de moedas favoritos
* Visualizar o histórico de conversões realizadas
* Alternar idiomas em tempo de execução usando bundles `.properties` internacionalizados

Cada funcionalidade segue os princípios de design SOLID e as melhores práticas de TDD, oferecendo uma base robusta e extensível para aprimoramentos futuros—seja uma interface gráfica, integração via API web.



## Table of Contents

* [About](#about)
* [Installation](#installation)
* [Configuration](#configuration)
* [Usage](#usage)
* [Testing & Quality](#testing--quality)
* [Continuous Integration](#continuous-integration) 
* [License](#license)

## Installation

### Prerequisites

* **Java 11** or higher installed and on your `PATH`
* **Maven 3.6+** installed
* **Exchange Rate API key** (required for all conversions)

   * Obtain a free API key from: [https://www.exchangerate-api.com](https://www.exchangerate-api.com)

### Clone the repository

```bash
git clone https://github.com/rafaelsilvalor/currencyconverter
cd currencyconverter
```

### Build & Package

Use Maven to compile the code, run tests, and assemble the fat-jar:

```bash
mvn clean package
```

After a successful build, the executable JAR will be located at:

```
target/currencyconverter-1.0-SNAPSHOT.jar
```
Alternatively, you can download the pre-built currencyconverter.jar into your Downloads folder and run it directly without building from source.

### Run the CLI

```bash
java -jar target/currencyconverter-1.0-SNAPSHOT.jar
```
or
```bash
java -jar target/currencyconverter-1.0-SNAPSHOT.jar [command] [args]
```

*For example:*

```bash
java -jar target/currencyconverter-1.0-SNAPSHOT.jar --online USD EUR 100
```

## Configuration

The application loads settings from two levels of properties files, plus locale-specific bundles for i18n (you must configure your `API_KEY` in one of these):

1. **Default properties (classpath)**

    * `src/main/resources/config.properties`
    * `src/main/resources/paths.properties`

2. **External overrides** (in working directory)

    * `settings/config.properties`
    * `settings/paths.properties`
      Override API base URL, history/favorites file paths, and verbose logging.

3. **Internationalization bundles**

    * `src/main/resources/messages_en-US.properties`
    * `src/main/resources/messages_pt-BR.properties`
      Not supported yet.
      Switch languages at runtime with `--lang <locale>` (e.g. `--lang pt-BR`).

### Example override

```properties
# settings/config.properties
API_KEY=<Exchange Rate API>
config.verbose=true
```

```properties
# settings/paths.properties
history.file=./data/history.log
favorites.file=./data/favorites.properties
```

## Usage

### 1. One-Off Conversion

```bash
java -jar target/currencyconverter-1.0-SNAPSHOT.jar --oneline <FROM> <TO> <AMOUNT>
```

*Example:*

```bash
java -jar target/currencyconverter-1.0-SNAPSHOT.jar --oneline USD EUR 100
# → “100.00 USD = 92.50 EUR”
```

### 2. Interactive Mode

```bash
java -jar target/currencyconverter-1.0-SNAPSHOT.jar --interactive
```

All runtime flags are listed below; the interactive menu is self-explanatory and not detailed here.

### 3. Available Commands

| Command              | Description                                                                     |
| -------------------- | ------------------------------------------------------------------------------- |
| `--oneline F T A`    | Convert amount `A` from currency code `F` to `T`.                               |
| `--interactive`      | Enter interactive mode to perform multiple conversions within a single session. |
| `--list`             | List supported currency codes.                                                  |
| `--set-apikey <KEY>` | Set or update your Exchange Rate API key.                                       |
| `--add-fav F T`\*    | Add the pair (`F` → `T`) to your favorites list.                                |
| `--list-fav`\*       | Show your saved favorite pairs, paginated 5 per page.                           |
| `--rm-fav`\*         | Remove one of your saved favorites.                                             |
| `--list-log`\*       | Display a paginated list of past conversions (newest first).                    |
| `--lang <locale>`\*  | Switch UI language at runtime (e.g. `--lang pt-BR` or `--lang en-US`).          |
| `--help` or `-?`\*   | Show this help summary.                                                         |
| `exit` or `quit`\*   | Terminate the application.                                                      |

- (*) Ainda não suportado.

### 5. Verbose Logging

Enable `config.verbose=true` in `settings/config.properties` to see HTTP request details, parsing steps, and error stack traces.

### 6. Using the `cvc` Wrapper (Optional)

After installing the `cvc` script into your `PATH`:

```bash
cvc --oneline EUR USD 20
cvc --interactive
```

## Testing & Quality

We follow a Test-Driven Development (TDD) approach and enforce quality via code coverage and CI checks.

```bash
# Run unit & integration tests
mvn clean test

# Generate coverage report
mvn jacoco:report
```

Open `target/site/jacoco/index.html` to review line and branch coverage.

Our GitHub Actions workflow (`.github/workflows/ci.yml`) runs on every push/PR:

1. Compile & Build
2. Run Tests
3. Enforce Coverage Threshold
4. (Optional) Static Analysis

## Continuous Integration

The CI workflow at `.github/workflows/ci.yml` includes:

```yaml
name: CI

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package --batch-mode
      - name: Run tests
        run: mvn test --batch-mode
      - name: Generate coverage
        run: mvn jacoco:report --batch-mode
      - name: Upload to Codecov
        uses: codecov/codecov-action@v3
```

Failures in any step will block merges, preserving code quality and consistency.


## License

This project is licensed under the MIT License.
