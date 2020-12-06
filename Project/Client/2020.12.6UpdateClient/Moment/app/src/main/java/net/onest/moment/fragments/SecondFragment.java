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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*sxh：活动Fragment*/
public class SecondFragment extends Fragment {

    private OkHttpClient okHttpClient;
    private Spinner mCitiesSpinner = null;
    private Spinner mDistanceSpinner = null;
    private TextView star;
    private static Context mContext = null;
    private List<Shop> lists;
    private ListView listView;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    final List<Shop> shops = (List<Shop>) msg.obj;
                    ShopAdapter adapter = new ShopAdapter(getContext(), shops, R.layout.sxh_seconedfragment_item);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Shop shop = shops.get(position);
                            Intent intent = new Intent(getContext(), Sale.class);
                            intent.putExtra("shopId", shop.getShopId() + "");
                            intent.putExtra("shopName", shop.getName());
                            intent.putExtra("shopImage", shop.getImage());
                            startActivity(intent);
                        }
                    });
                    listView.setAdapter(adapter);
                    for (Shop shop : lists
                    ) {
                        Log.e("handleMessage: ", shop.getBitmap()+"");
                    }
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.sxh_second_fragment, null);

        star = view.findViewById(R.id.star);
        mContext = getActivity();
        mCitiesSpinner = (Spinner) view.findViewById(R.id.spin_one);
        mDistanceSpinner = (Spinner) view.findViewById(R.id.spin_two);

        String[] arr1 = {"全部城市", "石家庄", "北京", "上海"};
        setCursListener(arr1, 0, 1);
        String[] arr2 = {"1km以内", "1-3km", "3-5km", "5km以外"};
        setCursListener(arr2, 0, 2);
        String[] arr3 = {"小于10元/小时","10-30元/小时","30-80元/小时","大于80元/小时"};
        setCursListener(arr3,0,3);

        listView = view.findViewById(R.id.activities);
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
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ServerConfig.SERVER_HOME + "shop/find")
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
                    Log.e("Bitmap",bitmap.toString() );
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


    // 初始化完了后改变 Spinner文字标题颜色
    private void setCursListener(String[] curs, int select, int monitor) {
        View view = View.inflate(getActivity(), R.layout.sxh_second_fragment, null);
        Spinner spinner = (Spinner)view.findViewById(R.id.spin_one);
        Spinner spinner1 = (Spinner)view.findViewById(R.id.spin_two);
        Spinner spinner2 = (Spinner)view.findViewById(R.id.spin_three);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, curs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(select);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(select);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(select);
        switch (monitor){
            case 1:
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                        TextView tv = (TextView)view;
                        tv.setTextColor(getResources().getColor(R.color.color_white));    //设置颜色
                        tv.setTextSize(15.0f);    //设置大小
                        tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                        Log.e("1234","12315125151515");
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent){}
                });
            case 2:
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                        TextView tv1 = (TextView)view;
                        tv1.setTextColor(getResources().getColor(R.color.color_white));    //设置颜色
                        tv1.setTextSize(15.0f);    //设置大小
                        tv1.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            case 3:
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                        TextView tv2 = (TextView)view;
                        tv2.setTextColor(getResources().getColor(R.color.color_white));    //设置颜色
                        tv2.setTextSize(15.0f);    //设置大小
                        tv2.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
        }}


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String content = parent.getItemAtPosition(position).toString();
        switch (parent.getId()){
            case R.id.spin_one:
                Toast.makeText(SecondFragment.mContext = getActivity(),"选择的城市是:" + content,Toast.LENGTH_SHORT).show();
                break;
            case R.id.spin_two:
                Toast.makeText(SecondFragment.mContext = getActivity(),"选择的距离是:" + content,Toast.LENGTH_SHORT).show();
                break;
            case R.id.spin_three:
                Toast.makeText(SecondFragment.mContext = getActivity(),"选择的价格是:" + content,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}