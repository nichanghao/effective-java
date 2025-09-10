package integration.net.sunday.effective.llm.chat;

import org.junit.jupiter.api.Test;
import org.springaicommunity.moonshot.MoonshotChatModel;
import org.springaicommunity.moonshot.MoonshotChatOptions;
import org.springaicommunity.moonshot.api.MoonshotApi;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link MoonshotChatModel} 的集成测试
 */
public class MoonshotChatModelTest {

    private final MoonshotChatModel chatModel = MoonshotChatModel.builder()
            .moonshotApi(MoonshotApi.builder()
                    .apiKey("sk-9wt3HhFpMPZs9py3y7PjjHiIVPfeQQObsL1aZ2fMGzOXR2du") // 密钥
                    .build())
            .defaultOptions(MoonshotChatOptions.builder()
                    .model("kimi-k2-0711-preview") // 模型
                    .build())
            .build();


    @Test
    public void testCall() {
        // 准备参数
        long startTime = System.currentTimeMillis();
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("你是一个优质的文言文作者，用文言文描述着各城市的人文风景。"));
        messages.add(new UserMessage("1 + 1 = ？"));

        // 调用
        ChatResponse response = chatModel.call(new Prompt(messages));
        // 打印结果
        System.out.println(response);
        System.out.println(response.getResult().getOutput());
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "ms");

    }

    @Test
    public void testStream() {
        // 准备参数
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("你是一个优质的文言文作者，用文言文描述着各城市的人文风景。"));
        messages.add(new UserMessage("1 + 1 = ？"));

        // 调用
        Flux<ChatResponse> flux = chatModel.stream(new Prompt(messages));
        // 打印结果
        flux.doOnNext(response -> System.out.println(response.getResult().getOutput())).then().block();
    }

}
