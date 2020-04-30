package com.documentflow.utils.cloudBox;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface FileStorageService {
    void upload(MultipartFile file, String path);
}
