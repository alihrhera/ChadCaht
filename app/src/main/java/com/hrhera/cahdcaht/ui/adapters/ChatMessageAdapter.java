package com.hrhera.cahdcaht.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.data.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.OneChat> {

    private List<Message>messageList=new ArrayList<>();
    private int other_user=0;
    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_usr,parent,false);

        if (viewType==other_user){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_other_usr,parent,false);
        }
        return new OneChat(view);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull OneChat holder, int position) {
        Message message=messageList.get(position);
        holder.mess.setText(message.getMessage());

    }

    @Override
    public int getItemViewType(int position) {
        int type=other_user;
        if (messageList.get(position).getSenderID().equals( DataMannger.getInstance().getLoginUser().getId())){
            type=1;
        }

        return type;
    }

    static class OneChat extends RecyclerView.ViewHolder{
        TextView mess;
        TextView time;

        public OneChat(View item){
            super(item);
            mess=item.findViewById(R.id.message);
            time=item.findViewById(R.id.time);

        }

    }
}
