package voz.incomingbill.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.content.Media;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class AbstractLoadMediaService {

    public List<Media> loadFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(getScanPath()))) {
            List<Media> files = paths.filter(this::isValidFile)
                    .map(this::getMedia)
                    .toList();
            log.info("{} files were loaded", files.size());
            return files;
        } catch (Exception e) {
            log.error("Error scanning path: [{}]", getScanPath(), e);
            return List.of();
        }
    }

    protected abstract String getScanPath();

    protected abstract Media getMedia(Path path);

    protected abstract boolean isValidFile(Path path);
}
