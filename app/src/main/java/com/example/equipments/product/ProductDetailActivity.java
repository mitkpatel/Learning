package com.example.equipments.product;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.equipments.R;
import com.example.equipments.base.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ProductDetailActivity extends BaseActivity {

    ImageView ivCloseActivity;
    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    String[] demoProductLimit = new String[sampleTestingLimit];
    ProductDetailRecycleViewAdapter detailRecycleViewAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView rvProductDetail;
    private int dotsCount;
    private ImageView[] dots;
    private Timer timer = new Timer();
    private int current_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ivCloseActivity = (ImageView) findViewById(R.id.ivBackProductDetail);
        viewPager = (ViewPager) findViewById(R.id.vpProductDetail);
        rvProductDetail = (RecyclerView) findViewById(R.id.rvProductDetail);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

//        rvProductDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sliderDotsPanel = (LinearLayout) findViewById(R.id.SliderDots);
        sliderDotsPanel.bringToFront();

        ivCloseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  openActivity(ProductDetailActivity.this,ProductListActivity.class);
                closeActivityWithAnimation();
            }
        });

        prepareSlideTimer();   // Method for set the time interval in slider

        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        // loop which count number of image and set dots according to that
        for (int i = 0; i < dotsCount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.product_detail_non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.product_detail_active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //set Active dot for selected image
            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.product_detail_non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.product_detail_active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        prepareDemoData();
    }

    private void prepareSlideTimer(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == dots.length)
                    current_position = 0;
                viewPager.setCurrentItem(current_position++,true);
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },250,2000);
    }

    class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private Integer[] images = {R.drawable.infra_bazaar_backhoe, R.drawable.infra_bazaar_crane, R.drawable.infra_bazaar_excavator};

        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (LinearLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_layout_for_slider, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.ivSliderProducts);
            imageView.setImageResource(images[position]);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private void prepareDemoData() {
        for (int i = 0; i < demoProductLimit.length; i++) {
            demoProductLimit[i] = String.valueOf(i);
        }

     //   rvProductDetail.setLayoutManager(new GridLayoutManager(this, 2));   //For grid layout
        rvProductDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        detailRecycleViewAdapter = new ProductDetailActivity.ProductDetailRecycleViewAdapter(this, demoProductLimit);
        rvProductDetail.setAdapter(detailRecycleViewAdapter);
    }

    // Class for implementation of product_detail recycleView
    public class ProductDetailRecycleViewAdapter extends RecyclerView.Adapter<ProductDetailRecycleViewAdapter.ViewHolder>{

        LayoutInflater mInflater;
        String[] dataLength;
        String[] productDetailLabel;    //For labels of product details

        ProductDetailRecycleViewAdapter(Context context, String[] temp){
            this.mInflater = LayoutInflater.from(context);
            this.dataLength = temp;
            productDetailLabel = context.getResources().getStringArray(R.array.product_detail_label);  //Get details from string.xml file
        }

        @NonNull
        @Override
        public ProductDetailRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.row_product_detail_recycler, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductDetailRecycleViewAdapter.ViewHolder holder, int position) {
            holder.tvProductDetailLabel.setText(productDetailLabel[position]);
            holder.tvProductDetailFromServer.setText(dataLength[position]);
        }

        @Override
        public int getItemCount() { return productDetailLabel.length; }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvProductDetailLabel, tvProductDetailFromServer;
            ViewHolder(View itemView) {
                super(itemView);
                tvProductDetailLabel = itemView.findViewById(R.id.tvProductDetailLabel);
                tvProductDetailFromServer = itemView.findViewById(R.id.tvProductDetailFromServer);
            }

        }

    }

}

/*    HashMap<Object, Object> sliderImages = new HashMap<>();
        sliderImages.put("excavator", R.drawable.infra_bazaar_excavator);
        sliderImages.put("infra_bazaar_crane",R.drawable.infra_bazaar_crane);
        sliderImages.put("infra_bazaar_backhoe", R.drawable.infra_bazaar_backhoe);

        for (Object name : sliderImages.keySet()) {

            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    } */
