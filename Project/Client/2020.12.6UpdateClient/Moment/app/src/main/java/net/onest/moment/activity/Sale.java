package net.onest.moment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.onest.moment.R;
import net.onest.moment.manager.ServerConfig;

public class Sale extends AppCompatActivity {
    private TextView textView;
    private ImageView sImage;
    private TextView sName;
    private TextView aName;
    private TextView soldNum;
    private TextView child;
    private TextView childPrice;
    private TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxh_sale);
        init();
        Intent intent = getIntent();
        String shopId = intent.getStringExtra("shopId");
        String shopName = intent.getStringExtra("shopName");
        String shopImage = intent.getStringExtra("shopImage");
        sName.setText(shopName);
        Glide.with(this)
                .load(ServerConfig.SERVER_HOME + "shop/receive?image=" + shopImage)
                .into(sImage);
        //设置中划线
        textView = findViewById(R.id.totalPrice);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }
    private void init(){
        sImage = findViewById(R.id.shopImg);
        sName = findViewById(R.id.shopName);

    }
}