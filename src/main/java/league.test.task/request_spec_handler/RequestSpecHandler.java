package league.test.task.request_spec_handler;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RequestSpecHandler {

    private String baseUrl = "https://api.thecatapi.com/v1/";
    private RequestSpecification spec;

    public void initRequestSpecification(){
        String apiKey = System.getProperty("api_key");
        this.spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .addHeader("x-api-key", apiKey)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
        System.out.println("\n");
    }
}
