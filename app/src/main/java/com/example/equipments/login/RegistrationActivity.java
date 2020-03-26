package com.example.equipments.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;

public class RegistrationActivity extends BaseActivity {

    Button btnRegister;
    EditText etUsername, etPassword;
    ImageView ivRegisterLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        }


        btnRegister = (Button) findViewById(R.id.btnRegistration);
        etPassword = (EditText) findViewById(R.id.etPasswordRegister);
        etUsername = (EditText) findViewById(R.id.etUsernameRegister);
        ivRegisterLogo = (ImageView)findViewById(R.id.ivRegistrationLogo);

        ivRegisterLogo.bringToFront();  //To overlap Image on to another View

      //  onBackPressed();
    }
}
