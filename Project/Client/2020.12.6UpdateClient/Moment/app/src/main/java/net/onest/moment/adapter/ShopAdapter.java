package net.onest.moment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.onest.moment.R;
import net.onest.moment.entity.Shop;

import java.util.List;

/*sxh：活动页Adapter*/
public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<Shop> shops;
    private int itemLayoutRes;
    private ImageView ziimg;
    private TextView name;
    private TextView starttime;
    private TextView location;
    private TextView endtime;
    private TextView stars;
    private TextView likes;


    public ShopAdapter(Context context, List<Shop> shops, int itemLayoutRes) {
        this.context = context;
        this.shops = shops;
        this.itemLayoutRes = itemLayoutRes;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(null == view){
            //加载布局
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(itemLayoutRes,null);
        }
        //获取控件引用
        ziimg = view.findViewById(R.id.store_img);
        name=view.findViewById(R.id.store_name);
        starttime=view.findViewById(R.id.time);
        endtime=view.findViewById(R.id.end_time);
        location=view.findViewById(R.id.location);
        stars=view.findViewById(R.id.star);
        likes=view.findViewById(R.id.collect);
        //设置控件内容
        Shop shop = shops.get(i);
        name.setText(shop.getName());
        starttime.setText(shop.getStarttime());
        endtime.setText(shop.getEndtime());
        location.setText(shop.getLocation());
        stars.setText(shop.getStars()+"");
        likes.setText(shop.getLikes()+"");

        ziimg.setImageBitmap(shop.getBitmap());
        return view;
    }
}
