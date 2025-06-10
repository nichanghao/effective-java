package net.sunday.effective.skywalking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkywalkingApplication {

    /**
     * JVM 启动参数: -javaagent:/path/skywalking-agent.jar -Dskywalking.agent.service_name=skywalking-server
     */
    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApplication.class, args);
    }
}
