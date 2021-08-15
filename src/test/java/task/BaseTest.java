package task;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public class BaseTest {

    protected String boxes = "boxes";
    protected RequestSpecification spec;
    private String baseUrl = "https://api.thecatapi.com/v1/";

    @BeforeAll
    public void beforeSuite() {
        String apiKey = System.getProperty("api_key");
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .addHeader("x-api-key", apiKey)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

}
