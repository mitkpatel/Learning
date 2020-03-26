package com.example.equipments.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;
import com.example.equipments.login.LoginActivity;
import com.example.equipments.login.RegistrationActivity;

import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListActivity extends BaseActivity implements MyRecyclerViewAdapter.ItemClickListener {

    EditText etSearchProduct;
    ImageView ivSearchProduct;
    MyRecyclerViewAdapter adapter;
    ToggleButton toggleButton;

    String[] data = {"Product Name:1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    int[] product_images = {R.drawable.infra_bazaar_backhoe,R.drawable.infra_bazaar_crane,R.drawable.infra_bazaar_excavator,R.drawable.infra_bazaar_motorgrader,
            R.drawable.infra_bazaar_roller,R.drawable.infra_bazaar_tipper,R.drawable.infra_bazaar_transitmixer,R.drawable.infra_bazaar_used};

    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        etSearchProduct = (EditText)findViewById(R.id.etSearchProduct);
        ivSearchProduct = (ImageView)findViewById(R.id.ivSearchProduct);

      //  toggleButton = (ToggleButton)

        ActionBar actionBar = getSupportActionBar();  //To create custom ActionBar with drawable
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        }

        RecyclerView.LayoutManager layoutManager;

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvProductList);
        int numberOfColumns = 3;

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyRecyclerViewAdapter(this , data);
        adapter.setClickListener(ProductListActivity.this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(ProductListActivity.this, "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position,Toast.LENGTH_LONG).show();
    }
}