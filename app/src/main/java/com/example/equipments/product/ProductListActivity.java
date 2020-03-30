package com.example.equipments.product;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;

public class ProductListActivity extends BaseActivity {

    EditText etSearchProduct;
    ImageView ivSearchProduct, ivBackButton;
    ProductListRecyclerViewAdapter productAdapter;
    String[] demoProductName = new String[sampleTestingLimit];
    RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        etSearchProduct = (EditText) findViewById(R.id.etSearchProduct);
        ivSearchProduct = (ImageView) findViewById(R.id.ivSearchProduct);
        ivBackButton = (ImageView) findViewById(R.id.ivBackActivity);
        rvProducts = findViewById(R.id.rvProductList);

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivityWithAnimation();
                //openActivity(ProductListActivity.this, LoginActivity.class);
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

        prepareDemoData();
    }

    private void prepareDemoData() {
        for (int i = 0; i < demoProductName.length; i++) {
            demoProductName[i] = String.valueOf(i);
        }
        if (isTabletDevice()) {
            rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        }
        productAdapter = new ProductListRecyclerViewAdapter(this, demoProductName);
        rvProducts.setAdapter(productAdapter);
    }

    public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder> {

        String[] productName;
        LayoutInflater mInflater;

        ProductListRecyclerViewAdapter(Context context, String[] data) {
            this.mInflater = LayoutInflater.from(context);
            this.productName = data;
        }

        @Override

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.row_product_grid, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.tvProductName.setText(productName[position]);
            if (position % 2 == 0) {
                holder.ivProductImage.setImageResource(R.drawable.infra_bazaar_backhoe);
            } else if (position % 3 == 0) {
                holder.ivProductImage.setImageResource(R.drawable.infra_bazaar_crane);
            } else {
                holder.ivProductImage.setImageResource(R.drawable.infra_bazaar_excavator);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast(productName[position]);
                }
            });
        }

        @Override
        public int getItemCount() {
            return productName.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvProductName;
            ImageView ivProductImage;

            ViewHolder(View itemView) {
                super(itemView);
                tvProductName = itemView.findViewById(R.id.tvProductName);
                ivProductImage = itemView.findViewById(R.id.ivProductImage);
            }

        }

    }
}