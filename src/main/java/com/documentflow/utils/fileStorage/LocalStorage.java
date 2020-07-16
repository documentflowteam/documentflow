package com.documentflow.utils.fileStorage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.FileSystemException;
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
    public void upload(MultipartFile file, String path) throws FileSystemException {
        tempPath = Paths.get(path).getParent();
        if (Files.notExists(tempPath)) {
            try {
                Files.createDirectory(tempPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileSystemException("Unable to create a directory " + tempPath);
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
            throw new FileSystemException("Unable to save the file");
        }
    }

    @Override
    public void download(String path, HttpServletResponse response) throws FileNotFoundException {
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
                throw new FileNotFoundException();
            }
        }
    }
}
