package com.hrhera.cahdcaht.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.data.model.Convrsation;
import com.hrhera.cahdcaht.data.model.OnItmeClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConvarsationAdapter extends RecyclerView.Adapter<ConvarsationAdapter.CoHolder> {

    private List<Convrsation> convrsationList = new ArrayList<>();
    private OnItmeClick onItmeClick;

    public void setConvrsationList(List<Convrsation> convrsationList) {
        this.convrsationList = convrsationList;
        notifyDataSetChanged();
    }

    public void setOnItmeClick(OnItmeClick onItmeClick) {
        this.onItmeClick = onItmeClick;
    }

    @NonNull
    @Override
    public CoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conv,parent,false);
        return new CoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoHolder holder, int position) {
        Convrsation convrsation = convrsationList.get(position);
        holder.name.setText(convrsation.getUser1().getName());
        Picasso.get().load(convrsation.getUser1().getPhotoLink()).fit().centerCrop().into(holder.userImage);
        if (convrsation.getUser1ID().equals(DataMannger.getInstance().getLoginUser().getId())) {
            holder.name.setText(convrsation.getUser2().getName());
            Picasso.get().load(convrsation.getUser2().getPhotoLink()).fit().centerCrop().into(holder.userImage);
        }

        if (convrsation.getConMessages().size() > 0) {
            holder.lastMessage.setText(convrsation.getConMessages().get(0).getMessage());
        }
        holder.itemView.setOnClickListener(view -> {

            if (onItmeClick != null) {
                onItmeClick.onClick(convrsation);
            }

        });
    }

    @Override
    public int getItemCount() {
        return convrsationList.size();
    }

     class CoHolder extends RecyclerView.ViewHolder {
        TextView name, lastMessage;
        ImageView userImage;

        public CoHolder(@NonNull View item) {
            super(item);
            name = item.findViewById(R.id.userName);
            lastMessage = item.findViewById(R.id.lastLinOfCon);
            userImage = item.findViewById(R.id.userImage);


        }
    }
}
