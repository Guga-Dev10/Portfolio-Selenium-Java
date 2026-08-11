package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
}
