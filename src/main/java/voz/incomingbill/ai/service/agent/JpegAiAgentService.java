package voz.incomingbill.ai.service.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import voz.incomingbill.ai.service.AbstractAiAgentService;
import voz.incomingbill.ai.service.AbstractLoadMediaService;
import voz.incomingbill.ai.service.loader.LoadJpegMediaService;

@Service
public class JpegAiAgentService extends AbstractAiAgentService {
    private final LoadJpegMediaService loadJpegMediaService;

    public JpegAiAgentService(ChatClient.Builder builder, LoadJpegMediaService loadJpegMediaService) {
        super(builder);
        this.loadJpegMediaService = loadJpegMediaService;
    }

    @Override
    protected AbstractLoadMediaService getMediaLoader() {
        return loadJpegMediaService;
    }
}
