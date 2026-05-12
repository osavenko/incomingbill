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

class LoadPdfMediaServiceTest {

    private LoadPdfMediaService service;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        service = new LoadPdfMediaService();
        ReflectionTestUtils.setField(service, "filePath", tempDir.toString());
    }

    @Test
    void loadFiles_shouldReturnEmptyList_whenNoFilesExist() {
        List<Media> result = service.loadFiles();

        assertTrue(result.isEmpty());
    }

    @Test
    void loadFiles_shouldLoadOnlyPdfFiles() throws IOException {
        Files.createFile(tempDir.resolve("invoice1.pdf"));
        Files.createFile(tempDir.resolve("invoice2.PDF"));
        Files.createFile(tempDir.resolve("document.txt"));
        Files.createFile(tempDir.resolve("image.jpg"));

        List<Media> result = service.loadFiles();

        assertEquals(2, result.size());
    }

    @Test
    void loadFiles_shouldLoadPdfFromSubdirectories() throws IOException {
        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        Files.createFile(tempDir.resolve("root.pdf"));
        Files.createFile(subDir.resolve("nested.pdf"));

        List<Media> result = service.loadFiles();

        assertEquals(2, result.size());
    }

    @Test
    void isValidFile_shouldReturnTrue_forPdfFiles() {
        Path pdfFile = tempDir.resolve("test.pdf");

        assertTrue(service.isValidFile(pdfFile));
    }

    @Test
    void isValidFile_shouldReturnTrue_forUppercasePdf() {
        Path pdfFile = tempDir.resolve("TEST.PDF");

        assertTrue(service.isValidFile(pdfFile));
    }

    @Test
    void isValidFile_shouldReturnFalse_forNonPdfFiles() {
        assertFalse(service.isValidFile(tempDir.resolve("test.txt")));
        assertFalse(service.isValidFile(tempDir.resolve("test.jpg")));
        assertFalse(service.isValidFile(tempDir.resolve("test.doc")));
    }

    @Test
    void getMedia_shouldCreateMediaWithPdfMimeType() throws IOException {
        Path pdfFile = Files.createFile(tempDir.resolve("test.pdf"));

        Media media = service.getMedia(pdfFile);

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
    void loadFiles_shouldHandleFilesWithPdfInMiddleOfName() throws IOException {
        Files.createFile(tempDir.resolve("my.pdf.backup"));
        Files.createFile(tempDir.resolve("invoice.pdf"));

        List<Media> result = service.loadFiles();

        assertEquals(1, result.size());
    }
}
