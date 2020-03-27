package com.example.equipments.product;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;
import com.example.equipments.login.LoginActivity;

import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListActivity extends BaseActivity implements ProductListRecyclerViewAdapter.ItemClickListener {

    EditText etSearchProduct;
    ImageView ivSearchProduct,ivBackButton;
    ProductListRecyclerViewAdapter adapter;
    ToggleButton toggleButton;
    ImageButton btn;

    JSONArray jsonArray;
    String[] data = new String[BaseActivity.sampleTestingLimit] ;

    int[] product_images = {R.drawable.infra_bazaar_backhoe,R.drawable.infra_bazaar_crane,R.drawable.infra_bazaar_excavator,R.drawable.infra_bazaar_motorgrader,
            R.drawable.infra_bazaar_roller,R.drawable.infra_bazaar_tipper,R.drawable.infra_bazaar_transitmixer,R.drawable.infra_bazaar_used};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        etSearchProduct = (EditText)findViewById(R.id.etSearchProduct);
        ivSearchProduct = (ImageView)findViewById(R.id.ivSearchProduct);
        ivBackButton = (ImageView)findViewById(R.id.ivBackActivity);
      //  toggleButton = (ToggleButton)

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ProductListActivity.this, LoginActivity.class);
            }
        });

        ActionBar actionBar = getSupportActionBar();  //To create custom ActionBar with drawable
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        }

        for(int i=0; i<data.length;i++) {
            data[i] = String.valueOf(i);
            DisplayProduct(data);
        }
    }

    private void DisplayProduct(String[] i) {

        // Starting of RecycleView
        RecyclerView.LayoutManager layoutManager;

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvProductList);
        int numberOfColumns = 3;

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProductListRecyclerViewAdapter(this , data);
        adapter.setClickListener(ProductListActivity.this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(ProductListActivity.this, "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position,Toast.LENGTH_LONG).show();
    }
}