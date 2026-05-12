package voz.incomingbill.ai.service.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.ai.content.Media;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoadJpegMediaServiceTest {

    private LoadJpegMediaService service;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        service = new LoadJpegMediaService();
        ReflectionTestUtils.setField(service, "filePath", tempDir.toString());
    }

    @Test
    void loadFiles_shouldReturnEmptyList_whenNoFilesExist() {
        List<Media> result = service.loadFiles();

        assertTrue(result.isEmpty());
    }

    @Test
    void loadFiles_shouldLoadBothJpgAndJpegFiles() throws IOException {
        Files.createFile(tempDir.resolve("image1.jpg"));
        Files.createFile(tempDir.resolve("image2.jpeg"));
        Files.createFile(tempDir.resolve("image3.JPG"));
        Files.createFile(tempDir.resolve("image4.JPEG"));
        Files.createFile(tempDir.resolve("document.pdf"));

        List<Media> result = service.loadFiles();

        assertEquals(4, result.size());
    }

    @Test
    void loadFiles_shouldLoadJpegFromSubdirectories() throws IOException {
        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        Files.createFile(tempDir.resolve("root.jpg"));
        Files.createFile(subDir.resolve("nested.jpeg"));

        List<Media> result = service.loadFiles();

        assertEquals(2, result.size());
    }

    @Test
    void isValidFile_shouldReturnTrue_forJpgFiles() {
        assertTrue(service.isValidFile(tempDir.resolve("test.jpg")));
        assertTrue(service.isValidFile(tempDir.resolve("TEST.JPG")));
    }

    @Test
    void isValidFile_shouldReturnTrue_forJpegFiles() {
        assertTrue(service.isValidFile(tempDir.resolve("test.jpeg")));
        assertTrue(service.isValidFile(tempDir.resolve("TEST.JPEG")));
    }

    @Test
    void isValidFile_shouldReturnFalse_forNonJpegFiles() {
        assertFalse(service.isValidFile(tempDir.resolve("test.pdf")));
        assertFalse(service.isValidFile(tempDir.resolve("test.png")));
        assertFalse(service.isValidFile(tempDir.resolve("test.txt")));
    }

    @Test
    void getMedia_shouldCreateMediaWithJpegMimeType() throws IOException {
        Path jpegFile = Files.createFile(tempDir.resolve("test.jpg"));

        Media media = service.getMedia(jpegFile);

        assertNotNull(media);
    }

    @Test
    void getScanPath_shouldReturnConfiguredPath() {
        String expectedPath = tempDir.toString();

        String actualPath = service.getScanPath();

        assertEquals(expectedPath, actualPath);
    }

    @Test
    void loadFiles_shouldReturnEmptyList_whenDirectoryDoesNotExist() {
        ReflectionTestUtils.setField(service, "filePath", "c:/nonexistent/path");

        List<Media> result = service.loadFiles();

        assertTrue(result.isEmpty());
    }

    @Test
    void loadFiles_shouldHandleMixedCaseExtensions() throws IOException {
        Files.createFile(tempDir.resolve("image1.JpG"));
        Files.createFile(tempDir.resolve("image2.JpEg"));

        List<Media> result = service.loadFiles();

        assertEquals(2, result.size());
    }
}
