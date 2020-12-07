package net.onest.moment.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import net.onest.moment.R;
import net.onest.moment.activity.MyFangJian;
import net.onest.moment.adapter.ZiAdapter;
import net.onest.moment.entity.Shop;
import net.onest.moment.manager.ServerConfig;

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
import okhttp3.RequestBody;
import okhttp3.Response;

//第一个内容页面对应的Fragment类
public class FirstFragment_sq extends Fragment {
    private OkHttpClient okHttpClient;
    private List<Shop> lists;
    private ListView listView1;
    private final int REFRESHUI_CODE = 1;
    private Handler myHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case REFRESHUI_CODE:
                    List<Shop> shops = (List<Shop>) msg.obj;
                    ZiAdapter adapter = new ZiAdapter(getContext(), shops, R.layout.sq_tab1_item);
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent =new Intent(getContext(), MyFangJian.class);
                            int shop_id = lists.get(position).getShopId();
                            String name = lists.get(position).getName();
                            int likes = lists.get(position).getLikes();
                            int stars = lists.get(position).getStars();
                            String shop_image = lists.get(position).getImage();

                            Log.e("onItemClick: ", likes+"");
                            String location = lists.get(position).getLocation();
                            intent.putExtra("shop_id", shop_id + "");
                            intent.putExtra("shop_name",name);
                            intent.putExtra("shop_likes", likes+"");
                            intent.putExtra("shop_stars", stars+"");
                            intent.putExtra("shop_location", location);
                            intent.putExtra("shop_image", shop_image);
                            startActivity(intent);
                        }
                    });
                    adapter.notifyDataSetInvalidated();
                    listView1.setAdapter(adapter);

            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载内容页面的布局文件（将内容页面的XML布局文件转成View类型的对象）
        View view = inflater.inflate(R.layout.sq_tab1,//内容页面的布局文件
                container,//根视图对象
                false);//false表示需要手动调用addView方法将view添加到container
        //true表示不需要手动调用addView方法

        //获取内容页面当中控件的引用
        listView1=view.findViewById(R.id.list1);
        initOkHttpClient();
        simpStringParamPostRequest();


        return view;
    }
    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
    }

    private void simpStringParamPostRequest() {
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        //2.创建请求对象
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "shop/hot")
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
                lists = gson.fromJson(result,new TypeToken<List<Shop>>(){}.getType());
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
        for (final Shop shop :lists) {
            Log.e("SHOPFOREACH", "---------------------------");
            Request request = new Request.Builder()
                    .url(ServerConfig.SERVER_HOME + "shop/receive?image=" + shop.getImage()+"&user_id=2")
                    .build();
            //Log.e("downLoadImgFromServerRequest ", shop.getImage());
            //3. 创建CALL对象
            Call call = okHttpClient.newCall(request);
            //4. 发起异步请求并获取响应
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.i("lww", "请求图片失败");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    //获取服务端返回的网络输入流
                    InputStream in = response.body().byteStream();
                    //第1种：获取本地文件输出流，经过流的循环读写实现图片拷贝
                    //第2种：通过BitmapFactory将输入流解析成一个Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(in);

                    shop.setBitmap(bitmap);
                    latch.countDown();
                    Log.e("获取图片当前的个数", latch.getCount()+"");
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

