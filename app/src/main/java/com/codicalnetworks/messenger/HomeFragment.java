package com.codicalnetworks.messenger;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    private RecyclerView recycler;
    private String userName;
    private String userMessage;

    private View mView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recycler = (RecyclerView) mView.findViewById(R.id.messages_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mView = inflater.inflate(R.layout.fragment_home, container, false);



        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(mUsersDatabase, ChatMessage.class)
                        .build();

        FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder> adapter  =
                new FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position, @NonNull ChatMessage model) {
                        holder.nameText.setText(model.getUsername());
                    }

                    @Override
                    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return null;
                    }
                };

        recycler.setAdapter(adapter);
    }

    private  class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public ChatMessageViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.single_user_name);
            messageText = (TextView) itemView.findViewById(R.id.single_user_image);

        }


    }
}
