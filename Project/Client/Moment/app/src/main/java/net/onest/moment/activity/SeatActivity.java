package net.onest.moment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qfdqc.views.seattable.SeatTable;
import net.onest.moment.R;
import net.onest.moment.entity.Seat;
import net.onest.moment.manager.DefaultAddress;
import net.onest.moment.manager.DefaultAttribute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*qzz：预约自习座位*/
public class SeatActivity extends AppCompatActivity {

    public SeatTable seatTableView;
    public List<Seat> seatList = new ArrayList<>();
    private Button btnSelected;
    private String seatInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        seatTableView = findViewById(R.id.sv_seat);
        seatTableView.setScreenName("倒一杯Java自习室"); //设置房间名称
        seatTableView.setMaxSelected(3); //设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int col) {
                if(col == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int col) {
                if(row == 6 && col == 6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int col) { }

            @Override
            public void unCheck(int row, int col) { }

            @Override
            public String[] checkedSeatTxt(int row, int col) {
                return null;
            }
        });

        seatTableView.setData(10,15);

        btnSelected = findViewById(R.id.btn_selected);
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToSeat(DefaultAddress.DEFAULT_ADDRESS);
            }
        });

    }


    public void ToSeat(final String s){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(s + "seat/" + "occupy");
                    Log.e("url",url.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                    OutputStream outputStream = connection.getOutputStream();
                    JSONArray seats = new JSONArray();

//                    for (Seat seat : seatList) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("id",1);
                        object.put("room_id",1);
                        object.put("user_id",1);
                        seats.put(object);
//                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    seatInfo = seats.toString();
                    Log.e("seatInfo",seatInfo);
                    outputStream.write(seatInfo.getBytes());
                    InputStream inputStream = connection.getInputStream();
                    inputStream.close();
                    outputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
