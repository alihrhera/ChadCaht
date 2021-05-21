package com.hrhera.cahdcaht.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.utl.CircleTransform;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.data.model.Conversation;
import com.hrhera.cahdcaht.data.model.OnItmeClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConvarsationAdapter extends RecyclerView.Adapter<ConvarsationAdapter.CoHolder> {

    private List<Conversation> conversationList = new ArrayList<>();
    private OnItmeClick onItmeClick;

    public void setConversationList(List<Conversation> conversationList) {
        this.conversationList = conversationList;
        notifyDataSetChanged();
    }

    public void setOnItmeClick(OnItmeClick onItmeClick) {
        this.onItmeClick = onItmeClick;
    }

    @NonNull
    @Override
    public CoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conv, parent, false);
        return new CoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoHolder holder, int position) {
        Conversation conversation = conversationList.get(position);
        holder.name.setText(conversation.getUser1().getName());
        Picasso.get().load(DataMannger.getInstance().userIcons[conversation.getUser1().getUserIconId()]).fit().centerCrop().transform(new CircleTransform()).into(holder.userImage);
        if (conversation.getUser1ID().equals(DataMannger.getInstance().getLoginUser().getId())) {
            holder.name.setText(conversation.getUser2().getName());
            Picasso.get().load(DataMannger.getInstance().userIcons[conversation.getUser2().getUserIconId()]).fit().centerCrop().transform(new CircleTransform()).into(holder.userImage);
        }

        if (conversation.getConMessages().size() > 0) {
            holder.lastMessage.setText(conversation.getConMessages().get(0).getMessage());
        }
        holder.itemView.setOnClickListener(view -> {

            if (onItmeClick != null) {
                onItmeClick.onClick(conversation);
            }

        });
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
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
