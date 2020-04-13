package com.ypp.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.ypp.broadcasttest.MY_BROADCAST");
                sendBroadcast(intent);
            }
        });
        //创建意图过滤器，并添加对系统网络状态广播
        intentFilter=new IntentFilter();
        //android.net.conn.CONNECTIVITY_CHANGE系统网络状态发生改变时的广播
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        //注册广播接收器
        registerReceiver(networkChangeReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //动态注册的意图广播接收器必须手动取消注册
        unregisterReceiver(networkChangeReceiver);
    }
//创建广播接收者类
    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
           // Toast.makeText(context, "NetWork Change", Toast.LENGTH_SHORT).show();
            //创建系统网络连接管理类
            ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取到系统中网络是否连通的类
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null&&networkInfo.isAvailable()){
                Toast.makeText(context, "网络连接可用", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "网络连接不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
