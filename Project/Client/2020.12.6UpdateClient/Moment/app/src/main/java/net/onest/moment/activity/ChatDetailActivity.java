package net.onest.moment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.onest.moment.adapter.ChatDetailAdapter;
import net.onest.moment.entity.ChatDetail;
import net.onest.moment.fragments.FourthFragment;
import net.onest.moment.manager.DefaultAttribute;
import net.onest.moment.manager.JWebSocketClient;
import net.onest.moment.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/*qzz：用户商家聊天详情界面*/
public class ChatDetailActivity extends AppCompatActivity{

    private List<ChatDetail> chatDetailList = new ArrayList<>();
    private RecyclerView chatDetailRecyclerView;
    private ChatDetailAdapter adapter;

    private Button btnBack;
    private EditText etInput;
    private Button btnSend;
    private String content;
    private ImageView img_head;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(ChatDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetail);
        img_head = findViewById(R.id.iv_head);
        Intent intent = getIntent();

        img_head.setImageBitmap((Bitmap) intent.getParcelableExtra("bitmap"));
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this, FourthFragment.class);
                startActivity(intent);
            }
        });

        etInput = findViewById(R.id.et_input);
        btnSend = findViewById(R.id.btn_send);
        chatDetailRecyclerView = findViewById(R.id.rv_chatList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatDetailRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ChatDetailAdapter(chatDetailList);
        chatDetailRecyclerView.setAdapter(adapter);

        URI uri = URI.create("ws://123.57.63.212:8080/yike/websocket/2");
        final JWebSocketClient client = new JWebSocketClient(uri){
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                super.onMessage(message);
                Log.e("JWebSClientService",message);
                Message msg = handler.obtainMessage();
                msg.obj = message;
                handler.sendMessage(msg);
                ChatDetail chatDetail1 = new ChatDetail(msg.obj.toString(),ChatDetail.RECEIVED);
                chatDetailList.add(chatDetail1);
            }
        };


        try {
            boolean b = client.connectBlocking();
            System.out.println("connectBlocking先连接再发送------执行结果:" + b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = etInput.getText().toString();
                //发送信息的方法
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("send_id", DefaultAttribute.DEFAULT_userID + ""); //用户id
                    jsonObject.put("target_id",1); //商家id
                    jsonObject.put("message",content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(client != null && client.isOpen() && !"".contentEquals(content)){
                    ChatDetail chatDetail1 = new ChatDetail(content,ChatDetail.SEND);
                    chatDetailList.add(chatDetail1);
                    client.send(jsonObject.toString());
                    adapter.notifyItemInserted(chatDetailList.size()-1); //当有新消息时，刷新显示
                    chatDetailRecyclerView.scrollToPosition(chatDetailList.size()-1); //将RecyclerView定位到最后一行
                    etInput.setText(""); //清空输入框
                }
            }
        });

    }




}
