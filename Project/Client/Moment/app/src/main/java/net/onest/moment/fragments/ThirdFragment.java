package net.onest.moment.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.moment.R;
import net.onest.moment.adapter.ReleaseAdapter;
import net.onest.moment.entity.Release;
import net.onest.moment.manager.DefaultAddress;

import org.jetbrains.annotations.NotNull;

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

/*qzz：发布Fragment*/
public class ThirdFragment extends Fragment {

    private OkHttpClient okHttpClient;
    private List<Release> releaseList;
    private final int RESULT = 0;
    private ListView listView;


    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    List<Release> releases = (List<Release>) msg.obj;
                    Log.e("handleMessage",releases.toString());
                    ReleaseAdapter adapter = new ReleaseAdapter(getContext(),releases,R.layout.zx_pyq_item);
                    adapter.notifyDataSetInvalidated();
                    listView.setAdapter(adapter);

            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载内容页面的布局文件（将内容页面的XML布局文件转成View类型的对象）
        View view = inflater.inflate(R.layout.zx_fragment_third
                , //内容页面的布局文件
                container, //根视图对象
                false); //false表示需要手动调用addView方法将view添加到container（true表示不需要手动调用addView方法）
        initOkHttpClient();
        listView = view.findViewById(R.id.lv_pyq);
        return view;
    }


    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
    }


    private void simpStringParamPostRequest(){
        MediaType type = MediaType.parse("text/plain");
        //1.创建RequestBody对象
        //2.创建请求对象
        final Request request = new Request.Builder()
                .url(DefaultAddress.DEFAULT_ADDRESS + "")
                .get()
                .build();
        //3.创建call对象
        Call call = okHttpClient.newCall(request);
        //4.提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("qzz","请求失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                if (result != null) {
                    Gson gson = new Gson();
                    releaseList = gson.fromJson(result,new TypeToken<List<Release>>(){}.getType());
                    Message message = myHandler.obtainMessage();
                    message.what = 1;
                    message.obj = releaseList;
                    myHandler.sendMessage(message);
                }
            }
        });
    }


    private void downLoadImgFronServerRequest(){
        List<Bitmap> bitmaps = new ArrayList<>();
        //使线程有执行顺序
        final CountDownLatch latch = new CountDownLatch(releaseList.size());
        for(final Release release : releaseList){
            Request request = new Request.Builder()
                    .url(DefaultAddress.DEFAULT_ADDRESS + "")
                    .build();
            //3.创建call对象
            Call call = okHttpClient.newCall(request);
            //4.发起异步请求并获取响应
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("qzz","请求图片失败");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    //获取服务端返回的网络输入流
                    InputStream in = response.body().byteStream();
                    //第一种：获取本地文件输出流，经过流的循环读写实现图片拷贝
                    //第二种：通过BitmapFactory将输入流解析成一个Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    release.setHead(bitmap);
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
































