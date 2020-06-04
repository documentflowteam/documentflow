package com.documentflow.utils.fileStorage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Primary
@Component
public class LocalStorage implements FileStorageService {
    private byte[] bytes;
    private BufferedOutputStream stream;
    private Path filePath;
    private Path tempPath;
    private String filename;

    @Override
    public void upload(MultipartFile file, String path) {
        tempPath = Paths.get(path).getParent();
        if (Files.notExists(tempPath)) {
            try {
                Files.createDirectory(tempPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bytes = file.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            stream.write(bytes);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(String path, HttpServletResponse response) {
        filePath = Paths.get(path);
        filename = filePath.getFileName().toString();
        if (Files.exists(filePath)) {
            response.setContentType(URLConnection.guessContentTypeFromName(filename));
            response.setCharacterEncoding("utf-8");
            try {
                response.addHeader("Filename", URLEncoder.encode(filename, "UTF8"));
                Files.copy(filePath, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
