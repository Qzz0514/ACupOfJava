package net.onest.moment.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.moment.R;
import net.onest.moment.activity.ChatDetailActivity;
import net.onest.moment.activity.LoginActivity;
import net.onest.moment.activity.MyFangJian;
import net.onest.moment.activity.SeatActivity;
import net.onest.moment.activity.SystemChatActivity;
import net.onest.moment.adapter.MessageAdapter;
import net.onest.moment.adapter.ZiAdapter;
import net.onest.moment.entity.Message;
import net.onest.moment.entity.Shop;
import net.onest.moment.entity.User;
import net.onest.moment.manager.DefaultAddress;
import net.onest.moment.manager.DefaultAttribute;
import net.onest.moment.manager.ServerConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



/*qzz：消息列表Fragment*/
public class FourthFragment extends Fragment {
    private OkHttpClient okHttpClient;
    private List<Shop> lists;
    private List<Message> messageList = new ArrayList<>();
    private final int RESUIT = 0;
    private ListView listView;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    List<Shop> shops = (List<Shop>) msg.obj;
                    Log.e( "handleMessage: ",shops.toString() );
                    MessageAdapter messageAdapter = new MessageAdapter(getContext(), shops,R.layout.message_item);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent =new Intent(getContext(), MyFangJian.class);
                            int num =lists.get(position).getShopId();
                            String name =lists.get(position).getName();
                            int likes = lists.get(position).getLikes();
                            int stars = lists.get(position).getStars();
                            //Bitmap bitmap = lists.get(position).getBitmap();

                            Log.e("onItemClick: ", likes+"");
                            String location = lists.get(position).getLocation();
                            intent.putExtra("shop_id", num + "");
                            intent.putExtra("shop_name",name);
                            intent.putExtra("shop_likes", likes+"");
                            intent.putExtra("shop_stars", stars+"");
                            intent.putExtra("shop_location", location);
                            //intent.putExtra("shop_image", bitmap);

                            startActivity(intent);
                        }
                    });
                    messageAdapter.notifyDataSetInvalidated();
                    listView.setAdapter(messageAdapter);

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message,container,false);
        initOkHttpClient();
        simpStringParamPostRequest();
        listView = view.findViewById(R.id.lv_message);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ListView的点击事件
        //setOnItemClickListener()为ListView注册一个监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = messageList.get(position);
                Toast.makeText(getContext(),message.getTitle(),Toast.LENGTH_SHORT).show();
                if(message.getTitle().equals("系统消息")){
                    Intent intent1 = new Intent(getActivity(), SystemChatActivity.class);
                    startActivityForResult(intent1,RESUIT);
                }
                if(message.getTitle().equals("倒一杯Java")){
                    Intent intent2 = new Intent(getContext(), ChatDetailActivity.class);
                    startActivityForResult(intent2,RESUIT);
                }
            }
        });
    }

    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
    }

    private void simpStringParamPostRequest() {
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        //2) 创建请求对象
        final Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "shop/talkList?user_id=" +1 /*DefaultAttribute.DEFAULT_userID*/)
                .get()
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
//                //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("lww", "请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                if (result != null) {
                    Gson gson = new Gson();
                    lists=gson.fromJson(result,new TypeToken<List<Shop>>(){}.getType());
                    downLoadImgFromServerRequest();
                    android.os.Message dowmMsg = myHandler.obtainMessage();
                    dowmMsg.what = 1;
                    dowmMsg.obj = lists;
                    myHandler.sendMessage(dowmMsg);

                }


            }
        });

    }

    private void downLoadImgFromServerRequest() {
        List<Bitmap> bitmaps = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(lists.size());
        for (final Shop shop :lists) {
            Log.e("SHOPFOREACH", "---------------------------");
            Request request = new Request.Builder()
                    .url(ServerConfig.SERVER_HOME + "shop/talkImage?image="+shop.getImage()
                    +"&user_id="+"1"/*DefaultAttribute.DEFAULT_userID*/)
                    .build();
            //Log.e("downLoadImgFromServerRequest ", shop.getImage());
            //3. 创建CALL对象
            Call call = okHttpClient.newCall(request);
            //4. 发起异步请求并获取响应
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.i("sq", "请求图片失败");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    //获取服务端返回的网络输入流
                    InputStream in = response.body().byteStream();
                    //第1种：获取本地文件输出流，经过流的循环读写实现图片拷贝
                    //第2种：通过BitmapFactory将输入流解析成一个Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    //Log.e("Bitmap",bitmap.toString() );
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


















