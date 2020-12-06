package net.onest.moment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.onest.moment.R;
import net.onest.moment.entity.Activitys;
import net.onest.moment.manager.ServerConfig;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Sale extends AppCompatActivity {
    private TextView textView;
    private ImageView sImage;
    private TextView sName;
    private TextView aName;
    private TextView soldNum;
    private TextView child;
    private TextView childPrice;
    private TextView total;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    Activitys activitys = (Activitys) msg.obj;
                    aName.setText(activitys.getName());
                    soldNum.setText(activitys.getNum()+"");
                    child.setText(activitys.getContentChild());
                    childPrice.setText(activitys.getContentChildPrice()+"");
                    total.setText(activitys.getTotal()+"");
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxh_sale);
        init();
        Intent intent = getIntent();
        int shopId = Integer.parseInt(intent.getStringExtra("shopId"));
        Log.e( "onCreate: receive", shopId+"");
        String shopName = intent.getStringExtra("shopName");
        String shopImage = intent.getStringExtra("shopImage");
        sName.setText(shopName);
        Glide.with(this)
                .load(ServerConfig.SERVER_HOME + "shop/receive?image=" + shopImage)
                .into(sImage);

        downloadActivity(shopId);
        //设置中划线
        textView = findViewById(R.id.totalPrice);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }

    private void downloadActivity(int shopId) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "activity/findActivity?shop_id="+shopId)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("lww", "请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                Log.e("onResponse: ",result );
                Activitys activitys = gson.fromJson(result, Activitys.class);
                Message message = handler.obtainMessage();
                message.what = 3 ;
                message.obj = activitys;
                handler.sendMessage(message);

            }
        });

    }

    private void init(){
        sImage = findViewById(R.id.shopImg);
        sName = findViewById(R.id.shopName);
        aName = findViewById(R.id.actiName);
        soldNum = findViewById(R.id.haveSold);
        child = findViewById(R.id.child);
        childPrice = findViewById(R.id.chlid_price);
        total = findViewById(R.id.groupPurchasePrice);

    }
}