package api;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ApiTests extends BaseApiTest {

    @Test()
    public void people_list_fields() {
        Response resp = GET("/people/");
        resp.then()
                .spec(okJson)
                .body(matchesJsonSchemaInClasspath("schemas/people-list.schema.json"))
                .body("count", greaterThan(0))
                .body("next", anyOf(matchesPattern("^https://swapi\\.dev/api/people/\\?page=\\d+$"), nullValue()))
                .body("previous", anyOf(matchesPattern("^https://swapi\\.dev/api/people/\\?page=\\d+$"), nullValue()))
                .body("results.size()", greaterThan(0));
    }

    @Test()
    public void person_not_found_404() {
        Response resp = GET("/people/55555/");
        resp.then()
                .spec(notFoundJson)
                .body("detail", equalTo("Not found"));
    }

    @DataProvider(name = "personIds")
    public Object[][] personIds() {
        return new Object[][]{
                {1, "Luke Skywalker"},
                {2, "C-3PO"},
                {3, "R2-D2"},
                {4, "Darth Vader"},
                {5, "Leia Organa"}
        };
    }

    @Test(dataProvider = "personIds")
    public void person_schema_and_core_fields(int personId, String expectedName) {
        Response resp = GET("/people/{id}/", personId);
        resp.then()
                .spec(okJson)
                .body(matchesJsonSchemaInClasspath("schemas/person.schema.json"))
                .body("name", equalTo(expectedName))
                .body("$", allOf(
                        hasKey("height"), hasKey("mass"),
                        hasKey("hair_color"), hasKey("skin_color"), hasKey("eye_color"),
                        hasKey("birth_year"), hasKey("gender"),
                        hasKey("homeworld"), hasKey("films"), hasKey("species"),
                        hasKey("vehicles"), hasKey("starships"),
                        hasKey("created"), hasKey("edited"), hasKey("url")
                ))
                .body("url", matchesPattern("^https://swapi\\.dev/api/people/" + personId + "/?$"))
                .body("homeworld", notNullValue())
                .body("films", notNullValue())
                .body("species", notNullValue())
                .body("vehicles", notNullValue())
                .body("starships", notNullValue());
    }
}
