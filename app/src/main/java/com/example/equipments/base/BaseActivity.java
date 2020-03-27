package com.example.equipments.base;;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.equipments.R;
import com.example.equipments.product.ProductListRecyclerViewAdapter;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BaseActivity extends AppCompatActivity implements Constant {

    public static int sampleTestingLimit = 200;
    Toolbar toolbar;
    public static AlertDialog progressDialog;
    protected SharedPreferences sharedPreferences;
    public static String session_id = "";
    protected Integer new_request_timeout = 30000;//DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    protected Integer new_request_max_retry = 5;//DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
    protected JSONObject requestParams = new JSONObject();
    public RequestQueue mRequestQueue;
    public ImageLoader mImageLoader;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    RecycleViewAdapter();
    }


    /**
     * will read string data from application's sharedPreferences
     *
     * @param key read the string data with key
     * @return
     */
    public String getSharedPreferencesValue(String key) {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right);
        closeActivityWithAnimation();
    }

    public void openActivity(Activity activityName, Class className) {
        startActivity(new Intent(activityName, className));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right);

        progressDialog = new ProgressDialog(BaseActivity.this);
        sharedPreferences = getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);

        //below line to avoid auto screen lock while app is open
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * open new activity with close previous activity
     *
     * @param activity
     * @param className
     */
    public void openActivityWithClosePrevious(Activity activity, Class className) {
        finish();
        startActivity(new Intent(activity, className));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_in_right);
    }


    /**
     * close current screen with animation
     */
    public void closeActivityWithAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finish();
        }
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_in_left);
        callGC();
    }

    /**
     * forcefully calling garbage collector of java
     * to clean unused memory
     */
    public void callGC() {
        System.gc();
        Runtime.getRuntime().gc();
    }

    /**
     * show toast message with long duration of time
     *
     * @param message
     */
    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
