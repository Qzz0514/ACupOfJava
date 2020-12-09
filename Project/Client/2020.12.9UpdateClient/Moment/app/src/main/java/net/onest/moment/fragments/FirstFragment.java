package net.onest.moment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.onest.moment.BaiduMap;
import net.onest.moment.R;
import net.onest.moment.entity.User;
import net.onest.moment.manager.DefaultAttribute;
import net.onest.moment.manager.ServerConfig;
import net.onest.moment.manager.ZQImageViewRoundOval;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*sq：主页Fragment*/
public class FirstFragment extends Fragment {
    private ImageView imageView;
    private TextView userName;
    private ZQImageViewRoundOval zqImageViewRoundOval;
    private Map<String,TextView> textViewMap =new HashMap<String,TextView>();


//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 3:
//                    User user = (User) msg.obj;
//                    userName.setText(user.getName()+"");
//                    String url = ServerConfig.SERVER_HOME + "user/uploadUserImage?image=" + user.getImage();
//                    Glide.with(getContext()).load(url).into(zqImageViewRoundOval);
//
//                    break;
//            }
//        }
//    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sq_hot_main, container,false);

        zqImageViewRoundOval = view.findViewById(R.id.touxiang);
        imageView = view.findViewById(R.id.dingwei);
        userName = view.findViewById(R.id.name1);
        Glide.with(this).asBitmap().load(DefaultAttribute.DEFAULT_userHEAD).into(zqImageViewRoundOval);
        userName.setText(DefaultAttribute.DEFAULT_userNAME);

        //获取FragmentTabHost的引用
        FragmentTabHost fragmentTabHost = view.findViewById(android.R.id.tabhost);
        //初始化
        fragmentTabHost.setup(getContext(),getChildFragmentManager()
                ,//管理多个Fragment对象的管理器
                android.R.id.tabcontent);//显示内容页面的控件的id

        //创建内容页面TabSpec对象
        TabHost.TabSpec tab1 = fragmentTabHost.newTabSpec("first_tab")
                .setIndicator(getTabSpecView("first_tab", "热门"));

        //Class参数：类名.class,对象.getClass()
        fragmentTabHost.addTab(tab1,
                FirstFragment_sq.class,//FristFragment类的Class（字节码）对象
                null);//传递数据时使用，不需要传递数据直接传null

        TabHost.TabSpec tab2 = fragmentTabHost.newTabSpec("second_tab")
                .setIndicator(getTabSpecView("second_tab", "近期"));

        fragmentTabHost.addTab(tab2,
                SecondFragment_sq.class,//SecondFragment类的Class对象
                null);
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "first_tab":
                        textViewMap.get("first_tab").setTextSize(30);
                        textViewMap.get("second_tab").setTextSize(20);
                        break;
                    case "second_tab":
                        textViewMap.get("first_tab").setTextSize(20);
                        textViewMap.get("second_tab").setTextSize(30);
                        break;
                }
            }
        });
        fragmentTabHost.setCurrentTab(0);
        textViewMap.get("first_tab").setTextSize(30);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaiduMap.class);
                startActivity(intent);
            }
        });
    }


    public View getTabSpecView(String tag, String tilte){
        View view1 = getLayoutInflater().inflate(R.layout.sq_tab_spec,null);
        TextView tvTitle = view1.findViewById(R.id.title);
        textViewMap.put(tag,tvTitle);
        tvTitle.setText(tilte);
        return view1;
    }


}
