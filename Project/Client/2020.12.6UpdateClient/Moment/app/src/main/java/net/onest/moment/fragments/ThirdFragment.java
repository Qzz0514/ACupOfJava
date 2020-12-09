package net.onest.moment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.onest.moment.R;

/*qzz：发布Fragment*/
public class ThirdFragment extends Fragment {
    private TextView tvCon;
    private final int RESOUT = 0;
    private LinearLayout root ;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载内容页面的布局文件（将内容页面的XML布局文件转成View类型的对象）
        View view = inflater.inflate(R.layout.fragment_layout,//内容页面的布局文件
                container,//根视图对象
                false);//false表示需要手动调用addView方法将view添加到container
        //true表示不需要手动调用addView方法
        tvCon = view.findViewById(R.id.tv_content);
        tvCon.setText("这是第一个");
        tvCon.setTextColor(R.color.color_nav);
        root = view.findViewById(R.id.test);
        return view;
    }
}
