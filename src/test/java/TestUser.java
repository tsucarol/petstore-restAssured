import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import com.google.gson.Gson;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUser {
    static String ct = "application/json";
    static String uriUser = "https://petstore.swagger.io/v2/user";
    static String token;
    /* TESTES USER
     * Post
     * Get
     * Put
     * Delete
     */

    @ParameterizedTest @Order(1)
    @CsvFileSource(resources = "/csv/userMassa.csv", numLinesToSkip = 1, delimiter = ',')
    public void testPostUserDDT(
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
}
