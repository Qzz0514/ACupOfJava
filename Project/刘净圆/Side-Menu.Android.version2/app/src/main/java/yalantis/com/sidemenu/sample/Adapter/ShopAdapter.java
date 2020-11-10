package yalantis.com.sidemenu.sample.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.entity.Cake;

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<Cake> cakes = new ArrayList<>();
    private int itemLayoutRes;

    public ShopAdapter(Context context, List<Cake> cakes, int itemLayoutRes) {
        this.context = context;
        this.cakes = cakes;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (cakes != null) {
            return cakes.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int position) {
        if (cakes != null) {
            return cakes.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(itemLayoutRes, null);
        TextView tv_shop_name = convertView.findViewById(R.id.tv_shop_name);
        ImageView iv_shop_img = convertView.findViewById(R.id.iv_shop_img);
        TextView tv_shop_price = convertView.findViewById(R.id.tv_shop_price);
        TextView tv_shop_size = convertView.findViewById(R.id.tv_shop_size);
        TextView tv_shop_step = convertView.findViewById(R.id.tv_shop_step);
        TextView tv_shop_star = convertView.findViewById(R.id.tv_shop_star);
        TextView tv_shop_description = convertView.findViewById(R.id.tv_shop_description);

        tv_shop_name.setText(cakes.get(position).getcName());
        iv_shop_img.setImageResource(R.drawable.ic_launcher);
        tv_shop_price.setText(cakes.get(position).getcPrice()+"");
        tv_shop_size.setText(cakes.get(position).getcSize()+"");
        tv_shop_step.setText(cakes.get(position).getcStep()+"");
        tv_shop_star.setText(cakes.get(position).getcStar()+"");
        tv_shop_description.setText(cakes.get(position).getcDescription());
        return convertView;
    }
}
