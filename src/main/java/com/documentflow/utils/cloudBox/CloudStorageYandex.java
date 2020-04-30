package com.documentflow.utils.cloudBox;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Primary
@Component
public class CloudStorageYandex implements FileStorageService {

    private CloudStorageYandexRet service = RetrofitInstance.getRetrofitInstance().create(CloudStorageYandexRet.class);
    private final String token = "OAuth AgAAAAANwDryAAZNOj4RaCvG2kvXl1VWBkpwO1Q";
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

//        uploadFile = new File(path);
//
//        if (!uploadFile.exists()) {
//            throw new FileNotFoundException("File is not exist");
//        }
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", uploadFile.getName(), requestFile);
//
//        Call<ResponseBody> call = service.upload(token, body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.print("Upload success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                System.out.print("Upload error" + t.getMessage());
//            }
//        });
    }

}
