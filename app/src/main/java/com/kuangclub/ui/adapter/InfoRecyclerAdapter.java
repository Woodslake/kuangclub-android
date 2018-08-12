package com.kuangclub.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kuangclub.R;
import com.kuangclub.model.bean.InfoType;
import com.kuangclub.ui.activity.InfoDetailActivity;

import java.util.List;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.ViewHolder> {
    private Context context;

    private List<InfoType> infoTypeList;

    public InfoRecyclerAdapter(List<InfoType> infoTypeList) {
        this.infoTypeList = infoTypeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_info_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoType infoType = infoTypeList.get(position);
        if(infoType == null){
            return;
        }
        Glide.with(context).load(infoType.getImg()).into(holder.ivImg);
        holder.tvTitle.setText(infoType.getTitle());
        holder.tvContent.setText(infoType.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, InfoDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoTypeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvTitle;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
