package voz.incomingbill.ai.service.agent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.test.util.ReflectionTestUtils;
import voz.incomingbill.ai.service.loader.LoadJpegMediaService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static voz.incomingbill.ai.service.AbstractAiAgentService.*;

@ExtendWith(MockitoExtension.class)
class JpegAiAgentServiceTest {

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private LoadJpegMediaService loadJpegMediaService;

    private JpegAiAgentService service;

    @BeforeEach
    void setUp() {
        when(chatClientBuilder.build()).thenReturn(chatClient);
        service = new JpegAiAgentService(chatClientBuilder, loadJpegMediaService);
        ReflectionTestUtils.setField(service, "promptSystemRole", "System role prompt");
        ReflectionTestUtils.setField(service, "promptUserRole", "User role prompt");
    }

    @Test
    void process_shouldReturnMessage_whenNoFilesFound() {
        when(loadJpegMediaService.loadFiles()).thenReturn(Collections.emptyList());

        String result = service.process();

        assertEquals(HAVE_NOT_FILES_FOR_PROCESSING, result);
        verifyNoInteractions(chatClient);
    }

    @Test
    void getMediaLoader_shouldReturnLoadJpegMediaService() {
        assertSame(loadJpegMediaService, service.getMediaLoader());
    }

    @Test
    void constructor_shouldBuildChatClient() {
        verify(chatClientBuilder).build();
    }

    @Test
    void process_shouldUseMediaLoader() {
        when(loadJpegMediaService.loadFiles()).thenReturn(Collections.emptyList());

        service.process();

        verify(loadJpegMediaService).loadFiles();
    }
}
