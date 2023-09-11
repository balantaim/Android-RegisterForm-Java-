package com.example.a10appuiinterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.a10appuiinterface.network.PostData;
import com.example.a10appuiinterface.databinding.ActivityMainBinding;
import com.example.a10appuiinterface.network.JsonPlaceholderForFakeData;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    //private Postgres dbManager;
    private Handler handler;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRegister();
                //registrationSuccess();
            }
        });
    }

    private void initRegister() {
        Log.d(TAG, "initRegister: Started");

        if (validateData()) {
            if (binding.agreementCheck.isChecked()) {
                registrationSuccess();
            } else {
                Toast.makeText(this, getResources().getString(R.string.toast_agree), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registrationSuccess() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
//        Snackbar.make(binding.parent, snackText, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Dismiss", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        binding.editTxtName.setText("");
//                        binding.editTxtEmail.setText("");
//                        binding.editTxtPassword.setText("");
//                        binding.editTxtPassRepeat.setText("");
//                    }
//                }).show();
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: Started");
        char c;
        boolean upperChar = false, lowerChar = false, numberChar = false;

        //name
        if (binding.editTxtName.getText().toString().equals("") || trySpace(binding.editTxtName.getText().toString())) {
            binding.txtWarnName.setVisibility(View.VISIBLE);
            return false;
        } else if (binding.editTxtName.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.toast_name_length), Toast.LENGTH_SHORT).show();
            binding.txtWarnName.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnName.setVisibility(View.GONE);
        }

        //email
        if (binding.editTxtEmail.getText().toString().equals("")) {
            binding.txtWarnEmail.setVisibility(View.VISIBLE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTxtEmail.getText().toString()).matches()) {
            binding.txtWarnEmail.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnEmail.setVisibility(View.GONE);
        }

        //password
        if (binding.editTxtPassword.getText().toString().equals("") || trySpace(binding.editTxtPassword.getText().toString())) {
            binding.txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        } else if (binding.editTxtPassword.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.toast_pass_length), Toast.LENGTH_SHORT).show();
            binding.txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnPass.setVisibility(View.GONE);
        }

        for (int i = 0; i < binding.editTxtPassword.length(); i++) {
            c = binding.editTxtPassword.getText().toString().charAt(i);
            if (Character.isUpperCase(c)) {
                upperChar = true;
            }
            if (Character.isLowerCase(c)) {
                lowerChar = true;
            }
            if (Character.isDigit(c)) {
                numberChar = true;
            }
        }

        if (!upperChar) {
            Toast.makeText(this, getResources().getString(R.string.toast_pass_upper), Toast.LENGTH_SHORT).show();
            binding.txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnPass.setVisibility(View.GONE);
        }

        if (!lowerChar) {
            Toast.makeText(this, getResources().getString(R.string.toast_pass_lower), Toast.LENGTH_SHORT).show();
            binding.txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnPass.setVisibility(View.GONE);
        }

        if (!numberChar) {
            Toast.makeText(this, getResources().getString(R.string.toast_pass_number), Toast.LENGTH_SHORT).show();
            binding.txtWarnPass.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnPass.setVisibility(View.GONE);
        }


        //repeat-pass
        if (binding.editTxtPassRepeat.getText().toString().equals("")) {
            binding.txtWarnPassRepeat.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnPassRepeat.setVisibility(View.GONE);
        }

        //pass==equal
        if (!binding.editTxtPassword.getText().toString().equals(binding.editTxtPassRepeat.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.toast_pass_match), Toast.LENGTH_SHORT).show();
            binding.txtWarnPassRepeat.setVisibility(View.VISIBLE);
            return false;
        } else {
            binding.txtWarnPassRepeat.setVisibility(View.GONE);
        }
        return true;
    }
    public boolean trySpace(String b) {
        for (int i = 0; i < b.length(); i++) {
            if (b.charAt(i) == ' ') {
                return true;
            }
        }
        return false;
    }

}