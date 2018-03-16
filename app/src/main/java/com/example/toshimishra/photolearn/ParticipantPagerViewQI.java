package com.example.toshimishra.photolearn;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ParticipantPagerViewQI extends  BasePageView{



    private TextView mQuiz;
    private TextView mPhotoURL;
    private RadioButton mOption1,mOption2,mOption3,mOption4;
    private  String quiz;
    private String photoURL;
    private  String option1,option2,option3,option4;

    private  int choiceselected;

    public ParticipantAttemptQuizItemActivity mMainActivity;
    public final LayoutInflater mInflater;

    public ParticipantPagerViewQI(ParticipantAttemptQuizItemActivity mainActivity, String quiz, String photoURL, String option1,String option2,String option3,String option4,int choiceselected) {
        super();
        mMainActivity = mainActivity;
        mInflater =  LayoutInflater.from(mMainActivity);
        this.quiz=quiz;
        this.photoURL=photoURL;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.choiceselected=choiceselected;

    }

    @Override
    public View initView() {
        View itemView = mInflater.inflate(R.layout.activity_participant_pager_view_qi,null);
        mQuiz=(TextView)itemView.findViewById(R.id.title_Quest) ;
        mOption1=(RadioButton)itemView.findViewById(R.id.radioButton1);
        mOption2=(RadioButton)itemView.findViewById(R.id.radioButton2);
        mOption3=(RadioButton)itemView.findViewById(R.id.radioButton3);
        mOption4=(RadioButton)itemView.findViewById(R.id.radioButton4);


        return itemView;
    }

    @Override
    public void initData() {
        super.initData();
       mQuiz.setText((quiz));
       mOption1.setText(option1);
       mOption2.setText(option2);
       mOption3.setText(option3);
       mOption4.setText(option4);
       //TODO ADD THE IMG PART
       switch (choiceselected)
       {
           case  1:mOption1.setChecked(true);
           break;
           case  2:mOption2.setChecked(true);
           break;
           case  3:mOption3.setChecked(true);
           break;
           case  4:mOption4.setChecked(true);
           break;
       }
    }
}