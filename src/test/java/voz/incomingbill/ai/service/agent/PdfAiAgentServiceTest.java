package voz.incomingbill.ai.service.agent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.test.util.ReflectionTestUtils;
import voz.incomingbill.ai.service.loader.LoadPdfMediaService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static voz.incomingbill.ai.service.AbstractAiAgentService.*;

@ExtendWith(MockitoExtension.class)
class PdfAiAgentServiceTest {

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private LoadPdfMediaService loadPdfMediaService;

    private PdfAiAgentService service;

    @BeforeEach
    void setUp() {
        when(chatClientBuilder.build()).thenReturn(chatClient);
        service = new PdfAiAgentService(chatClientBuilder, loadPdfMediaService);
        ReflectionTestUtils.setField(service, "promptSystemRole", "System role prompt");
        ReflectionTestUtils.setField(service, "promptUserRole", "User role prompt");
    }

    @Test
    void process_shouldReturnMessage_whenNoFilesFound() {
        when(loadPdfMediaService.loadFiles()).thenReturn(Collections.emptyList());

        String result = service.process();

        assertEquals(HAVE_NOT_FILES_FOR_PROCESSING, result);
        verifyNoInteractions(chatClient);
    }

    @Test
    void getMediaLoader_shouldReturnLoadPdfMediaService() {
        assertSame(loadPdfMediaService, service.getMediaLoader());
    }

    @Test
    void constructor_shouldBuildChatClient() {
        verify(chatClientBuilder).build();
    }

    @Test
    void process_shouldUseMediaLoader() {
        when(loadPdfMediaService.loadFiles()).thenReturn(Collections.emptyList());

        service.process();

        verify(loadPdfMediaService).loadFiles();
    }
}
