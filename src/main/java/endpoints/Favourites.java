package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Favourites {

    public static Response postFavourites(RequestSpecification spec, String imageId) {
        return given().spec(spec)
                      .when()
                      .body(new HashMap<String, String>() {{
                          put("image_id", imageId);
                      }})
                      .post("favourites/");
    }

    public static Response getFavourites(RequestSpecification spec) {
        return given().spec(spec)
                      .when()
                      .get("favourites/")
                      .thenReturn();
    }

    public static Response deleteFavourite(RequestSpecification spec, Integer favouriteId) {
        return given().spec(spec)
                      .when()
                      .delete("favourites/" + favouriteId)
                      .thenReturn();
    }
}
