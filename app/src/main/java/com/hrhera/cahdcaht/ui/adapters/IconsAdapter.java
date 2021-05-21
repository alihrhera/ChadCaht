package com.hrhera.cahdcaht.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.data.model.OnItmeClick;

import java.util.ArrayList;
import java.util.List;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.OneChat> {

    private List<Integer> dataList = new ArrayList<>();
    private OnItmeClick onClick;

    public void setDataList(List<Integer> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setOnClick(OnItmeClick onClick) {
        this.onClick = onClick;
    }
    @NonNull
    @Override
    public OneChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_icon_image, parent, false);
        return new OneChat(view);

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull OneChat holder, int position) {
        holder.icon.setImageResource(dataList.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onClick != null) {
                onClick.onClick(position);
            }
        });
    }


    static class OneChat extends RecyclerView.ViewHolder {
        ImageView icon;

        public OneChat(View item) {
            super(item);
            icon = item.findViewById(R.id.usrIcon);

        }

    }
}
