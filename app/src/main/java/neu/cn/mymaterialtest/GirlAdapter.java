package neu.cn.mymaterialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by neuHenry on 2017/6/9.
 */

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.MyViewHolder> {

    private List<BeautifulGirl> girlList;
    private Context mContext;

    public GirlAdapter(List<BeautifulGirl> girlList) {
        this.girlList = girlList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            imageView = (ImageView) cardView.findViewById(R.id.girl_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.girl_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                BeautifulGirl girl = girlList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.GIRL_IMAGE_URL, girl.getUrl());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BeautifulGirl beautifulGirl = girlList.get(position);
        Glide.with(mContext).load(beautifulGirl.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return girlList.size();
    }
}
