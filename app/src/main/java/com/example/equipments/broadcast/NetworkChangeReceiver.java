package com.example.equipments.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.equipments.base.BaseActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() ||
                mobile != null && mobile.isConnectedOrConnecting();
        if (isConnected) {
            Log.d("Network Available ", "YES");
            Toast.makeText(context,"Connected to network!!!",Toast.LENGTH_LONG).show();
            if(BaseActivity.networkServiceDialog.isShowing()){
                BaseActivity.networkServiceDialog.dismiss();
            }
        } else {
            Log.d("Network Available ", "NO");
            Toast.makeText(context,"Not connected to Internet!!!",Toast.LENGTH_LONG).show();
            BaseActivity.networkServiceDialog.show();    //call dialog when internet gone
        }
    }
}