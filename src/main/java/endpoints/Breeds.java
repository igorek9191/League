package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Breeds {

    public static Response search(RequestSpecification spec, String nameOfBreed) {
        return given().spec(spec)
                      .param("q", nameOfBreed)
                      .when()
                      .get("breeds/search/")
                      .thenReturn();
    }

}
