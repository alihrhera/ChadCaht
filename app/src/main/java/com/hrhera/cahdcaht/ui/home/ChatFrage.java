package com.hrhera.cahdcaht.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.ui.adapters.ChatMessageAdapter;
import com.hrhera.cahdcaht.data.model.Convrsation;
import com.hrhera.cahdcaht.data.model.Message;

import java.util.Calendar;
import java.util.List;

public class ChatFrage extends Fragment {

    public ChatFrage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chat_frage, container, false);
        RecyclerView chatMessage = root.findViewById(R.id.chatMessage);
        chatMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        ChatMessageAdapter adapter = new ChatMessageAdapter();
        chatMessage.setAdapter(adapter);
        Convrsation convrsation= DataMannger.getInstance().getMoveToConv();
        DataMannger.getInstance().setChatList(list -> {
            adapter.setMessageList((List<Message>) list);
        }, convrsation);


        EditText editText=root.findViewById(R.id.getMessage);
        root.findViewById(R.id.send).setOnClickListener(view -> {
            String msg=editText.getText().toString();
            if (msg.isEmpty()){
                return;
            }
            Message message=new Message();
            message.setSenderID(DataMannger.getInstance().getLoginUser().getId());
            message.setTime(Calendar.getInstance().getTimeInMillis());
            message.setMessage(msg);
            convrsation.getConMessages().add(message);
            convrsation.setLastUpdateTime(Calendar.getInstance().getTimeInMillis());
            DataMannger.getInstance().updateCon(convrsation);
            editText.setText("");
        });



        return root;
    }
}