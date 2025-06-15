import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUser {
    static String ct = "application/json";
    static String uriUser = "https://petstore.swagger.io/v2/user";
    static String token;

    String username = "dummie";
    String password = "Dummie123!";

    /* Função para leitura do json */
    public static String lerArquivoJson(String arquivoJson) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }


    @ParameterizedTest @Order(1)
    @CsvFileSource(resources = "/csv/userMassa.csv", numLinesToSkip = 1, delimiter = ',')
    public void testCreateUserDDT(
        int userId, 
        String userName,
        String userFistName,
        String userLastName,
        String userEmail,
        String userPassword,
        String userPhone,
        int userStatus
    )
    {
        User user = new User();
        Gson gson = new Gson();

        user.id = userId;
        user.username = userName;
        user.firstName = userFistName;
        user.lastName = userLastName;
        user.email = userEmail;
        user.password = userPassword;
        user.phone = userPhone;
        user.userStatus = userStatus;

        String jsonBody = gson.toJson(user);

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(uriUser)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(userId)))
        ;
    }

    @Test @Order(2)
    public void testLoginUser() {
        String resultadoEsperado = "logged in user session:";

        Response resposta = (Response) 
        given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uriUser + "/login?username=" + username + "&password=" + password)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", containsString(resultadoEsperado))
            .body("message", hasLength(36))
        .extract()
        ;

        token = resposta.jsonPath().getString("message").substring(23);
        System.out.println("Conteudo do Token: " + token);
    }

    @Test @Order(3)
    public void testUpdateUser() throws IOException {
        int userId = 3;
        String jsonBody = lerArquivoJson("src/test/resources/json/updateUser3.json");

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .put(uriUser + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(userId)))
        ;
    }

    @Test @Order(4)
    public void testDeleteUser() throws IOException {
        given()
            .contentType(ct)
            .log().all()
        .when()
            .delete(uriUser + "/" + username)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(username))
        ;
    }
    
}
