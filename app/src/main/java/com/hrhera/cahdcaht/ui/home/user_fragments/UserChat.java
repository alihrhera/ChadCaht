package com.hrhera.cahdcaht.ui.home.user_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.ui.MainActivity;
import com.hrhera.cahdcaht.ui.home.ChatFrage;
import com.hrhera.cahdcaht.utl.DataMannger;
import com.hrhera.cahdcaht.ui.adapters.ConvarsationAdapter;
import com.hrhera.cahdcaht.data.model.Convrsation;

import java.util.List;


public class UserChat extends Fragment {


    public UserChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_chat, container, false);

        RecyclerView allCon = root.findViewById(R.id.allChat);
        allCon.setLayoutManager(new LinearLayoutManager(getContext()));
        ConvarsationAdapter adapter = new ConvarsationAdapter();
        allCon.setAdapter(adapter);
        adapter.setOnItmeClick(object -> {
            if (object instanceof Convrsation) {
                Convrsation con = (Convrsation) object;
                DataMannger.getInstance().setMoveToConv(con);
                ((MainActivity) getActivity()).attachFrag(new ChatFrage());
            }

        });
        DataMannger.getInstance().getAllUserChatConvrsatio(list -> {
            adapter.setConvrsationList((List<Convrsation>) list);

        });
   /*     MyStaticData.getInstance().getAllUserChat(list -> {
            adapter.setConvrsationList((List<Convrsation>) list);
        });
        */


        return root;
    }
}