package net.onest.moment.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import net.onest.moment.R;

/*zx：设置界面*/
public class SettingActivity extends AppCompatActivity {

    private ToggleButton buttonOff;
    private ToggleButton buttonOn;
    private ImageView ivSetBack;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //默认设置夜间模式
        setContentView(R.layout.zx_activity_setting);

        buttonOn = findViewById(R.id.switch1);
        btnBack = findViewById(R.id.btn_back);
    }


    public void onclick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.switch1:
                buttonOn.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
                break;
        }
    }


    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                //开
                Log.e("默认模式","夜间");
//                setTheme(R.style.MyTheme);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                //关
                Log.e("默认模式","白色");
                setTheme(R.style.AppTheme);
//                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                getDelegate().setLocalNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                recreate();

            }
        }
    }

}
