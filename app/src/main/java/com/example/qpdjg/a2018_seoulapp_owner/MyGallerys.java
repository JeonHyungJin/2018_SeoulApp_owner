package com.example.qpdjg.a2018_seoulapp_owner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class MyGallerys extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<My_gallery_read_data> my_gallery_read_datas = new ArrayList<>();
    private List<String>uidLists = new ArrayList<>();
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gallerys);
        database = FirebaseDatabase.getInstance();
        String tokenID = FirebaseInstanceId.getInstance().getToken();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecylcerViewAdapter boardRecylcerViewAdapter = new BoardRecylcerViewAdapter();
        recyclerView.setAdapter(boardRecylcerViewAdapter);

        database.getReference().child("OwnerProfile/"+tokenID+"/MyGallerys").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                my_gallery_read_datas.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    My_gallery_read_data my_gallery_read_data = snapshot.getValue(My_gallery_read_data.class);
                    System.out.println(my_gallery_read_data.My_Gallery_name);
                    my_gallery_read_datas.add(my_gallery_read_data);
                }
                boardRecylcerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    class BoardRecylcerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_gallerys_listview,parent,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder)holder).textView.setText(my_gallery_read_datas.get(position).My_Gallery_name);
            //((CustomViewHolder)holder)..setText(my_gallery_read_datas.get(position).Gallery_name);

        }

        @Override
        public int getItemCount() {
            return my_gallery_read_datas.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.MyGallery_imageView);
                textView = (TextView)view.findViewById(R.id.MyGallery_Name);
            }
        }
    }
}
