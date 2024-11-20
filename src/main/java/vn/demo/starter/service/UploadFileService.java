package vn.demo.starter.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface UploadFileService {

     String upload(MultipartFile file) throws IOException;

     void delete(String publicId);
}
