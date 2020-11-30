package yalantis.com.sidemenu.sample.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.entity.Cake;
import yalantis.com.sidemenu.sample.entity.Order;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orders;
    private List<Cake> cakes;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private int itemLayoutRes;

    public OrderAdapter(Context context, List<Order> orders, List<Cake> cakes, int itemLayoutRes) {
        this.context = context;
        this.orders = orders;
        this.cakes = cakes;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (orders != null) {
            return orders.size();
        }
        return 0;
    }
    @Override
    public Object getItem(int position) {
        if (orders != null) {
            return orders.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(itemLayoutRes, null);
        TextView tv_order_id = convertView.findViewById(R.id.tv_order_id);
        TextView tv_order_price = convertView.findViewById(R.id.tv_order_price);
        final Button btn_fin = convertView.findViewById(R.id.btn_fin);
        recyclerView = convertView.findViewById(R.id.rv_item);
        tv_order_id.setText(orders.get(position).getoId()+"");
        tv_order_price.setText(orders.get(position).getoPrice()+"");
        btn_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_fin.setText("已完成");

            }
        });
        myAdapter = new MyAdapter(context, cakes, R.layout.shop_list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(myAdapter);


        return convertView;
    }
}
