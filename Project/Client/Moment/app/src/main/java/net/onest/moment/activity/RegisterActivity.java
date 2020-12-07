package net.onest.moment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.onest.moment.R;
import net.onest.moment.manager.DefaultAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*qzz：注册界面*/
public class RegisterActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnRegister;
    private EditText etReUserName;
    private EditText etReUserEmail;
    private EditText etReUserPwd;
    private String rename;
    private String reemail;
    private String repwd;

    private static final int RES_FAIL = 1;
    private static final int RES_SUS=2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case RES_FAIL:
                    Toast.makeText(RegisterActivity.this,"邮箱已被注册!",Toast.LENGTH_SHORT).show();
                    break;
                case RES_SUS:
                    Toast.makeText(RegisterActivity.this,"请在邮箱中验证注册!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        etReUserName = findViewById(R.id.et_reusername);
        etReUserEmail = findViewById(R.id.et_reuseremail);
        etReUserPwd = findViewById(R.id.et_reuserpwd);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rename = etReUserName.getText().toString();
                reemail = etReUserEmail.getText().toString();
                repwd = etReUserPwd.getText().toString();

                if(rename.length() > 0 && reemail.length() > 0 && repwd.length() > 0){
                    Register(DefaultAddress.DEFAULT_ADDRESS + "user/");
                }else if(rename.length() == 0 || reemail.length() == 0 || repwd.length() == 0){
                    Toast.makeText(RegisterActivity.this,"用户名或邮箱或密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void Register(final String s) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(s + "register?name=" + rename +"&email=" + reemail + "&password=" + repwd);
                    Log.e("url",url.toString());
                    InputStream inputStream = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

                    String confirm = reader.readLine();
                    Log.e("url",confirm);
                    if(confirm.equals("ready to makesure")){
                        Message message = handler.obtainMessage();
                        message.what = RES_SUS;
                        handler.sendMessage(message);
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("reuserName",rename);
                        intent.putExtra("reuserEmail",reemail);
                        intent.putExtra("reuserPwd",repwd);
                        startActivity(intent);
                    } else{
                        Message message = handler.obtainMessage();
                        message.what = RES_FAIL;
                        handler.sendMessage(message);
                    }
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
