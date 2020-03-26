package com.example.equipments.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity {

    ImageView ivForgotPasswordLogo;
    EditText etEmail;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ivForgotPasswordLogo = (ImageView)findViewById(R.id.ivForgotPasswordLogo);
        etEmail = (EditText)findViewById(R.id.etEmail);
        btnSend = (Button)findViewById(R.id.btnSend);

        ivForgotPasswordLogo.bringToFront();  //To overlap Image on to another View

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        }

    }
}
