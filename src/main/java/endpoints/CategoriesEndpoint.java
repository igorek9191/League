package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CategoriesEndpoint extends EndpointTechnicalSteps {

    public CategoriesEndpoint getListOfCategories(RequestSpecification specification) {
        Response response = given().spec(specification)
                                   .when()
                                   .get("categories/")
                                   .thenReturn();
        assert response.getBody() != null;
        this.response = response;
        return this;

    }
}
