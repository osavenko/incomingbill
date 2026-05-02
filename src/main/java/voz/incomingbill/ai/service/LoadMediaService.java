package voz.incomingbill.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class LoadMediaService {

    public static final String LOAD_FILE_PATH = "load file path: {}";
    public static final String CONVERT_FILE_TO_MEDIA = "Convert file [{}] to media";
    public static final String END_WITH_JPG = ".jpg";
    public static final String END_WITH_JPEG = ".jpeg";

    @Value("${app.file-path}")
    private String filePath;

    @PostConstruct
    public void init() {
        log.info(LOAD_FILE_PATH, false);
    }

    public List<Media> loadFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(filePath))) {
            List<Media> files = paths.filter(this::isValidFile)
                    .map(this::getMedia)
                    .toList();
            log.info("{} files were loaded", files.size());
            return files;
        } catch (Exception e) {
            log.error("Error scanning path: [{}]", filePath, e);
            return List.of();
        }
    }

    private Media getMedia(Path path) {
        log.info(CONVERT_FILE_TO_MEDIA, path);
        return new Media(MimeTypeUtils.IMAGE_JPEG, new FileSystemResource(path));
    }

    private boolean isValidFile(Path path) {
        String fileName = path.toString().toLowerCase();

        return fileName.endsWith(END_WITH_JPG) || fileName.endsWith(END_WITH_JPEG);
    }
}
