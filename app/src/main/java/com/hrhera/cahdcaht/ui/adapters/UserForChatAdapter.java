package com.hrhera.cahdcaht.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.data.model.ChatUser;

import java.util.ArrayList;
import java.util.List;

public class UserForChatAdapter extends RecyclerView.Adapter<UserForChatAdapter.OneChat> {

    private List<ChatUser> chatUserList = new ArrayList<>();

    public void setList(List<ChatUser> messageList) {
        this.chatUserList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conv, parent, false);

        return new OneChat(view);

    }

    @Override
    public int getItemCount() {
        return chatUserList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull OneChat holder, int position) {
        ChatUser user = chatUserList.get(position);
        holder.name.setText(user.getName());
        holder.phone.setText(user.getPhone());

    }

    class OneChat extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;

        public OneChat(View item) {
            super(item);
            name = item.findViewById(R.id.userName);
            phone = item.findViewById(R.id.lastLinOfCon);

        }

    }
}
