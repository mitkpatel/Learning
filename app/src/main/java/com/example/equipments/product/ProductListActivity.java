package com.example.equipments.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
    int[] imagePosition = new int[sampleTestingLimit];
    RecyclerView rvProducts;
    LinearLayout buttonTestingLayout,imageTestingLayout;
    Button btnTesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        etSearchProduct = (EditText) findViewById(R.id.etSearchProduct);
        ivSearchProduct = (ImageView) findViewById(R.id.ivSearchProduct);
        ivBackButton = (ImageView) findViewById(R.id.ivBackActivity);
        rvProducts = findViewById(R.id.rvProductList);
        buttonTestingLayout = (LinearLayout) findViewById(R.id.btnTestingLayout);
        imageTestingLayout = (LinearLayout) findViewById(R.id.imagePricePopupLayout);
        btnTesting = (Button) findViewById(R.id.btnTesting);

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivityWithAnimation();
                //openActivity(ProductListActivity.this, LoginActivity.class);
            }
        });

        btnTesting.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("hover", "Bring yor cursor over the button");
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    //instantiate the popup.xml layout file
                    LayoutInflater layoutInflater = (LayoutInflater) ProductListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.productlist_popup_menu,null);

                    //instantiate popup window
                    PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    popupWindow.setOutsideTouchable(true);

                    //display the popup window
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    return true;
                }

                return false;
            }
        });

        prepareDemoData();
    }

    private void prepareDemoData() {
        for (int i = 0; i < demoProductName.length; i++) {
            demoProductName[i] = String.valueOf(i);
            imagePosition[i] = i+1;
         }
        if (isTabletDevice()) {
            rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvProducts.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        }
        productAdapter = new ProductListRecyclerViewAdapter(this, demoProductName,imagePosition);
        rvProducts.setAdapter(productAdapter);
    }

    public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder> {

        String[] productName;
        LayoutInflater mInflater;
        int[] imageId;

        ProductListRecyclerViewAdapter(Context context, String[] data, int[] imageId) {
            this.mInflater = LayoutInflater.from(context);
            this.productName = data;
            this.imageId = imageId;
        }

        @Override

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.row_product_grid, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.tvProductName.setText(productName[position]);

            holder.ivPricePopupMenu.setTag(new Integer(position));   //To get particular position of item in grid

            holder.ivPricePopupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initiatePopupWindow(v);
                }
            });

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

        private void initiatePopupWindow(View view) {


            Integer position = (Integer)view.getTag();  //Get position of particular item

            //instantiate the popup.xml layout file
            LayoutInflater layoutInflater = (LayoutInflater) ProductListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.productlist_popup_menu,null);

            //instantiate popup window
            final PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.setFocusable(true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setOutsideTouchable(true);

            //display the popup window
            popupWindow.showAtLocation(buttonTestingLayout,  Gravity.NO_GRAVITY,locateView(view).right,locateView(view).bottom);
          //  popupWindow.showAtLocation(buttonTestingLayout,  Gravity.NO_GRAVITY,locateView(view).top,locateView(view).left);
        }

        public Rect locateView(View v)
        {
            int[] loc_int = imageId;
            if (v == null) return null;
            try
            {
                v.getLocationOnScreen(loc_int);
            } catch (NullPointerException npe)
            {
                //Happens when the view doesn't exist on screen anymore.
                return null;
            }
            Rect location = new Rect();
            /*location.left = loc_int[0];
            location.top = loc_int[1];
            location.right = location.left + v.getWidth();
            location.bottom = location.top + v.getHeight();*/

            //For right and bottom side popup
            location.left = loc_int[0];
            location.top = loc_int[1];
            location.right = location.left + v.getWidth();
            location.bottom = location.top + v.getHeight();

            /*location.left = location.right + v.getWidth();
            location.top = location.top + v.getWidth();
            location.right = loc_int[0];
            location.bottom = loc_int[1];
*/
            return location;
        }

        @Override
        public int getItemCount() {
            return productName.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvProductName;
            ImageView ivProductImage, ivPricePopupMenu;

            ViewHolder(View itemView) {
                super(itemView);
                tvProductName = itemView.findViewById(R.id.tvProductName);
                ivProductImage = itemView.findViewById(R.id.ivProductImage);
                ivPricePopupMenu = itemView.findViewById(R.id.ivPricePopupMenu);

            }

        }

    }
}