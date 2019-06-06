package com.example.my_retrofit_rxjava_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.my_retrofit_rxjava_demo.http.AppSchedulers;
import com.example.my_retrofit_rxjava_demo.http.BaseObserver;
import com.example.my_retrofit_rxjava_demo.http.HttpConfig;
import com.example.my_retrofit_rxjava_demo.http.HttpManager;

public class MainActivity extends AppCompatActivity {

    private TextView tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMain = findViewById(R.id.tv_main);
        Button btnRequest = findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(v -> getData1());
    }

    public void getData1() {
        String tag = HttpManager.recordMethodName();
        HttpConfig.httpAPI.getData1()
                .compose(AppSchedulers.compose())
                .subscribe(new BaseObserver<String>(tag) {
                    @Override
                    protected void onSuccess(String data, String msg) {
                        tvMain.setText(data);
                    }

                    @Override
                    protected void onFailure(String msg) {

                    }
                });
    }
}
