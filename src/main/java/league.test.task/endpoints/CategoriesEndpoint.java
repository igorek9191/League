package league.test.task.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import league.test.task.dto.category.Categories;
import league.test.task.dto.category.Category;
import league.test.task.dto.favourites.Favourite;
import league.test.task.dto.favourites.Favourites;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.RestAssured.given;

@Component
public class CategoriesEndpoint extends EndpointTechnicalSteps {

    @Lazy
    @Autowired
    private RequestSpecHandler requestSpecHandler;

    public Categories getListOfCategories() {
        Response response = given().spec(requestSpecHandler.getSpec())
                                   .when()
                                   .get("categories/")
                                   .thenReturn();
        assert response.getBody() != null;

        Type collectionType = new TypeToken<List<Category>>(){}.getType();
        List<Category> сategoriesResponse = getGsonInstance().fromJson(response.asString(), collectionType);
        Categories categories =  new Categories();
        categories.setCategories(сategoriesResponse);
        return categories;
    }
}
