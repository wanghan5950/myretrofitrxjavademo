package com.example.RetrofitRxJavaDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.RetrofitRxJavaDemo.http.AppSchedulers;
import com.example.RetrofitRxJavaDemo.http.BaseObserver;
import com.example.RetrofitRxJavaDemo.http.HttpConfig;
import com.example.RetrofitRxJavaDemo.http.HttpManager;

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
