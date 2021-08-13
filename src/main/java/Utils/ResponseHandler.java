package Utils;

import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;

public class ResponseHandler {

    private Response response;
    private ResponseBodyExtractionOptions body;

    public ResponseHandler extractBody(Response response) {
        this.response = response;
        this.body = this.response.then().extract().body();
        return this;
    }

    public String getJsonValue(String jsonPath){
        return this.body.jsonPath().get(jsonPath);
    }

    public static <T> T getJsonValue(Response response, String jsonPath) {
        return response.then().extract().body().jsonPath().get(jsonPath);
    }

}
