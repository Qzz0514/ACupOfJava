package yalantis.com.sidemenu.sample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.entity.Cake;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private Context context;
    private List<Cake> cakes = new ArrayList<>();
    private int itemLayoutRes;

    public MyAdapter(Context context, List<Cake> cakes, int itemLayoutRes) {
        this.context = context;
        this.cakes = cakes;
        this.itemLayoutRes = itemLayoutRes;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemLayoutRes, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tv_shop_name.setText(cakes.get(position).getcName());
        holder.iv_shop_img.setImageResource(R.drawable.ic_launcher);

        holder.tv_shop_price.setText(cakes.get(position).getcPrice()+"");
        holder.tv_shop_size.setText(cakes.get(position).getcSize()+"");
        holder.tv_shop_step.setText(cakes.get(position).getcStep()+"");
        holder.tv_shop_star.setText(cakes.get(position).getcStar()+"");
        holder.tv_shop_description.setText(cakes.get(position).getcDescription());


    }


    @Override
    public int getItemCount() {
        return cakes.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tv_shop_name;
        ImageView iv_shop_img;
        TextView tv_shop_price;
        TextView tv_shop_size;
        TextView tv_shop_step;
        TextView tv_shop_star;
        TextView tv_shop_description;


        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            iv_shop_img = itemView.findViewById(R.id.iv_shop_img);
            tv_shop_price = itemView.findViewById(R.id.tv_shop_price);
            tv_shop_size = itemView.findViewById(R.id.tv_shop_size);
            tv_shop_step = itemView.findViewById(R.id.tv_shop_step);
            tv_shop_star = itemView.findViewById(R.id.tv_shop_star);
            tv_shop_description = itemView.findViewById(R.id.tv_shop_description);

        }
    }
}
