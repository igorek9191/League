package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BreedsEndpoint extends EndpointTechnicalSteps{

    public BreedsEndpoint(){
        super();
    }

    public BreedsEndpoint search(RequestSpecification spec, String nameOfBreed) {
         Response response = given().spec(spec)
                      .param("q", nameOfBreed)
                      .when()
                      .get("breeds/search/")
                      .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

}
