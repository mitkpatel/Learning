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
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.equipments.R;
import com.example.equipments.login.SplashActivity;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

public class BaseActivity extends AppCompatActivity implements Constant {

    public int sampleTestingLimit = 30;
    public static AlertDialog progressDialog;
    protected SharedPreferences sharedPreferences;
    public static String session_id = "";
    protected Integer new_request_timeout = 30000;//DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    protected Integer new_request_max_retry = 5;//DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
    protected JSONObject requestParams = new JSONObject();
    public RequestQueue mRequestQueue;
    public ImageLoader mImageLoader;
    public int orientation;
    private int checkedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    RecycleViewAdapter();
        orientation = getResources().getConfiguration().orientation;
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

    //Back Press method with confirmation dialog
    public void onBackPressed(Activity activity) {
        logoutFromApp(activity);
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
     * ask user confirmation for logout from the odoo application
     *
     * @param context
     */

    public void logoutFromApp(final Context context) {
        new AlertDialog.Builder(BaseActivity.this)
                .setMessage(getString(R.string.dialog_logout_confirm))
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout(context);
                    }
                }).setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void logout(Context context) {
//        clearSharedPreferences();
        startActivity(new Intent(context, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_in_left);
        finish();
    }

    /**
     * clear all values from local memory of application
     */
    public void clearSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
        }
        Map<String, ?> allEntries = sharedPreferences.getAll();
        if (allEntries.containsKey(PREF_USERNAME)) {
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                if (entry.getKey().equalsIgnoreCase(PREF_USERNAME) ||
                        entry.getKey().equalsIgnoreCase(PREF_PASSWORD) ||
                        entry.getKey().equalsIgnoreCase(PREF_SERVER_URL_IP) ||
                        entry.getKey().equalsIgnoreCase(PREF_SERVER_URL_PORT) ||
                        entry.getKey().equalsIgnoreCase(PREF_DB_NAME)) {
                    continue;
                } else {
                    Log.d("map values", entry.getKey() + ": Removed");
                    sharedPreferences.edit().remove(entry.getKey()).commit();
                }
            }
        } else {
            sharedPreferences.edit().clear().commit();
        }
    }

    /**
     * To change the application language dialog
     */
    protected void showChangeLanguageDialog() {

        final String[] languageList = {"ગુજરાતી", "हिन्दी", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("Choose Language...");

        builder.setSingleChoiceItems(languageList, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (languageList[i].equals("ગુજરાતી")) {
                    //Gujarati
                    setLanguage("gu");
                    recreate();
                    checkedItem = 0;
                } else if (languageList[i].equals("हिन्दी")) {
                    //Hindi
                    setLanguage("hi");
                    recreate();
                    checkedItem = 1;
                } else if (languageList[i].equals("English")) {
                    //English
                    setLanguage("en");
                    recreate();
                    checkedItem = 2;
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
