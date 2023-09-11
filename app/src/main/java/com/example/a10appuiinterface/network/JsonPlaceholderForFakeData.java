package com.example.a10appuiinterface.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderForFakeData {
    @GET("posts")
    Call<List<PostData>> getPosts();
}
