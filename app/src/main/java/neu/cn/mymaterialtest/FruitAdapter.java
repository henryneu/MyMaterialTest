package neu.cn.mymaterialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by neuHenry on 2017/6/8.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.MyViewHolder> {

    private List<Fruit> fruitLists;
    private Context mContext;

    public FruitAdapter(List<Fruit> fruitLists) {
        this.fruitLists = fruitLists;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            imageView = (ImageView) cardView.findViewById(R.id.fruit_image);
            textView = (TextView) cardView.findViewById(R.id.fruit_name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                Fruit fruit = fruitLists.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getImageName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Fruit fruit = fruitLists.get(position);
        holder.textView.setText(fruit.getImageName());
        // 调用load()方法加载图片，可以传入URL地址、本地路径或资源ID
        Glide.with(mContext).load(fruit.getImageId()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return fruitLists.size();
    }
}
