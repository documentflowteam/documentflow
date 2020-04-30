package com.documentflow.utils.cloudBox;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CloudStorageYandexRet {
    @Multipart
    @POST("/v1/disk/resources/upload")
    Call<ResponseBody> upload(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file);
}
