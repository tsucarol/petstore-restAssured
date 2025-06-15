import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStore {
    static String ct = "application/json";
    static String uriStoreOrder ="https://petstore.swagger.io/v2/store/order";

    @ParameterizedTest @Order(1)
    @CsvFileSource(resources = "/csv/storeMassa.csv", numLinesToSkip = 1, delimiter = ',')
    public void testCreateOrderDDT(
        int orderId,
        int petId,
        int orderQuantity,
        String orderShipDate,
        String orderStatus,
        boolean orderComplete
    ) 
    {
        Store store = new Store();
        Gson gson = new Gson();

        store.id = orderId;
        store.petId = petId;
        store.quantity = orderQuantity;
        store.shipDate = orderShipDate;
        store.status = orderStatus;
        store.complete = orderComplete;

        String jsonBody = gson.toJson(store);

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(uriStoreOrder)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(orderId))
            .body("petId", is(petId))
            .body("quantity", is(orderQuantity))
            .body("shipDate", containsString(orderShipDate))
            .body("status", is(orderStatus))
            .body("complete", is(orderComplete))
        ;
    }

    @ParameterizedTest @Order(2)
    @CsvFileSource(resources = "/csv/storeMassa.csv", numLinesToSkip = 1, delimiter = ',')
    public void testFindOrderDDT(
        int orderId,
        int petId,
        int orderQuantity,
        String orderShipDate,
        String orderStatus,
        boolean orderComplete
    ) 
    {
        Store store = new Store();
        Gson gson = new Gson();

        store.id = orderId;
        store.petId = petId;
        store.quantity = orderQuantity;
        store.shipDate = orderShipDate;
        store.status = orderStatus;
        store.complete = orderComplete;

        String jsonBody = gson.toJson(store);

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .get(uriStoreOrder + "/" + orderId)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(orderId))
            .body("petId", is(petId))
            .body("quantity", is(orderQuantity))
            .body("shipDate", containsString(orderShipDate))
            .body("status", is(orderStatus))
            .body("complete", is(orderComplete))
        ;
    }

    @Test @Order(3)
    public void testDeleteOrder(){
        int orderId = 001;

        given()
            .contentType(ct)
            .log().all()
        .when()
            .delete(uriStoreOrder + "/" + orderId)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(orderId)))
        ;
    }

}
