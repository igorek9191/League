package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Categories {

    public static Response getListOfCategories(RequestSpecification specification){
        return given().spec(specification)
                      .when()
                      .get("categories/")
                      .thenReturn();
    }
}
