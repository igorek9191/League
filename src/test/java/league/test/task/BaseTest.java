package league.test.task;

import league.test.task.endpoints.BreedsEndpoint;
import league.test.task.endpoints.CategoriesEndpoint;
import league.test.task.endpoints.FavouritesEndpoint;
import league.test.task.endpoints.ImagesEndpoint;
import league.test.task.request_spec_handler.RequestSpecHandler;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MyApplication.class)
@ContextConfiguration(classes = {
        RequestSpecHandler.class,
        BreedsEndpoint.class,
        ImagesEndpoint.class,
        FavouritesEndpoint.class,
        CategoriesEndpoint.class
})
//@ContextConfiguration(loader= AnnotationConfigContextLoader.class)
public class BaseTest {

    @Lazy
    @Autowired
    RequestSpecHandler requestSpecHandler;

    @BeforeAll
    public void beforeSuite() {
        requestSpecHandler.initRequestSpecification();
    }

}
