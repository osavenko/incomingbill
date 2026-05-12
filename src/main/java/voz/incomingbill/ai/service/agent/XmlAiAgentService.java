package voz.incomingbill.ai.service.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import voz.incomingbill.ai.service.AbstractAiAgentService;
import voz.incomingbill.ai.service.AbstractLoadMediaService;
import voz.incomingbill.ai.service.loader.LoadXmlMediaService;

@Service
public class XmlAiAgentService extends AbstractAiAgentService {
    private final LoadXmlMediaService loadXmlMediaService;

    public XmlAiAgentService(ChatClient.Builder builder, LoadXmlMediaService loadXmlMediaService) {
        super(builder);
        this.loadXmlMediaService = loadXmlMediaService;
    }

    @Override
    protected AbstractLoadMediaService getMediaLoader() {
        return loadXmlMediaService;
    }
}
