package com.documentflow.utils.cloudBox;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Primary
@Component
public class LocalStorage implements FileStorageService {
    private byte[] bytes;
    private BufferedOutputStream stream;

    @Override
    public void upload(MultipartFile file, String path) {
        Path tempPath = Paths.get(path).getParent();
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
}
