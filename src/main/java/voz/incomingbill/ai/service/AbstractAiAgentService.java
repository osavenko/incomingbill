package voz.incomingbill.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
public abstract class AbstractAiAgentService {
    public static final String HAVE_NOT_FILES_FOR_PROCESSING = "Have not files for processing";
    public static final String WE_HAVE_ANSWER = "We have answer[{}]";
    public static final String HAVEN_T_ACCESS = "Haven`t access";
    public static final String ERROR = "ERROR";
    public static final String ARE_YOU_HERE = "Ти тут?";

    private final ChatClient chatClient;
    @Autowired
    @Value("${app.prompts.system-role}")
    private String promptSystemRole;
    @Autowired
    @Value("${app.prompts.system-role}")
    private String promptUserRole;

    public AbstractAiAgentService(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    public String process() {
        List<Media> medias = getMediaLoader().loadFiles();

        if (medias.isEmpty()) {
            return HAVE_NOT_FILES_FOR_PROCESSING;
        }

        return chatClient.prompt()
                .system(promptSystemRole)
                .user(u -> u.text(promptUserRole)
                        .media(medias.toArray(new Media[0])))
                .call()
                .content();
    }

    public String testConnection() {
        String content = "";
        try {
            content = chatClient.prompt()
                    .user(ARE_YOU_HERE)
                    .call()
                    .content();
            log.info(WE_HAVE_ANSWER, content);
        } catch (Exception e) {
            log.error(HAVEN_T_ACCESS, e);
            content = ERROR;
        }
        return content;
    }

    protected abstract AbstractLoadMediaService getMediaLoader();
}
