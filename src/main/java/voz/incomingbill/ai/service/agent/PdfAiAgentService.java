package voz.incomingbill.ai.service.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import voz.incomingbill.ai.service.AbstractAiAgentService;
import voz.incomingbill.ai.service.AbstractLoadMediaService;
import voz.incomingbill.ai.service.loader.LoadPdfMediaService;

@Service
public class PdfAiAgentService extends AbstractAiAgentService {
    private final LoadPdfMediaService loadPdfMediaService;

    public PdfAiAgentService(ChatClient.Builder builder, LoadPdfMediaService loadPdfMediaService) {
        super(builder);
        this.loadPdfMediaService = loadPdfMediaService;
    }

    @Override
    protected AbstractLoadMediaService getMediaLoader() {
        return loadPdfMediaService;
    }
}
