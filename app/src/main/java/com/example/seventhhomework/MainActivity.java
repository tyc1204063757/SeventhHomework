package com.example.seventhhomework;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.seventhhomework.RecyclerView.Item;
import com.example.seventhhomework.RecyclerView.MyRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Dust_it_off";
    private int Times;
    private List<Item> itemList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("网络请求加JSON解析");
        toolbar.setSubtitle("Dust_it_off");
        setSupportActionBar(toolbar);

        if(Build.VERSION.SDK_INT >= 21){
            View view = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
            view.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Times = 1;
        initItem();

    }

    public void initItem(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                URL url = null;
                BufferedReader reader = null;

                try {

                    url = new URL("http://gank.io/api/data/Android/300/1");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream is = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        sb.append(line);
                    }

                    parseJSONWithJSONObject(sb.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    public void parseJSONWithJSONObject(String jsonData){

        try {

            JSONObject obj_0 = new JSONObject(jsonData);
            JSONArray jsonArray = obj_0.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj_1 = jsonArray.getJSONObject(i);

                if (obj_1.getString("used").equals("true")) {

                    Item item = new Item();
                    item.setWho(obj_1.getString("who"));
                    item.setDesc(obj_1.getString("desc"));
                    item.setPublishedAt(obj_1.getString("publishedAt"));
                    item.setUri(obj_1.getString("url"));





                      if(obj_1.has("images") && !(obj_1.getJSONArray("images").isNull(0)  )){
                          Log.d(TAG,"TAT");
                          Log.d(TAG,obj_1.getJSONArray("images").getString(0));
                          Log.d(TAG,"QAQ");
                          item.setImageUri(obj_1.getJSONArray("images").getString(0));
                    }
                     itemList.add(item);


                    Log.d(TAG, itemList.get(i).getWho() + "               " + itemList.get(i).getDesc() + "         " + itemList.get(i).getPublishedAt());

                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);


            }

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }


        @SuppressLint("HandlerLeak")
        public Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:


                        RecyclerView recyclerView = findViewById(R.id.recycler_view);

                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);

                        if (Times == 1) {
                            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                            Times++;
                        }


                        recyclerView.setLayoutManager(manager);

                        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(itemList,MainActivity.this);
                        recyclerView.setAdapter(adapter);
                }
            }
        };

}
