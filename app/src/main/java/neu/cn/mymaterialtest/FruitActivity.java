package neu.cn.mymaterialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by neuHenry on 2017/6/8.
 */

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "fruit_name";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    public static final String GIRL_IMAGE_URL = "girl_image_url";

    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageView;
    private Toolbar mToolBar;
    private TextView mTextView;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_content);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        String girlImageUrl = intent.getStringExtra(GIRL_IMAGE_URL);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mImageView = (ImageView) findViewById(R.id.fruit_image_view);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mTextView = (TextView) findViewById(R.id.fruit_content_text);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button_comment);
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitle(fruitName);
        Glide.with(this).load(girlImageUrl).into(mImageView);
        //Glide.with(this).load(fruitImageId).into(mImageView);
        //mImageView.setImageResource(fruitImageId);
        //mTextView.setText(generateContent(fruitName));
        mTextView.setText(generateContent(girlImageUrl));
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "请输入评论", Snackbar.LENGTH_LONG).setAction("提交", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String generateContent(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            stringBuilder.append(name);
        }
        return stringBuilder.toString();
    }
}
