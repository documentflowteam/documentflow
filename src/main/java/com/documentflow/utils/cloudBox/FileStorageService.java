package com.documentflow.utils.cloudBox;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Component
public interface FileStorageService {
    void upload(MultipartFile file, String path);
    void download(String path, HttpServletResponse response);
}
