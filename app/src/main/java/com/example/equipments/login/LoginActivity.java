package com.example.equipments.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;
import com.example.equipments.product.ProductListActivity;

import java.util.Locale;

public class LoginActivity extends BaseActivity {

    Button btnLogin,btnChangeLanguage;
    EditText etUsername, etPassword;
    ImageView ivLoginLogo;
    TextView tvCreateAccount,tvForgotPassword,tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        ivLoginLogo = (ImageView)findViewById(R.id.ivLoginLogo);
        tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);
        tvForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        btnChangeLanguage = (Button) findViewById(R.id.btnChangeLanguage);

        ivLoginLogo.bringToFront();  //To overlap Image on to another View

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.login_activity));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                openActivity(LoginActivity.this, ProductListActivity.class);
            }
        });

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(LoginActivity.this, RegistrationActivity.class);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(LoginActivity.this, ForgotPasswordActivity.class);
            }
        });

        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
    }

}
