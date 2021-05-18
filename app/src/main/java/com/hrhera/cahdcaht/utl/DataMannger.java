package com.hrhera.cahdcaht.utl;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hrhera.cahdcaht.R;
import com.hrhera.cahdcaht.data.model.AppMethod;
import com.hrhera.cahdcaht.data.model.AppUser;
import com.hrhera.cahdcaht.data.model.ChatUser;
import com.hrhera.cahdcaht.data.model.Convrsation;
import com.hrhera.cahdcaht.data.model.Message;
import com.hrhera.cahdcaht.data.model.OnListDone;
import com.hrhera.cahdcaht.data.model.OnUserData;
import com.hrhera.cahdcaht.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataMannger {

    private DataMannger() {
    }

    private static DataMannger app;

    public static synchronized DataMannger getInstance() {
        if (app == null) {
            app = new DataMannger();
        }

        return app;
    }


    AppMethod appMethod;

    public void setAppMethod(AppMethod appMethod) {
        this.appMethod = appMethod;
    }

    public void loading(){
        if (appMethod!=null){
            appMethod.loading();
        }
    }


    public void normal(){
        if (appMethod!=null){
            appMethod.normal();
        }
    }


    private SharedPreferences shp;
    public void setRememberMeStatus(Context context,boolean remember){
        if (shp==null){
            shp=context.getSharedPreferences("Info",0);
        }
        shp.edit().putBoolean("remember",remember).apply();
    }

    public boolean isRemember(Context context){
        if (shp==null){
            shp=context.getSharedPreferences("Info",0);
        }
        return shp.getBoolean("remember",false);
    }

    public void setUserData(String phone,String pass){

        shp.edit().putString("phone",phone).apply();
        shp.edit().putString("pass",pass).apply();

    }
    public String getPhone(){

        return shp.getString("phone","");
    }

    public String getPass(){

        return shp.getString("pass","");
    }










    public String UsersRef = "Users";
    public String ChatRef = "Chats";
    private OnUserData addNewUser;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // first Step
    public void CheckIfUser(User user, OnUserData addNewUser) {
        this.addNewUser = addNewUser;

        DatabaseReference myRef = database.getReference(UsersRef);
        Query query = myRef.orderByChild("phone").equalTo(user.getPhone()).limitToFirst(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    addToDataBase(user);

                    query.removeEventListener(this);
                    return;
                }
                if (addNewUser != null) {
                    addNewUser.onFail("هذ المستخدم مودود بالفعل ");
                }
                query.removeEventListener(this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                query.removeEventListener(this);

            }
        });

    }

    private void addToDataBase(User user) {

        DatabaseReference myRef =
                database.getReference(DataMannger.getInstance().UsersRef + "/" + user.getId());
        myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addNewUser.onDone();
            }
        });

    }


    public void startLogin(User user, OnUserData onUserData, Context ctx) {

        DatabaseReference myRef = database.getReference(UsersRef);
        Query query = myRef.orderByChild("phone").equalTo(user.getPhone()).limitToFirst(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        User u = d.getValue(User.class);
                        assert u != null;

                        if (user.getPassword().equals(u.getPassword())) {
                            if (onUserData != null) {
                                onUserData.onDone();
                                loginUser = u;
                            }
                            return;
                        }
                        if (onUserData != null) {
                            onUserData.onFail(ctx.getString(R.string.wrongPass));

                        }


                    }
                    return;
                }
                if (onUserData != null) {
                    onUserData.onFail(ctx.getString(R.string.not_user));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                query.removeEventListener(this);
            }
        });

    }

    private User loginUser = new User();

    public User getLoginUser() {
        return loginUser;
    }


    private Convrsation moveToConv = new Convrsation();

    public Convrsation getMoveToConv() {
        return moveToConv;
    }

    public void setMoveToConv(Convrsation moveToConv) {
        this.moveToConv = moveToConv;
    }


    public void setChatList(OnListDone onListDone, Convrsation convrsation) {
        List<Message> ChatList = new ArrayList<>();
        DatabaseReference myRef = database.getReference(ChatRef + "/" + convrsation.getId());
        myRef.child("conMessages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ChatList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Message message = ds.getValue(Message.class);

                        if (!ChatList.contains(message)) {
                            ChatList.add(message);
                        }
                        if (onListDone != null) {
                            onListDone.onLoad(ChatList);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void startNewChat(Convrsation convrsation) {
        DatabaseReference myRef = database.getReference(ChatRef + "/" + convrsation.getId());
        myRef.setValue(convrsation);

    }

    private List<Convrsation> convrsationsList = new ArrayList<>();

    OnListDone onChatListDone;
    public void getAllUserChatConvrsatio(OnListDone onListDone){
        onChatListDone=onListDone;
            if (chatRef==null){
                getAllUserChat();

                return;
            }
            onChatListDone.onLoad(convrsationsList);
    }
    DatabaseReference chatRef;

    private void getAllUserChat() {
         chatRef = database.getReference(ChatRef);
        Query query = chatRef.orderByChild("user1ID").equalTo(loginUser.getId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                convrsationsList = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Convrsation c = ds.getValue(Convrsation.class);
                        if (!convrsationsList.contains(c)) {
                            convrsationsList.add(c);
                        }
                    }
                }

                if (onChatListDone != null) {
                    onChatListDone.onLoad(convrsationsList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query query1 = chatRef.orderByChild("user2ID").equalTo(loginUser.getId());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Convrsation c = ds.getValue(Convrsation.class);
                        if (!convrsationsList.contains(c)) {
                            convrsationsList.add(c);
                        }
                    }
                }

                if (onChatListDone != null) {
                    onChatListDone.onLoad(convrsationsList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
















    public void updateCon(Convrsation convrsation) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(ChatRef + "/" + convrsation.getId());
        reference.setValue(convrsation);
    }


    private List<AppUser> appUserList = new ArrayList<>();
    private List<ChatUser> chatUserList = new ArrayList<>();

    public void setAppUserList(List<AppUser> list) {
        this.appUserList = list;
    }

    public void getAllUserForChat(OnListDone onContactsLoad) {
        if (onContactsLoad != null) {
            chatUserList = new ArrayList<>();
            for (AppUser appUser : appUserList) {
                for (String phone : appUser.getPhones()) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(UsersRef+"/" + phone);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                ChatUser chatUser = snapshot.getValue(ChatUser.class);
                                chatUserList.add(chatUser);

                            }
                            reference.removeEventListener(this);

                            Log.e("chatUserList",chatUserList.size()+"");
                            onContactsLoad.onLoad(chatUserList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }
        }

    }


}
