package league.test.task.endpoints;

import io.restassured.response.Response;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class CategoriesEndpoint extends EndpointTechnicalSteps {

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public CategoriesEndpoint getListOfCategories() {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .when()
                                   .get("categories/")
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;

    }
}
