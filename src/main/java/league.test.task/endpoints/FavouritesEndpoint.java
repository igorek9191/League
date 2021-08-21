package league.test.task.endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@Component
public class FavouritesEndpoint extends EndpointTechnicalSteps {

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public FavouritesEndpoint postFavourites(String imageId) {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .when()
                                   .body(new HashMap<String, String>() {{
                                       put("image_id", imageId);
                                   }})
                                   .post("favourites/");
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

    public FavouritesEndpoint getFavourites() {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .when()
                                   .get("favourites/")
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;
    }

    public FavouritesEndpoint deleteFavourite(Integer favouriteId) {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .when()
                                   .delete("favourites/" + favouriteId)
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;
    }
}
