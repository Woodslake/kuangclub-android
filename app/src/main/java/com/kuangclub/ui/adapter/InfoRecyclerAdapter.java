package com.kuangclub.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuangclub.R;
import com.kuangclub.model.bean.Info;

import java.util.List;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.ViewHolder> {

    private List<Info> infoList;

    public InfoRecyclerAdapter(List<Info> infoList) {
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_info_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Info info = infoList.get(position);
        if(info == null){
            return;
        }
        holder.tvRecycler.setText(info.getTitle());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecycler;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRecycler = itemView.findViewById(R.id.tv_recycler);
        }
    }
}
