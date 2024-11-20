package vn.demo.starter.consumer;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    String upload(MultipartFile file);

    void delete(String publicId);
}
