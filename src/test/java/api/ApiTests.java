package api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests extends BaseApiTest {

    @Test()
    public void people_list_should_not_be_empty() {
        given()
                .when()
                .get("/people/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("count", greaterThan(0))
                .body("results.size()", greaterThan(0))
                .body("results[0].name", not(isEmptyOrNullString()))
                .body("results[0].height", not(isEmptyOrNullString()))
                .body("results[0]", hasKey("films"));
    }

    @Test(description = "GET /people/1/ returns Luke Skywalker with films")
    public void person_one_should_be_luke() {
        given()
                .when()
                .get("/people/1/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Luke Skywalker"))
                .body("films", not(empty()))
                .body("mass", not(isEmptyOrNullString()))
                .body("birth_year", matchesPattern("\\d{2}BBY|\\d{2}ABY|unknown"));
    }
}

