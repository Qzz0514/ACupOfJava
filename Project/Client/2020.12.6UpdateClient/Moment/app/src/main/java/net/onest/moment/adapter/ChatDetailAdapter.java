package net.onest.moment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.onest.moment.R;
import net.onest.moment.entity.ChatDetail;

import java.util.List;

/*qzz：聊天详情界面Adapter*/
public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.ViewHolder> {

    private Context context;
    private List<ChatDetail> chatDetailList;
    public ChatDetailAdapter(List<ChatDetail> chatDetailList) {
        this.chatDetailList = chatDetailList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatdetail_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ChatDetailAdapter.ViewHolder holder, int position) {
        ChatDetail chatDetail = chatDetailList.get(position);
        if(chatDetail.getType() == ChatDetail.RECEIVED){
            holder.rlLeft.setVisibility(View.VISIBLE);
            holder.rlRight.setVisibility(View.GONE);
            holder.tvLeftChat.setText(chatDetail.getContent());
        }else if(chatDetail.getType() == chatDetail.SEND){
            holder.rlLeft.setVisibility(View.GONE);
            holder.rlRight.setVisibility(View.VISIBLE);
            holder.tvRightChat.setText(chatDetail.getContent());
        }
    }


    @Override
    public int getItemCount() {
        return chatDetailList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlLeft;
        RelativeLayout rlRight;
        TextView tvLeftChat;
        TextView tvRightChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlLeft = itemView.findViewById(R.id.rl_left);
            rlRight = itemView.findViewById(R.id.rl_right);
            tvLeftChat = itemView.findViewById(R.id.tv_leftchat);
            tvRightChat = itemView.findViewById(R.id.tv_rightchat);
        }
    }

}
