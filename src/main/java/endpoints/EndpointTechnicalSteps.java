package endpoints;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class EndpointTechnicalSteps {

    protected Response response;

    public <T> List<T> getBodyAsListOf(Class<T> aClass) {
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.getList("", aClass);
    }

    public <T> T getBodyAs(Class<T> aClass) {
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.getObject("", aClass);
    }
}
