package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.Config;

public class BaseApiTest {

    @BeforeClass(alwaysRun = true)
    public void apiSetup() {
        RestAssured.baseURI = Config.get("baseApiUrl");
        RestAssured.filters(new AllureRestAssured());
    }
}

