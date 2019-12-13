package com.example.seventhhomework.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seventhhomework.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> itemList;
    private Context context;
    private static final int FIRST = 1;
    private static final int SECOND = 2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FIRST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first, parent, false);
            final MyViewHolder holder = new MyViewHolder(view);
            holder.item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Item item = itemList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(item.getUri()));
                    context.startActivity(intent);
                }
            });
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second, parent, false);
            final WithImageViewHolder holder = new WithImageViewHolder(view);
            holder.item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Item item = itemList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(item.getUri()));
                    context.startActivity(intent);
                }
            });
            return holder;
        }
    }

    public MyRecyclerViewAdapter(List<Item> itemList,Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position).getImageUri() == null) {
            return FIRST;
        } else {
            return SECOND;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == FIRST) {

            final Item item = itemList.get(position);
            MyViewHolder holder_1 = (MyViewHolder) holder;
            holder_1.who.setText(item.getWho());
            holder_1.desc.setText(item.getDesc());

        } else {

            final Item item = itemList.get(position);
            WithImageViewHolder holder_2 = (WithImageViewHolder) holder;
            holder_2.who.setText(item.getWho());
            holder_2.desc.setText(item.getDesc());
            RequestOptions requestOptions = new RequestOptions()
                                            .override(100,150);
            Glide.with(context).load(item.getImageUri()).apply(requestOptions).into(holder_2.image);

        }
    }

    @Override
    public int getItemCount() {
        Log.d("Dust_it_off", "Count" + itemList.size());
        if (itemList.size() != 0) {
            return itemList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView who;
        TextView desc;
        TextView uri;
        View item_view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_view = itemView;
            who = itemView.findViewById(R.id.tv_who);
            desc = itemView.findViewById(R.id.tv_desc);

        }
    }


    public class WithImageViewHolder extends RecyclerView.ViewHolder {

        TextView who;
        TextView desc;
        ImageView image;
        View item_view;

        public WithImageViewHolder(@NonNull View itemView) {
            super(itemView);

            item_view = itemView;
            who = itemView.findViewById(R.id.tv_who);
            desc = itemView.findViewById(R.id.tv_desc);
            image = itemView.findViewById(R.id.imv_item);

        }
    }
}





