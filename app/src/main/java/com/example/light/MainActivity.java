package com.example.light;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private OutputStream mOutputStream = null;
    private Socket mSocket = null;
    private String ip = "121.40.165.18";
    private String data = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    //连接服务端
                    mSocket = new Socket(ip, 8800);
                    if (mSocket != null) {
                        Toast.makeText(getBaseContext(), "连接成功", Toast.LENGTH_LONG).show();
                    }
                    mOutputStream = mSocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data == "1") {
                    data = "0";
                    button.setBackgroundResource(R.drawable.light);
                } else {
                    data = "1";
                    button.setBackgroundResource(R.drawable.light_close);

                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mOutputStream.write(data.getBytes());
                            Log.d("AAA", "run: " + data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mOutputStream.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
