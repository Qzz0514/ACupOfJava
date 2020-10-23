package yalantis.com.sidemenu.sample.Adapter;

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

public class CakeAdapter extends BaseAdapter {
    private Context context;
    private List<Cake> cakes = new ArrayList<>();
    private int itemLayoutRes;

    public CakeAdapter(Context context, List<Cake> cakes, int itemLayoutRes) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(itemLayoutRes, null);
        TextView cakeName = convertView.findViewById(R.id.tv_name);
        ImageView cakeImg = convertView.findViewById(R.id.iv_img);
        TextView cakePrice = convertView.findViewById(R.id.tv_price);
        cakeName.setText(cakes.get(position).getcName());
        cakePrice.setText(cakes.get(position).getcPrice()+"");

        return convertView;
    }

}
