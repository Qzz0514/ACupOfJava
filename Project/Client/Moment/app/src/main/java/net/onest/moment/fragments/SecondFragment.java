package net.onest.moment.fragments;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.moment.R;
import net.onest.moment.activity.Sale;
import net.onest.moment.adapter.ShopAdapter;
import net.onest.moment.entity.Shop;
import net.onest.moment.manager.ServerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*sxh：活动Fragment*/
public class SecondFragment extends Fragment {

    private OkHttpClient okHttpClient;
    private Spinner mCitiesSpinner = null;
    private Spinner mDistanceSpinner = null;
    private Spinner mPriceSpinner = null;
    private TextView star;
    private static Context mContext = null;
    private List<Shop> lists;
    private ListView listView;
    private ImageView mImageView;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    List<Shop> shops = (List<Shop>) msg.obj;
                    ShopAdapter adapter = new ShopAdapter(getContext(), shops, R.layout.sxh_seconedfragment_item);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(),Sale.class);
                            intent.putExtra("shopId", lists.get(position).getShopId() + "");
                            intent.putExtra("shopName", lists.get(position).getName());
                            intent.putExtra("shopImage", lists.get(position).getImage());
                            startActivity(intent);
                        }
                    });
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    List<Shop> shops1 = (List<Shop>) msg.obj;
                    ShopAdapter adapter1 = new ShopAdapter(getContext(), shops1, R.layout.sxh_seconedfragment_item);
                    adapter1.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(),Sale.class);
                            intent.putExtra("shopId", lists.get(position).getShopId() + "");
                            intent.putExtra("shopName", lists.get(position).getName());
                            intent.putExtra("shopImage", lists.get(position).getImage());
                            startActivity(intent);
                        }
                    });
                    listView.setAdapter(adapter1);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.sxh_second_fragment, null);

        star = view.findViewById(R.id.star);
        mContext = getActivity();
        mCitiesSpinner = view.findViewById(R.id.spin_one);

        listView = view.findViewById(R.id.activities);
        initOkHttpClient();
        simpStringParamPostRequest();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mCitiesSpinner.setAdapter(adapter);
        mCitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String selectedCity = (String) spinner.getSelectedItem();
                downloadSpinner(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }


    //下载spiner
    private void downloadSpinner(String city) {
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "shop/selectCity?location="+city)
                .get()
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lww", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                List<Shop> shops = gson.fromJson(result, new TypeToken<List<Shop>>() {
                }.getType());
                downLoadImgFromServerRequest(shops);
                Message dowmMsg = myHandler.obtainMessage();
                dowmMsg.what = 2;
                dowmMsg.obj = shops;
                myHandler.sendMessage(dowmMsg);
            }
        });
    }


    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
    }


    private void simpStringParamPostRequest() {
        //1.创建RequestBody对象
        //2.创建请求对象
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "shop/find")
                .get()
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("sxh", "请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                lists=gson.fromJson(result,new TypeToken<List<Shop>>(){}.getType());
                downLoadImgFromServerRequest(lists);
                Message dowmMsg = myHandler.obtainMessage();
                dowmMsg.what = 1;
                dowmMsg.obj = lists;
                myHandler.sendMessage(dowmMsg);
            }
        });
    }


    private void downLoadImgFromServerRequest(List<Shop> shops) {
        final CountDownLatch latch = new CountDownLatch(shops.size());
        for (final Shop shop :shops) {
            Request request = new Request.Builder()
                    .url(ServerConfig.SERVER_HOME + "shop/receive?image=" + shop.getImage()+"&user_id=2")
                    .build();
            //3. 创建CALL对象
            Call call = okHttpClient.newCall(request);
            //4. 发起异步请求并获取响应
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("sxh", "请求图片失败");
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
