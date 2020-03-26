package com.example.equipments.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    public static final int R_PERM = 2822;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //To hide notification bar in activity
        setContentView(R.layout.activity_splash);
        takeAllRequiredPermissions();

    }

    public void callNextActivity() {
        callGC();
        String session_id = getSharedPreferencesValue(PREF_SESSION_ID);
        if (session_id.length() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openActivityWithClosePrevious(SplashActivity.this, LoginActivity.class);
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openActivityWithClosePrevious(SplashActivity.this, LoginActivity.class);
                }
            }, 2000);
        }
    }

    public void takeAllRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkInternetPermission() || !checkReadPhoneStatePermission()
                    || !checkWriteExternalStorage() || !checkCameraPermission() ||
                    !checkCallPhonePermission() || !checkNetworkStatePermission() ||
                    !checkFineLocationPermission() || !checkCoarseLocationPermission() ||
                    !checkPhoneStatePermission()) {
                try {
                    requestPermission();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                // Move to main act
                callNextActivity();
            }
        } else {
            // Move to main act
            callNextActivity();
        }
    }

    public boolean checkFineLocationPermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPhoneStatePermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCoarseLocationPermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkInternetPermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.INTERNET);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCallPhonePermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkReadPhoneStatePermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkWriteExternalStorage() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCameraPermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNetworkStatePermission() {
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_NETWORK_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public void requestPermission() {
        ActivityCompat.requestPermissions(SplashActivity.this,
                new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                R_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case R_PERM:
                // if granted then 0 else -1
                // i have 5 permisson to check so 0,1,2,3,4..
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[5] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[6] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[7] == PackageManager.PERMISSION_GRANTED) {

                    // means all permission are granted..move to Main activity
                    callNextActivity();

                } else {
                    // show alert
                    toast(getString(R.string.toast_all_permission_req_to_start));
                    takeAllRequiredPermissions();
                }
                break;
        }
    }

}