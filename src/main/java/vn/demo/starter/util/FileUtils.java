package vn.demo.starter.util;

import org.springframework.web.server.UnsupportedMediaTypeStatusException;

public final class FileUtils {
    private FileUtils() {}

    public static String getExtension(String mimeType) {
        switch (mimeType) {
        case "image/jpeg":
            return "jpeg";
        case "image/png":
            return "png";
        case "application/pdf":
            return "pdf";
        case "video/mp4":
            return "mp4";
        case "video/webm":
            return "webm";
        case "video/quicktime":
            return "mov";
        default:
            throw new UnsupportedMediaTypeStatusException("");
        }
    }

    public static String getFilename(String name, String mimeType) {
        return name;
    }
}
