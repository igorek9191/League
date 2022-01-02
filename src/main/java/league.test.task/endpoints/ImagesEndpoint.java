package league.test.task.endpoints;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import league.test.task.dto.images.BreedImages;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

@Component
public class ImagesEndpoint extends EndpointTechnicalSteps{

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public List<BreedImages> search(String breedId) {
        Response response = given().spec(requestSpecHandler.getSpec())
                      .params(new HashMap<String, String>() {{
                          put("breed_id", breedId);
                      }})
                      .when()
                      .get("images/search/");
        assert response.getBody() != null;
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.getList("", BreedImages.class);
    }

}
