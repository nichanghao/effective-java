package integration.net.sunday.effective.llm.chat;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;

public class LangChainOpenAITest {

    private final OpenAiChatModel chatModel = OpenAiChatModel.builder()
            .baseUrl("http://langchain4j.dev/demo/openai/v1")
            .apiKey("demo")
            .modelName("gpt-4o-mini")
            .build();

    @Test
    void test() {
        String answer = chatModel.chat("Say 'Hello World'");
        System.out.println(answer);
    }

    @Test
    void testImageInput() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("What do you see?"),
                ImageContent.from("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png")
        );

        ChatResponse chatResponse = chatModel.chat(userMessage);

        System.out.println(chatResponse.aiMessage().text());
    }
}
