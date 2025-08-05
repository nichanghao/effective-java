package net.sunday.effective.llm.springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OpenAIApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OpenAIApplication.class, args);
    }

}
