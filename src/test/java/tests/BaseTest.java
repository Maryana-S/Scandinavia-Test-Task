package tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    @Before
    @Step("Предустановка")
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

}
