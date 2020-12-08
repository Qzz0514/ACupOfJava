package net.onest.moment.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.moment.R;
import net.onest.moment.entity.Shop;
import net.onest.moment.manager.ServerConfig;
import net.onest.moment.adapter.ZiAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SQMainActivity extends AppCompatActivity {

    private Button btnBack;
    private OkHttpClient okHttpClient;
    private List<Shop> lists = new ArrayList<>();
    private ListView listView1;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    List<Shop> shops = (List<Shop>) msg.obj;
                    Log.e( "handleMessage: ",shops.toString() );
                    ZiAdapter adapter = new ZiAdapter(SQMainActivity.this, shops, R.layout.sq_tab1_item);
                    adapter.notifyDataSetChanged();
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SQMainActivity.this, MyFangJian.class);

                            int num =lists.get(position).getShopId();
                            String name =lists.get(position).getName();
                            int likes = lists.get(position).getLikes();
                            int stars = lists.get(position).getStars();
                            Bitmap bitmap = lists.get(position).getBitmap();
                            //Bundle bundle = new Bundle();
                            //bundle.putParcelable("bitmap", bitmap);

                            Log.e("onItemClick: ", likes+"");
                            String location = lists.get(position).getLocation();
                            intent.putExtra("shop_id", num + "");
                            intent.putExtra("shop_name",name);
                            intent.putExtra("shop_likes", likes+"");
                            intent.putExtra("shop_stars", stars+"");
                            intent.putExtra("shop_location", location);
                            //intent.putExtra("bitmap", bitmap);

                            //intent.putExtra("bundle", bundle);
                            startActivity(intent);
                        }
                    });

                    adapter.notifyDataSetInvalidated();
                    listView1.setAdapter(adapter);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sq_activity_main);


        listView1 = findViewById(R.id.list1);

        initOkHttpClient();
        simpStringParamPostRequest();

        btnBack=findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
    }


    private void simpStringParamPostRequest() {
        MediaType type = MediaType.parse("text/plain");
        //1.创建RequestBody对象
        //2.创建请求对象
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "shop/findMyLikes?user_id=2")
                .get()
                .build();
        //3.创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4.提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("sq", "请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                lists=gson.fromJson(result,new TypeToken<List<Shop>>(){}.getType());
                downLoadImgFromServerRequest();
                Message dowmMsg = myHandler.obtainMessage();
                dowmMsg.what = 1;
                dowmMsg.obj = lists;
                myHandler.sendMessage(dowmMsg);
            }
        });
    }


    private void downLoadImgFromServerRequest() {
        List<Bitmap> bitmaps = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(lists.size());
        for (final Shop shop : lists) {
            Log.e("SHOPFOREACH", "---------------------------");
            Request request = new Request.Builder()
                    .url(ServerConfig.SERVER_HOME + "shop/myReceive?image=" + shop.getImage() + "&user_id=2")
                    .build();
            //3.创建CALL对象
            Call call = okHttpClient.newCall(request);
            //4.发起异步请求并获取响应
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("sq", "请求图片失败");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    //获取服务端返回的网络输入流
                    InputStream in = response.body().byteStream();
                    //第1种：获取本地文件输出流，经过流的循环读写实现图片拷贝
                    //第2种：通过BitmapFactory将输入流解析成一个Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Log.e("Bitmap",bitmap.toString() );
                    shop.setBitmap(bitmap);
                    latch.countDown();
                    Log.e("获取图片当前的个数", latch.getCount() + "");
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}