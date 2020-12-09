package net.onest.moment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.onest.moment.R;
import net.onest.moment.entity.Release;

import java.util.List;

/*zx：发布页列表*/
public class ReleaseAdapter extends BaseAdapter {

    private Context context;
    private List<Release> releaseList;
    private int itemlayoutRes;

    private int resourceId;

    public ReleaseAdapter(Context context, List<Release> releaseList, int itemlayoutRes) {
        this.context = context;
        this.releaseList = releaseList;
        this.itemlayoutRes = itemlayoutRes;
    }

    //获得数据条数
    @Override
    public int getCount() {
        if (releaseList != null) {
            return releaseList.size();
        }
        return 0;
    }

    //获取item显示的数据对象
    @Override
    public Object getItem(int i) {
        if (releaseList != null) {
            return releaseList.get(i);
        }
        return null;
    }

    //获取item的id值
    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            //加载布局
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemlayoutRes,null);
        }
        Release release = releaseList.get(position);
        ImageView userhead = convertView.findViewById(R.id.iv_userhead);
        TextView username = convertView.findViewById(R.id.tv_username);
        TextView content = convertView.findViewById(R.id.tv_content);
        ImageView pyqImg = convertView.findViewById(R.id.iv_pyqimg);
        TextView tvtime = convertView.findViewById(R.id.tv_time);

        userhead.setImageBitmap(release.getHead());
        username.setText(release.getUserName());
        content.setText(release.getMessage());
        pyqImg.setImageBitmap(release.getShareImg());
        tvtime.setText(release.getShareTime());

        return convertView;
    }

}
































