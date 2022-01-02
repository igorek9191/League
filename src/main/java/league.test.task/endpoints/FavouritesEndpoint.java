package league.test.task.endpoints;

import com.google.gson.reflect.TypeToken;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import league.test.task.dto.favourites.DeleteFavouriteResponce;
import league.test.task.dto.favourites.Favourite;
import league.test.task.dto.favourites.Favourites;
import league.test.task.dto.favourites.PostFavouriteResponce;
import league.test.task.request_spec_handler.RequestSpecHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

@Component
public class FavouritesEndpoint extends EndpointTechnicalSteps {

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public PostFavouriteResponce postFavourites(String imageId) {
        Response response = given().spec(requestSpecHandler.getSpec())
                .when()
                .body(new HashMap<String, String>() {{
                    put("image_id", imageId);
                }})
                .post("favourites/");
        assert response.getBody() != null;
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.getObject("", PostFavouriteResponce.class);
    }

    @SneakyThrows
    public Favourites getFavourites() {
        Response response = given().spec(requestSpecHandler.getSpec())
                .when()
                .get("favourites/")
                .thenReturn();
        assert response.getBody() != null;

        Type collectionType = new TypeToken<List<Favourite>>() {
        }.getType();
        List<Favourite> favouritesResponse = getGsonInstance().fromJson(response.asString(), collectionType);
        Favourites favourites = new Favourites();
        favourites.setFavourites(favouritesResponse);
        return favourites;
    }

    public DeleteFavouriteResponce deleteFavourite(Integer favouriteId) {
        Response response = given().spec(requestSpecHandler.getSpec())
                .when()
                .delete("favourites/" + favouriteId)
                .thenReturn();
        assert response.getBody() != null;
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.getObject("", DeleteFavouriteResponce.class);
    }
}
