# 🎬 Movie App TMDB

Um aplicativo Android nativo desenvolvido em Kotlin que consome a API do The Movie Database (TMDB) para listar filmes, ver seus detalhes e permitir que o usuário favorite os que mais gosta.

---

## ✨ Features

* **Listagem de Filmes:** Exibe uma lista de filmes.
* **Detalhes do Filme:** Permite ao usuário clicar em um filme para ver mais detalhes, como sinopse e avaliação.
* **Sistema de Favoritos:** O usuário pode marcar e desmarcar filmes como favoritos.
* **Persistência Local:** Os filmes favoritados são salvos no dispositivo e ficam disponíveis mesmo offline.

---

## 🛠️ Tecnologias e Arquitetura

Este projeto foi desenvolvido utilizando as seguintes tecnologias e padrões:

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **Arquitetura:** Clean Architecture (MVVM na camada de apresentação)
* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) para uma UI declarativa e moderna.
* **Injeção de Dependência:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) para gerenciar as dependências de forma robusta.
* **Assincronismo:** [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) para gerenciar tarefas em segundo plano.
* **Networking:** [Retrofit](https://square.github.io/retrofit/) para fazer as chamadas à API do TMDB.
* **JSON Parsing:** [Gson](https://github.com/google/gson) para converter a resposta da API em objetos Kotlin.
* **Banco de Dados Local:** [Room](https://developer.android.com/jetpack/androidx/releases/room) para persistir os filmes favoritos.
* **Navegação:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) para navegar entre as telas.

---

## ⚙️ Como Rodar o Projeto

Para rodar este projeto, você precisará de uma chave de API do TMDB.

1.  Clone este repositório: `git clone https://github.com/seu-usuario/tmdb-movie-app-android.git`
2.  Obtenha uma chave de API gratuita no site do [The Movie Database (TMDB)](https://www.themoviedb.org/signup).
3.  No Android Studio, na raiz do projeto, crie um arquivo chamado `local.properties`.
4.  Dentro de `local.properties`, adicione sua chave de API da seguinte forma:
    ```properties
    tmdb_api_key="SUA_CHAVE_DE_API_AQUI"
    ```
5.  Sincronize e compile o projeto.

> **Nota:** Para que a chave no `local.properties` funcione, o arquivo `app/build.gradle.kts` deve ser configurado para lê-la e o `buildConfig` deve estar ativado. Garanta que seu arquivo tenha uma estrutura parecida com esta:`:
> ```kotlin
> android {
>     buildFeatures {
>         compose = true
>         buildConfig = true // Garanta que esta linha exista
>     }
>
>     defaultConfig {
>         // ... outras configurações
>
>         // Lê e expõe a chave da API
>         val properties = java.util.Properties()
>         val localPropertiesFile = project.rootProject.file("local.properties")
>         if (localPropertiesFile.exists()) {
>             properties.load(localPropertiesFile.inputStream())
>         }
>         buildConfigField("String", "TMDB_API_KEY", "\"${properties.getProperty("tmdb_api_key")}\"")
>     }
>     // ...
> }
> ```

---

## 🙏 Agradecimentos

* A API utilizada neste projeto é fornecida pelo [The Movie Database (TMDB)](https://www.themoviedb.org/).