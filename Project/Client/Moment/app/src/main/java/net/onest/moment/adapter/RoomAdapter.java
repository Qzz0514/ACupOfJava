package net.onest.moment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.onest.moment.R;
import net.onest.moment.entity.Room;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    private Context context;
    private List<Room> rooms;
    private int itemLayoutRes;
    private TextView roomname;
    private TextView roomprice;

    public RoomAdapter(Context context, List<Room> rooms, int itemLayoutRes) {
        this.context = context;
        this.rooms = rooms;
        this.itemLayoutRes = itemLayoutRes;
    }
    /**
     * 获得数据条数
     * @return
     */
    @Override
    public int getCount() {
        if(null != rooms){
            return rooms.size();
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
        if(null != rooms){
            return rooms.get(i);
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

        roomname=view.findViewById(R.id.tit1);
        roomprice=view.findViewById(R.id.tit2);

        roomname.setText(rooms.get(i).getName());
        roomprice.setText(rooms.get(i).getPrice()+"");

        return view;
    }
}

