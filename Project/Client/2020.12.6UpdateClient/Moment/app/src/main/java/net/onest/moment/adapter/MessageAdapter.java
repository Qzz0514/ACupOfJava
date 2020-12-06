package net.onest.moment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.onest.moment.R;
import net.onest.moment.entity.Message;
import net.onest.moment.entity.Shop;

import java.util.List;

/*qzz：消息界面列表Adapter*/
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Shop> shops;
    private int itemlayoutRes;


    private int resourceId;

    public MessageAdapter(Context context, List<Shop> shops, int itemlayoutRes) {
        this.context = context;
        this.shops = shops;
        this.itemlayoutRes = itemlayoutRes;

    }

    /**
     * 获得数据条数
     * @return
     */
    @Override
    public int getCount() {
        if(null != shops){
            return shops.size();
        }
        return 0;
    }

    /**
     * 获取item显示的数据对象
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        if(null != shops){
            return shops.get(i);
        }
        return null;
    }

    /**
     * 获取item的id值
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(null == convertView){
            //加载布局
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemlayoutRes,null);
        }
        Shop shop = shops.get(position);
        ImageView mHead = convertView.findViewById(R.id.iv_head);
        TextView mChecked = convertView.findViewById(R.id.tv_checked);
        TextView mTitle = convertView.findViewById(R.id.tv_mtitle);

        mHead.setImageBitmap(shop.getBitmap());
        //mChecked.setText(message.getCheckedMessage());
        mTitle.setText(shop.getName());

        return convertView;
    }
}
