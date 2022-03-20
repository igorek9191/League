package league.test.task.endpoints;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import league.test.task.dto.breed.Breed;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.restassured.RestAssured.given;

@Component
public class BreedsEndpoint extends EndpointTechnicalSteps {

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public List<Breed> search(String nameOfBreed) {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .param("q", nameOfBreed)
                                   .when()
                                   .get("breeds/search/")
                                   .thenReturn();
        assert response.getBody() != null;
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.getList("", Breed.class);

    }

}
