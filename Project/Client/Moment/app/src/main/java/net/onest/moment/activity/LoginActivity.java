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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import net.onest.moment.R;
import net.onest.moment.fragments.FourthFragment;
import net.onest.moment.manager.DefaultAddress;
import net.onest.moment.manager.DefaultAttribute;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*qzz：登录界面*/
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUserEmail;
    private EditText etUserPwd;
    private TextView tvRegister;
    private String email;
    private String pwd;
    private String user;

    private static final int LOGIN_CODE = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case LOGIN_CODE:
                    Toast.makeText(LoginActivity.this,"邮箱或密码错误!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        etUserEmail = findViewById(R.id.et_useremail);
        etUserPwd = findViewById(R.id.et_userpwd);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etUserEmail.getText().toString();
                pwd = etUserPwd.getText().toString();
                if(email.length() > 0 && pwd.length() > 0){
                    //判断邮箱和密码是否正确
                    Login(DefaultAddress.DEFAULT_ADDRESS + "user/");
                }else {
                    Toast.makeText(LoginActivity.this,"邮箱或密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void Login(final String s){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(s + "login?email=" + email + "&password=" + pwd);
                    Log.e("url",url.toString());
                    InputStream inputStream = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    user = reader.readLine();
                    Log.e("user",user);

                    JSONObject jsonObject = new JSONObject(user);
                    DefaultAttribute.DEFAULT_userID = jsonObject.getInt("userId");
                    DefaultAttribute.DEFAULT_userEMAIL = jsonObject.getString("email");
                    DefaultAttribute.DEFAULT_userNAME = jsonObject.getString("name");

                    if(user.equals("login failed")){
                        Message message = handler.obtainMessage();
                        message.what = LOGIN_CODE;
                        handler.sendMessage(message);
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, FragmentMainActivity.class);
                        intent.putExtra("userEmail",email);
                        intent.putExtra("userPwd",pwd);
                        LoginActivity.this.startActivity(intent);
                    }
                    inputStream.close();
                    reader.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
