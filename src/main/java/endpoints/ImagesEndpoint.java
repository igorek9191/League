package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ImagesEndpoint extends EndpointTechnicalSteps{

    public ImagesEndpoint search(RequestSpecification spec, String breedId) {
        Response response = given().spec(spec)
                      .params(new HashMap<String, String>() {{
                          put("breed_id", breedId);
                      }})
                      .when()
                      .get("images/search/");
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

}
