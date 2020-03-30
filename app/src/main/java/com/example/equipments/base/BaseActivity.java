package com.example.equipments.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.equipments.R;

import org.json.JSONObject;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements Constant {

    public int sampleTestingLimit = 20;
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
    public int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    RecycleViewAdapter();
        orientation = getResources().getConfiguration().orientation;
    }

    /**
     * set value to specified key into application's local memory
     *
     * @param key
     * @param value
     */

    public void setSharedPreferences(String key, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
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

    /**
     * show progress dialog
     *
     * @param context
     */
    public static void showProgressDialog(Context context) {
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.progress_msg));
            if (!progressDialog.isShowing())
                progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide progress bar dialog
     */
    public static void hideProgressDialog() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alert(String message) {
        if (!(BaseActivity.this).isFinishing()) {
            new AlertDialog.Builder(BaseActivity.this)
                    .setMessage(message)
                    .setOnCancelListener(null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    /**
     * show message to alert dialog
     *
     * @param message
     */
    public void alert(String message, DialogInterface.OnClickListener listener) {
        if (!(BaseActivity.this).isFinishing()) {
            new AlertDialog.Builder(BaseActivity.this)
                    .setMessage(message)
                    .setOnCancelListener(null)
                    .setCancelable(false)
                    .setPositiveButton("Ok", listener).show();
        }
    }

    public void alertWithCloseScreen(String message) {
        new AlertDialog.Builder(BaseActivity.this).setMessage(message).setOnCancelListener(null).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                closeActivityWithAnimation();
            }
        }).show();
    }


    /**
     * To change the application language dialog
     */
    protected void showChangeLanguageDialog() {

        String[] languageList = {"ગુજરાતી", "हिन्दी", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(languageList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    //Gujarati
                    setLanguage("gu");
                    recreate();
                } else if (i == 1) {
                    //Hindi
                    setLanguage("hi");
                    recreate();
                } else if (i == 2) {
                    //English
                    setLanguage("en");
                    recreate();
                }
                dialog.dismiss();
                ;
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // To set particular language
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        setSharedPreferences(PREF_LANGUAGE, lang);
    }

    public void loadLanguage() {
        String language = getSharedPreferencesValue(PREF_LANGUAGE);
        setLanguage(language);
    }

    /**
     * To recognize the device is tablet or mobile
     */

    protected boolean isTabletDevice() {
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        boolean isScreenXlarge = (screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        return (isScreenXlarge);
    }

}
