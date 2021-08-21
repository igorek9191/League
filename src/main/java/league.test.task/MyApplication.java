package league.test.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "league.test.task")
public class MyApplication {

    public static void main(String[] args) {
        new SpringApplication(MyApplication.class).run(args);
    }
}
