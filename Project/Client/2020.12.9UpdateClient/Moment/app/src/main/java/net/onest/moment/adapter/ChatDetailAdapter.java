package net.onest.moment.adapter;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.onest.moment.R;
import net.onest.moment.entity.ChatDetail;
import net.onest.moment.manager.DefaultAddress;
import net.onest.moment.manager.DefaultAttribute;

import java.util.List;

/*qzz：聊天详情界面Adapter*/
public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.ViewHolder> {

    private Context context;
    private List<ChatDetail> chatDetailList;

    public ChatDetailAdapter(Context context, List<ChatDetail> chatDetailList) {
        this.context = context;
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
            Glide.with(context).load(DefaultAddress.DEFAULT_ADDRESS + "shop/receive?image=" + DefaultAttribute.DEFAULT_SHOPHEADSTRING).into(holder.sellerhead);
        }else if(chatDetail.getType() == chatDetail.SEND){
            holder.rlLeft.setVisibility(View.GONE);
            holder.rlRight.setVisibility(View.VISIBLE);
            holder.tvRightChat.setText(chatDetail.getContent());
            holder.userhead.setImageBitmap(DefaultAttribute.DEFAULT_userHEAD);
        }
    }


    @Override
    public int getItemCount() {
        return chatDetailList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView userhead;
        private ImageView sellerhead;
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
            userhead = itemView.findViewById(R.id.iv_my);
            sellerhead = itemView.findViewById(R.id.iv_seller);

            //sellerhead.setImageBitmap(DefaultAttribute.DEFAULT_sellerHEAD);

        }
    }

}
