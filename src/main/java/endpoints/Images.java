package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Images {

    public static Response search(RequestSpecification spec, String breedId) {
        return given().spec(spec)
                      .params(new HashMap<String, String>() {{
                          put("breed_id", breedId);
                      }})
                      .when()
                      .get("images/search/");
    }

}
