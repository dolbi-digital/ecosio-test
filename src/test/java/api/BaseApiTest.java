package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import utils.Config;

import static io.restassured.RestAssured.given;

public class BaseApiTest {

    protected RequestSpecification req;
    protected ResponseSpecification okJson;
    protected ResponseSpecification notFoundJson;

    @BeforeClass(alwaysRun = true)
    public void apiSetup() {
        RestAssured.baseURI = Config.get("baseApiUrl");

        req = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();

        okJson = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        notFoundJson = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();
    }

    protected Response GET(String path, Object... uriParams) {
        return given().spec(req).when().get(path, uriParams);
    }
}
