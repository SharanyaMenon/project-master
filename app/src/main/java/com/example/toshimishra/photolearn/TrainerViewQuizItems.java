package com.example.toshimishra.photolearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUGANTHI on 15-03-2018.
 */

public class TrainerViewQuizItems extends AppCompatActivity implements SampleRecyclerAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private List<String> dataSet;
    private List<QuizItem> quizItems;
    private SampleRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view_quizitems);

        dataSet = new ArrayList<>();
        quizItems = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recy_quizitem);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SampleRecyclerAdapter(this,dataSet, QuizItem.class);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("LearningSessions-QuizTitles-QuizItems").child(State.getCurrentSession().getSessionID()).child(State.getCurrentQuizTitle().getTitleID());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSet.clear();
                quizItems.clear();
                for ( DataSnapshot val : dataSnapshot.getChildren()){
                    dataSet.add(val.getValue(QuizItem.class).getQuestion());
                    quizItems.add(val.getValue(QuizItem.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button button = (Button)findViewById(R.id.bt_Add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainerViewQuizItems.this, TrainerAddQuizItem.class));
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, String name) {

    }
}
