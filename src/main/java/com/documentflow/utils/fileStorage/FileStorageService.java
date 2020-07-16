package com.documentflow.utils.fileStorage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

@Component
public interface FileStorageService {
    void upload(MultipartFile file, String path) throws FileSystemException;
    void download(String path, HttpServletResponse response) throws FileNotFoundException;
}
