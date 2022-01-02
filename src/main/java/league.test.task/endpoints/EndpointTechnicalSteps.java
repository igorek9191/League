package league.test.task.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class EndpointTechnicalSteps {

    private static Gson gsonInstance;

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonInstance = gsonBuilder.create();
        }

        return gsonInstance;
    }
}
