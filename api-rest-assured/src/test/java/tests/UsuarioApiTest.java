package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.instanceOf;

public class UsuarioApiTest {

    private static RequestSpecification requestSpec;

    @BeforeAll
    public static void setUpConfig() {
        // Boa Prática: Definimos a URL base e headers padrões de forma reutilizável
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void deveListarUsuariosComSucesso() {
        given()
                .spec(requestSpec)
        .when()
                .get("/users")
        .then()
                .log().all()
                .statusCode(200) // Valida que a API respondeu com sucesso
                .body("size()", greaterThan(0)) // Valida que a lista de usuários não está vazia
                .body("[0].name", notNullValue()); // Valida que o nome do primeiro usuário existe
    }

    @Test
    public void deveBuscarUsuarioEspecificoPorId() {
        int usuarioId = 1;

        given()
                .spec(requestSpec)
        .when()
                .get("/users/" + usuarioId)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(usuarioId))
                .body("username", equalTo("Bret")) // Valida o username padrão do ID 1 nesta API
                .body("address.city", notNullValue()); // Valida objeto aninhado no JSON
    }

    @Test
    public void deveCriarNovoUsuarioComSucesso() {
        // Payload limpo enviado na requisição POST
        String novoUsuarioJson = """
                {
                    "name": "Gustavo Moraes",
                    "username": "gustavomoraes",
                    "email": "gustavo@portfolio.com"
                }
                """;

        given()
                .spec(requestSpec)
                .body(novoUsuarioJson)
        .when()
                .post("/users")
        .then()
                .log().all()
                .statusCode(201) // Status 201 Created para novas entidades
                .body("id", notNullValue())
                .body("name", equalTo("Gustavo Moraes"));
    }

    @Test
    public void deveCriarUsuarioComDadosDinamicos() {
        // Gerando dados dinâmicos baseados no timestamp atual do sistema
        long timestamp = System.currentTimeMillis();
        String emailDinamico = "usuario_" + timestamp + "@portfolio.com";
        String nomeDinamico = "Tester " + timestamp;

        String payloadDinamico = """
                {
                    "name": "%s",
                    "username": "user_%d",
                    "email": "%s"
                }
                """.formatted(nomeDinamico, timestamp, emailDinamico);

        given()
                .spec(requestSpec)
                .body(payloadDinamico)
        .when()
                .post("/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo(nomeDinamico))
                .body("email", equalTo(emailDinamico));
    }

    @Test
    public void deveCriarUsuarioEExtrairRespostaComOpcao2() {
        long timestamp = System.currentTimeMillis();
        String emailDinamico = "usuario_" + timestamp + "@portfolio.com";
        String nomeDinamico = "Tester " + timestamp;

        String payloadDinamico = """
                {
                    "name": "%s",
                    "username": "user_%d",
                    "email": "%s"
                }
                """.formatted(nomeDinamico, timestamp, emailDinamico);

        // 1. Disparamos a requisição e salvamos o resultado inteiro na variável 'resposta'
        io.restassured.response.Response resposta = given()
                .spec(requestSpec)
                .body(payloadDinamico)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response(); // Extrai a resposta para manipulação em Java

        // 2. Imprime o JSON formatado de forma bonita no console do IntelliJ
        System.out.println("--- JSON DE RESPOSTA FORMATADO ---");
        resposta.prettyPrint();

        // 3. Extraímos um campo específico do JSON usando o jsonPath()
        int idGerado = resposta.jsonPath().getInt("id");
        String nomeRetornado = resposta.jsonPath().getString("name");

        System.out.println("ID extraído da resposta: " + idGerado);
        System.out.println("Nome extraído da resposta: " + nomeRetornado);

        // 4. Podemos fazer asserções normais do JUnit com os valores extraídos
        org.junit.jupiter.api.Assertions.assertTrue(idGerado > 0, "O ID deveria ser maior que zero");
        org.junit.jupiter.api.Assertions.assertEquals(nomeDinamico, nomeRetornado);
    }

    @Test
    public void deveRetornar404AoBuscarUsuarioInexistente() {
        int idInexistente = 99999; // ID que sabidamente não existe na base de dados

        given()
                .spec(requestSpec)
        .when()
                .get("/users/" + idInexistente)
        .then()
                .log().all()
                .statusCode(404) // Valida que o servidor informa que o recurso não foi encontrado
                .body("$", anEmptyMap()); // Valida que a API retorna um objeto JSON vazio e seguro
    }

    @Test
    public void deveRetornarFalhaAoBuscarUsuarioInexistente() {
        int idExistente = 1; // ID que sabidamente existe na base de dados

        given()
                .spec(requestSpec)
        .when()
                .get("/users/" + idExistente)
        .then()
                .log().all()
                .statusCode(404) // Valida que o servidor informa que o recurso foi encontrado,
                                                  // portante, deve resultar em falha no resultado
                .body("$", anEmptyMap()); // Valida que a API retorna um objeto JSON com dados do usuário
    }

    @Test
    public void deveValidarContratoDosTiposDeDadosDoUsuario() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/1")
                .then()
                .log().all()
                .statusCode(200)
                // Validamos se o contrato garante os tipos corretos de cada campo
                .body("id", instanceOf(Integer.class))
                .body("name", instanceOf(String.class))
                .body("email", instanceOf(String.class))
                .body("address.geo.lat", instanceOf(String.class));
    }
}
