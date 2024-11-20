package vn.demo.starter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.demo.starter.consumer.ICloudinaryService;
import vn.demo.starter.service.UploadFileService;

import java.io.IOException;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    ICloudinaryService cloudinaryService;

    @Override
    public String upload(MultipartFile file) throws IOException {
        return cloudinaryService.upload(file);
    }

    @Override
    public void delete(String publicId) {
        cloudinaryService.delete(publicId);

    }
}