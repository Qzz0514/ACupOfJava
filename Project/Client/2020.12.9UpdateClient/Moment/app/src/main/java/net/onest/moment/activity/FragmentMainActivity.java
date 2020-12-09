package net.onest.moment.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import net.onest.moment.R;
import net.onest.moment.fragments.FifthFragment;
import net.onest.moment.fragments.FirstFragment;
import net.onest.moment.fragments.FourthFragment;
import net.onest.moment.fragments.SecondFragment;
import net.onest.moment.fragments.ThirdFragment;

import java.util.HashMap;
import java.util.Map;

/*zx：主页总布局*/
public class FragmentMainActivity extends AppCompatActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zx_fragment_activity_main);
        getFragmentTabHost();
    }


    private void getFragmentTabHost(){
        //获取FragmentTabHost的引用
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);

        //初始化
        fragmentTabHost.setup(this,
                getSupportFragmentManager(), //管理多个Fragment对象的管理器
                android.R.id.tabcontent); //显示内容页面的控件的id

        //创建内容页面TabSpec对象
        //主页
        TabHost.TabSpec firstTab = fragmentTabHost.newTabSpec("firstTab").setIndicator(getTabSpecView("firstTab","主页", R.drawable.nav_main));
        fragmentTabHost.addTab(firstTab, FirstFragment.class,null);
        //活动
        TabHost.TabSpec SecondTab = fragmentTabHost.newTabSpec("secondTab").setIndicator(getTabSpecView("secondTab","活动",R.drawable.nav_activity));
        fragmentTabHost.addTab(SecondTab, SecondFragment.class,null);
        //发布
        TabHost.TabSpec ThirdTab = fragmentTabHost.newTabSpec("thirdTab").setIndicator(getTabSpecView("thirdTab","发布",R.drawable.nav_release));
        fragmentTabHost.addTab(ThirdTab, ThirdFragment.class,null);
        //消息
        TabHost.TabSpec FourthTab = fragmentTabHost.newTabSpec("fourthTab").setIndicator(getTabSpecView("fourthTab","消息",R.drawable.nav_chat));
        fragmentTabHost.addTab(FourthTab, FourthFragment.class,null);
        //我的
        TabHost.TabSpec FifthTab = fragmentTabHost.newTabSpec("fifthTab").setIndicator(getTabSpecView("fifthTab","我的",R.drawable.nav_mine));
        fragmentTabHost.addTab(FifthTab, FifthFragment.class,null);

        //处理fragmentTabHost的选项切换事件
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onTabChanged(String tabId) {
                //修改图片和文字颜色
                switch (tabId){
                    case "firstTab":
                        imageViewMap.get("firstTab").setImageResource(R.drawable.nav_main1);
                        textViewMap.get("firstTab").setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                        imageViewMap.get("secondTab").setImageResource(R.drawable.nav_activity);
                        textViewMap.get("secondTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("thirdTab").setImageResource(R.drawable.nav_release);
                        textViewMap.get("thirdTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fourthTab").setImageResource(R.drawable.nav_chat);
                        textViewMap.get("fourthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fifthTab").setImageResource(R.drawable.nav_mine);
                        textViewMap.get("fifthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        break;
                    case "secondTab":
                        imageViewMap.get("firstTab").setImageResource(R.drawable.nav_main);
                        textViewMap.get("firstTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("secondTab").setImageResource(R.drawable.nav_activity1);
                        textViewMap.get("secondTab").setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                        imageViewMap.get("thirdTab").setImageResource(R.drawable.nav_release);
                        textViewMap.get("thirdTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fourthTab").setImageResource(R.drawable.nav_chat);
                        textViewMap.get("fourthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fifthTab").setImageResource(R.drawable.nav_mine);
                        textViewMap.get("fifthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        break;
                    case "thirdTab":
                        imageViewMap.get("firstTab").setImageResource(R.drawable.nav_main);
                        textViewMap.get("firstTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("secondTab").setImageResource(R.drawable.nav_activity);
                        textViewMap.get("secondTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("thirdTab").setImageResource(R.drawable.nav_release1);
                        textViewMap.get("thirdTab").setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                        imageViewMap.get("fourthTab").setImageResource(R.drawable.nav_chat);
                        textViewMap.get("fourthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fifthTab").setImageResource(R.drawable.nav_mine);
                        textViewMap.get("fifthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        break;
                    case "fourthTab":
                        imageViewMap.get("firstTab").setImageResource(R.drawable.nav_main);
                        textViewMap.get("firstTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("secondTab").setImageResource(R.drawable.nav_activity);
                        textViewMap.get("secondTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("thirdTab").setImageResource(R.drawable.nav_release);
                        textViewMap.get("thirdTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fourthTab").setImageResource(R.drawable.nav_chat1);
                        textViewMap.get("fourthTab").setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                        imageViewMap.get("fifthTab").setImageResource(R.drawable.nav_mine);
                        textViewMap.get("fifthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        break;
                    case "fifthTab":
                        imageViewMap.get("firstTab").setImageResource(R.drawable.nav_main);
                        textViewMap.get("firstTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("secondTab").setImageResource(R.drawable.nav_activity);
                        textViewMap.get("secondTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("thirdTab").setImageResource(R.drawable.nav_release);
                        textViewMap.get("thirdTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fourthTab").setImageResource(R.drawable.nav_chat);
                        textViewMap.get("fourthTab").setTextColor(getResources().getColor(R.color.color_nav));
                        imageViewMap.get("fifthTab").setImageResource(R.drawable.nav_mine1);
                        textViewMap.get("fifthTab").setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                        break;
                }
            }
        });

        //设置默认选项中标签页
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("firstTab").setImageResource(R.drawable.nav_main1);
        textViewMap.get("firstTab").setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        textViewMap.get("secondTab").setTextColor(getResources().getColor(R.color.color_nav));
        textViewMap.get("thirdTab").setTextColor(getResources().getColor(R.color.color_nav));
        textViewMap.get("fourthTab").setTextColor(getResources().getColor(R.color.color_nav));
        textViewMap.get("fifthTab").setTextColor(getResources().getColor(R.color.color_nav));
    }


    public View getTabSpecView(String tag,String title,int drawable){
        View view = getLayoutInflater().inflate(R.layout.zx_tab_spec_layout,null);

        //获取tab_spec_layout的布局中控件引用
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);
        imageViewMap.put(tag,icon); //将ImageView对象存储到Map中

        TextView tvTitle = view.findViewById(R.id.title);
        tvTitle.setText(title);
        textViewMap.put(tag,tvTitle);

        return view;
    }

}
