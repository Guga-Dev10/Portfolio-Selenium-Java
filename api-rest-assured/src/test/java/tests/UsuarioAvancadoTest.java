package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioAvancadoTest {

    private static RequestSpecification requestSpec;

    @BeforeAll
    public static void setUpConfig() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void deveCriarUsuarioUsandoSerializacaoComRecord() {
        User novoUsuario = new User(null, "Gustavo Senior QA", "gugadev", "guga@portfolio.com");

        User usuarioCriado = given()
                .spec(requestSpec)
                .body(novoUsuario)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .time(lessThan(2000L))
                .extract()
                .as(User.class);

        assertNotNull(usuarioCriado.id());
        assertEquals("Gustavo Senior QA", usuarioCriado.name());
        assertEquals("guga@portfolio.com", usuarioCriado.email());

        System.out.println("Usuário desserializado com sucesso! ID gerado: " + usuarioCriado.id());
    }

    @ParameterizedTest
    @CsvSource({
            "1, Bret",
            "2, Antonette",
            "3, Samantha"
    })
    // CORREÇÃO AQUI: Adicionado 'String' antes do expectedUsername
    public void deveTestarMultiplosUsuariosParametrizados(int id, String expectedUsername) {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(200)
                .body("username", equalTo(expectedUsername));
    }
}