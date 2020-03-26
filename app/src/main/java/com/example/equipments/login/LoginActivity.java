package com.example.equipments.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;
import com.example.equipments.product.ProductListActivity;

public class LoginActivity extends BaseActivity {

    Button btnLogin;
    EditText etUsername, etPassword;
    ImageView ivLoginLogo;
    TextView tvCreateAccount,tvForgotPassword,tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        ivLoginLogo = (ImageView)findViewById(R.id.ivLoginLogo);
        tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);
        tvForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
       // tvToolbarTitle = (TextView)findViewById(R.id.tv_toolbar_title);
       // Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
    //    TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);

        ivLoginLogo.bringToFront();  //To overlap Image on to another View

        //   setSharedPreferences(PREF_SERVER_URL, ServerURL);

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

    }
}
