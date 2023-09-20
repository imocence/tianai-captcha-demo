package cloud.tianai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;


@Slf4j
@Controller
@SpringBootApplication
public class CaptchaDemoApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(CaptchaDemoApplication.class, args);
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = getParam("POST", run.getEnvironment().getProperty("server.port"));

        log.info("\n---------------------------------------------------------\n" +
                "Application Captcha is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External:\thttp://" + ip + ":" + port + "/" +
                "\n-----------------页面请部署 admin-web----------------------");
    }

    private static String getParam(String param, String defVal) {
        Optional<String> envPort = Optional.ofNullable(System.getenv(param));
        return envPort.map(String::valueOf).orElse(defVal);
    }

}
