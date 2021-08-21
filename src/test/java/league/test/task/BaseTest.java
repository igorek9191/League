package league.test.task;

import league.test.task.request_spec_handler.RequestSpecHandler;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

@SpringBootTest(classes = MyApplication.class)
public class BaseTest {

    @Lazy
    @Autowired
    RequestSpecHandler requestSpecHandler;

    @BeforeAll
    public void beforeSuite() {
        requestSpecHandler.initRequestSpecification();
    }

}
