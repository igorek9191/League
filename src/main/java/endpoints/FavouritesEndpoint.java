package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class FavouritesEndpoint extends EndpointTechnicalSteps {

    public FavouritesEndpoint() {
        super();
    }

    public FavouritesEndpoint postFavourites(RequestSpecification spec, String imageId) {
        Response response = given().spec(spec)
                                   .when()
                                   .body(new HashMap<String, String>() {{
                                       put("image_id", imageId);
                                   }})
                                   .post("favourites/");
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

    public FavouritesEndpoint getFavourites(RequestSpecification spec) {
        Response response = given().spec(spec)
                                   .when()
                                   .get("favourites/")
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

    public FavouritesEndpoint deleteFavourite(RequestSpecification spec, Integer favouriteId) {
        Response response = given().spec(spec)
                                   .when()
                                   .delete("favourites/" + favouriteId)
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;
    }
}
