package voz.incomingbill.ai.service.loader;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import voz.incomingbill.ai.service.AbstractLoadMediaService;

import java.nio.file.Path;

@Service
@Slf4j
public class LoadJpegMediaService extends AbstractLoadMediaService {

    private static final String LOAD_FILE_PATH = "[JPEG]: set path [{}]";
    public static final String CONVERT_FILE_TO_MEDIA = "Convert from [{}] jpeg file to media";
    public static final String END_WITH_JPG = ".jpg";
    public static final String END_WITH_JPEG = ".jpeg";

    @Value("${app.file-path}")
    private String filePath;

    @PostConstruct
    public void init() {
        log.info(LOAD_FILE_PATH, getScanPath());
    }

    @Override
    protected String getScanPath() {
        return filePath;
    }

    @Override
    protected Media getMedia(Path path) {
        log.info(CONVERT_FILE_TO_MEDIA, path);
        return new Media(MimeTypeUtils.IMAGE_JPEG, new FileSystemResource(path));
    }

    @Override
    protected boolean isValidFile(Path path) {
        String fileName = path.toString().toLowerCase();
        return fileName.endsWith(END_WITH_JPG) || fileName.endsWith(END_WITH_JPEG);
    }
}
