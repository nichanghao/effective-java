package integration.net.sunday.effective.llm.knowledge;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.ByteArrayResource;

import java.util.Collections;
import java.util.List;

@Slf4j
public class AiKnowledgeSegmentTest {

    /**
     * 测试内容分割切片
     */
    @Test
    void testSplitContent() {
        // 解析内容
        String content = parseTextFromUrl("https://tika.apache.org/");
        Assertions.assertNotNull(content);

        // 内容切片
        List<Document> documentList = TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(200) // 在 200 字符后寻找句末断点（根据文体调整）
                .withMinChunkLengthToEmbed(1) // 允许的最小有效分段长度
                .withMaxNumChunks(Integer.MAX_VALUE)
                .withKeepSeparator(true) // 保留分隔符
                .build().apply(Collections.singletonList(new Document(content)));

        documentList.forEach(document -> {
            log.info("split content: {}", document.getText());
        });
    }

    private String parseTextFromUrl(String url) {
        // 下载文件
        ByteArrayResource resource = null;
        try {
            byte[] bytes = HttpUtil.downloadBytes(url);
            if (bytes.length == 0) {
                return null;
            }
            resource = new ByteArrayResource(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        // 读取文件
        TikaDocumentReader loader = new TikaDocumentReader(resource);
        List<Document> documents = loader.get();
        Document document = CollUtil.getFirst(documents);
        if (document == null || StrUtil.isEmpty(document.getText())) {
            return null;
        }
        return document.getText();
    }
}
