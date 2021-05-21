package com.hrhera.cahdcaht.utl;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

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
import com.hrhera.cahdcaht.data.model.Conversation;
import com.hrhera.cahdcaht.data.model.GroupConversation;
import com.hrhera.cahdcaht.data.model.Message;
import com.hrhera.cahdcaht.data.model.OnListDone;
import com.hrhera.cahdcaht.data.model.OnUserData;
import com.hrhera.cahdcaht.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void loading() {
        if (appMethod != null) {
            appMethod.loading();
        }
    }


    public void normal() {
        if (appMethod != null) {
            appMethod.normal();
        }
    }


    private SharedPreferences shp;

    public void setRememberMeStatus(Context context, boolean remember) {
        if (shp == null) {
            shp = context.getSharedPreferences("Info", 0);
        }
        shp.edit().putBoolean("remember", remember).apply();
    }

    public boolean isRemember(Context context) {
        if (shp == null) {
            shp = context.getSharedPreferences("Info", 0);
        }
        return shp.getBoolean("remember", false);
    }

    public void setUserData(String phone, String pass) {

        shp.edit().putString("phone", phone).apply();
        shp.edit().putString("pass", pass).apply();

    }

    public String getPhone() {

        return shp.getString("phone", "");
    }

    public String getPass() {

        return shp.getString("pass", "");
    }


    public Integer[] coversPhoto = {R.drawable.a1, R.drawable.a2, R.drawable.a3};
    public Integer[] userIcons = {
            R.drawable.ic_batman_162
            , R.drawable.ic_queen_165
            , R.drawable.ic_lego_136
            , R.drawable.ic_scubadiver_131
            , R.drawable.ic_lego_167
            , R.drawable.ic_knight_121
            , R.drawable.ic_superman_156
            , R.drawable.ic_lego_160
            , R.drawable.ic_gnome_138
            , R.drawable.ic_injured_146
            , R.drawable.ic_clown_122
            , R.drawable.ic_lego_161
            , R.drawable.ic_dentist_118
            , R.drawable.ic_pirate_180
            , R.drawable.ic_lego_137
            , R.drawable.ic_lego_126
            , R.drawable.ic_lego_177
            , R.drawable.ic_lego_176
            , R.drawable.ic_thor_13
            , R.drawable.ic_lego_18
            , R.drawable.ic_mohawk_110
            , R.drawable.ic_santaclaus_153
            , R.drawable.ic_lego_179
            , R.drawable.ic_businessman_15
            , R.drawable.ic_explorer_111
            , R.drawable.ic_wolverine_139
            , R.drawable.ic_lego_125
            , R.drawable.ic_lego_10
            , R.drawable.ic_thespian_178
            , R.drawable.ic_lego_129
            , R.drawable.ic_caveman_12
            , R.drawable.ic_lego_145
            , R.drawable.ic_lego_148
            , R.drawable.ic_nativeamerican_11
            , R.drawable.ic_ninja_17
            , R.drawable.ic_lego_170
            , R.drawable.ic_hiphop_143
            , R.drawable.ic_witch_116
            , R.drawable.ic_desert_124
            , R.drawable.ic_magician_150
            , R.drawable.ic_priest_14
            , R.drawable.ic_devil_119
            , R.drawable.ic_lego_158
            , R.drawable.ic_vampire_172
            , R.drawable.ic_lego_149
            , R.drawable.ic_cyborg_120
            , R.drawable.ic_lego_169
            , R.drawable.ic_boy_127
            , R.drawable.ic_dummy_113
            , R.drawable.ic_woman_173
            , R.drawable.ic_cheerleader_142
            , R.drawable.ic_mummy_16
            , R.drawable.ic_spiderman_135
            , R.drawable.ic_dj_155
            , R.drawable.ic_cowgirl_114
            , R.drawable.ic_americanfootballplayer_164
            , R.drawable.ic_fisherman_112
            , R.drawable.ic_spy_141
            , R.drawable.ic_lego_166
            , R.drawable.ic_conquistador_174
            , R.drawable.ic_surgeon_130
            , R.drawable.ic_man_163
            , R.drawable.ic_singer_154
            , R.drawable.ic_lego_128
            , R.drawable.ic_hiker_152
            , R.drawable.ic_diver_117
            , R.drawable.ic_cowboy_168
            , R.drawable.ic_mariachi_140
            , R.drawable.ic_lego_19
            , R.drawable.ic_vampire_147
            , R.drawable.ic_ninja_159
            , R.drawable.ic_man_132,
            R.drawable.ic_thief_157
            , R.drawable.ic_explorer_133
            , R.drawable.ic_lego_175
            , R.drawable.ic_lego_134
            , R.drawable.ic_cashier_144
            , R.drawable.ic_lego_151
            , R.drawable.ic_deadpool_171
            , R.drawable.ic_businessman_123};


    public String UsersRef = "Users";
    public String ChatRef = "Chats";
    public String GroupConversationRef = "GroupConversation";
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
        myRef.setValue(user).addOnCompleteListener(task -> addNewUser.onDone());

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
                                loginUser = u;
                                onUserData.onDone();
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


    private Conversation moveToConv = new Conversation();

    public Conversation getMoveToConv() {
        return moveToConv;
    }

    public void setMoveToConv(Conversation moveToConv) {
        this.moveToConv = moveToConv;
    }


    public void setChatList(OnListDone onListDone, Conversation conversation) {
        List<Message> ChatList = new ArrayList<>();
        DatabaseReference myRef = database.getReference(ChatRef + "/" + conversation.getId());
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

    public void updateProfile(String id, Map<String, Object> map) {
        DatabaseReference reference = database.getReference(UsersRef).child(id);
        reference.updateChildren(map);

    }

    private DatabaseReference groupConversationRefrence ;
    private OnListDone onGroupConversationListLoad;
    private List<GroupConversation>groupConversations=new ArrayList<>();
    public void getAllChatGroupConversation(OnListDone onGroupConversationListLoad) {
        this.onGroupConversationListLoad=onGroupConversationListLoad;
        if (groupConversationRefrence==null){
            groupConversationRefrence= database.getReference(UsersRef).child(GroupConversationRef);


            return;
        }
        onGroupConversationListLoad.onLoad(groupConversations);


    }



    public void startNewChat(Conversation conversation) {
        DatabaseReference myRef = database.getReference(ChatRef + "/" + conversation.getId());
        myRef.setValue(conversation);

    }

    private List<Conversation> convrsationsList = new ArrayList<>();

    OnListDone onChatListDone;

    public void getAllUserChatConvrsatio(OnListDone onListDone) {
        onChatListDone = onListDone;
        if (chatRef == null) {
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
                        Conversation c = ds.getValue(Conversation.class);
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
                        Conversation c = ds.getValue(Conversation.class);
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


    public void updateCon(Conversation conversation) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(ChatRef + "/" + conversation.getId());
        reference.setValue(conversation);
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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(UsersRef + "/" + phone);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                ChatUser chatUser = snapshot.getValue(ChatUser.class);
                                chatUserList.add(chatUser);

                            }
                            reference.removeEventListener(this);

                            Log.e("chatUserList", chatUserList.size() + "");
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
