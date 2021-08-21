package league.test.task.endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import static io.restassured.RestAssured.given;

public class BreedsEndpoint extends EndpointTechnicalSteps {

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public BreedsEndpoint search(String nameOfBreed) {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .param("q", nameOfBreed)
                                   .when()
                                   .get("breeds/search/")
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

}
