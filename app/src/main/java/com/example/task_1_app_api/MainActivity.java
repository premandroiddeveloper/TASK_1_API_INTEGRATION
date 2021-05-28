package com.example.task_1_app_api;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<modelclass> ar1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView l1 = findViewById(R.id.lis);
        l1.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ar1.size();
            }

            @Override
            public Object getItem(int position) {
                return ar1.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                @SuppressLint("ViewHolder") View view=getLayoutInflater().inflate(R.layout.carditem,null);
                modelclass set=ar1.get(position);
                ImageView img1=view.findViewById(R.id.imageView);
                TextView t1=view.findViewById(R.id.textView);
                Glide.with(getApplicationContext())
                        .load(set.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img1);
                t1.setText(set.getName());
                return view;
            }
        });
        String url="https://picsum.photos/v2/list";
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray js1=new JSONArray(response);
                    for(int i=0;i<js1.length();i++){
                        JSONObject jso1=js1.getJSONObject(i);
                        modelclass md1=new modelclass();
                        md1.setImage(jso1.getString("download_url"));
                        md1.setName(jso1.getString("author"));
                        Log.i("check",jso1.getString("author"));
                        ar1.add(md1);
                        Log.i("arraycheck", String.valueOf(ar1.get(i).getName()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);




    }
}