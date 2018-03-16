package com.example.toshimishra.photolearn;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TrainerAddQuizTitle extends AppCompatActivity {
    Button button;
    EditText et;
    TextView text_ls;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_add_quiztitle);
        text_ls = (TextView)findViewById(R.id.title_LS);
        button = (Button)findViewById(R.id.bt_Add);
        et = (EditText)findViewById(R.id.xh_txt);
        text_ls.setText(State.getCurrentSession().getCourseCode());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et.getText().toString();
                new Trainer().createQuizTitle(title);
                finish();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        else if(i == R.id.action_switch){
            startActivity(new Intent(this, ParticipantEnterLearningsessionActivity.class));
            finish();
            return  true;

        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}

