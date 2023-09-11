package com.example.a10appuiinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a10appuiinterface.network.PostData;
import com.example.a10appuiinterface.network.JsonPlaceholderForFakeData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    TextView post;
    Button btn;
    private String myText = "";
    private final static String TAG = "HomeActivity";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        post = findViewById(R.id.allPosts);
        btn = findViewById(R.id.getMyList);

        btn.setOnClickListener(v -> {
            btn.setVisibility(View.GONE);
            retrofitInit();
        });
    }
    private void retrofitInit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderForFakeData jsonPlaceholderForFakeData = retrofit.create(JsonPlaceholderForFakeData.class);
        Call<List<PostData>> call = jsonPlaceholderForFakeData.getPosts();

        call.enqueue(new Callback<List<PostData>>() {
            @Override
            public void onResponse(Call<List<PostData>> call, Response<List<PostData>> response) {
                if(!response.isSuccessful()){
                    myText = "retrofit: Connection error " + response.code();
                    Log.d(TAG, "retrofit: Connection error " + response.code());
                    return;
                }
                List<PostData> data = response.body();
                for(PostData user: data){
                    myText += user.getUserid() +"\n";
                    myText += user.getId() +"\n";
                    myText += user.getTitle() +"\n";
                    myText += user.getBody() +"\n";
                    myText += "\n";
                }
                Log.d(TAG, "retrofit: "+ myText);
                post.setText(myText);
            }

            @Override
            public void onFailure(Call<List<PostData>> call, Throwable t) {

            }
        });
    }
}