package cloud.tianai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@SpringBootApplication
public class CaptchaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptchaDemoApplication.class, args);
    }

}
