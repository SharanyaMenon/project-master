package com.example.toshimishra.photolearn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantAttemptQuizItemActivity extends AppCompatActivity {

    Button terminate;
    private ViewPager mViewPager;

    //ViewPager总共多少个页面
    List<BasePageView> mPageViews = new ArrayList<>();

    //数据源The data source
    // List<String> mStrings = new ArrayList<>();
    List<String> mPhotoURL = new ArrayList<>();
    List<String> mQuiz = new ArrayList<>();
    List<Integer> mChoice=new ArrayList<>();
    List<String> mOption1 = new ArrayList<>();
    List<String> mOption2= new ArrayList<>();
    List<String> mOption3= new ArrayList<>();
    List<String> mOption4= new ArrayList<>();

    private ParticipantAttemptQuizItemActivity.MyAdapter mAdapter;
    private TextView mTvNum;
    private int mCurrentCount = 1;//默认为1
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        initView();//初始化界面
        initEvent();
        initDatas();


    }

    private void initDatas() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("LearningSessions-QuizTitles-QuizItems").child(State.getCurrentSession().getSessionID()).child(State.getCurrentQuizTitle().getTitleID());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 mPhotoURL.clear();
                 mOption1.clear();
                 mOption2.clear();
                 mOption3.clear();
                 mOption4.clear();
                 mPageViews.clear();
                 mChoice.clear();
                 mQuiz.clear();

                for ( DataSnapshot val : dataSnapshot.getChildren()){
                    //TODO read quiz answer if any and set the choice selected
                    addPage(val.getValue(QuizItem.class),0);

                }
                mAdapter.notifyDataSetChanged();
                if(mQuiz.size()==0)
                    mTvNum.setText(0+ " / " + 0);
                else
                    mTvNum.setText(mCurrentCount + " / " + mAdapter.getCount());
                if(mChoice.size()==mQuiz.size())
                        terminate.setText("Submit");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_participant_attempt_quiz_item);
        mTvNum = (TextView) findViewById(R.id.tvnum);
        terminate = (Button)findViewById(R.id.Terminate);
        if(mQuiz.size()==0)
            mTvNum.setText(0+ " / " + 0);
        else
            mTvNum.setText(mCurrentCount + " / " + mAdapter.getCount());

        if(mChoice.size()==mQuiz.size())
            terminate.setText("Submit");

        //获取viewpage实例
        // Get the viewpage instance.
        mViewPager = (ViewPager) findViewById(R.id.viewPage);

        //PagerAdapter关联ViewPager，数据源间接绑定到ViewPager
        // The PagerAdapter is associated with ViewPager, and the data source is indirectly bound to the ViewPager.
        mAdapter = new ParticipantAttemptQuizItemActivity.MyAdapter();
        mViewPager.setAdapter(mAdapter);


    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mQuiz.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePageView basePageView = mPageViews.get(position);
            View rootView = basePageView.getRootView();
            basePageView.initData();
            container.addView(rootView);
            return rootView;//返回显示
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }




    /**
     * 显示popupWindow
     */ private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 在底部显示
        window.showAtLocation(ParticipantAttemptQuizItemActivity.this.findViewById(R.id.start),
                Gravity.BOTTOM, 0, 0);


        //popWindow消失监听方法
        window.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.first);
        first.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Remove all attempted answers", Toast.LENGTH_LONG).show();
            }
        });

        Button second=(Button) view.findViewById(R.id.second);
        second.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Keep all attempted answers", Toast.LENGTH_LONG).show();
            }
        });

        Button third = (Button) view.findViewById(R.id.third);
        third.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //销毁弹出框
                window.dismiss();
            }
        });


    }




    private void initEvent() {
        terminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至第二个Activity
                // Jump to the second Activity.
                //Todo pop up window
                showPopwindow();
            }
        });





        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentCount = position + 1;
                mTvNum.setText(mCurrentCount + " / " + mAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String datas = data.getStringExtra("backString");
            if(TextUtils.isEmpty(datas)){
                return;
            }
//            //成功返回
//            Log.e("YDL", datas);

        }
    }

    /**
     * 该方法封装了添加页面的代码逻辑实现，参数text为要展示的数据
     * This method encapsulates the code logic implementation of the added page, and the parameter text is the data to be displayed.
     */
    public void addPage(QuizItem item,int choiceselected) {
        BasePageView basePageView ;
       /* BasePageView basePageView = new ParticipantPagerViewQI(this,item.getQuiz(),item.getPhotoURL(),item.getOption1(),item.getOption2(),item.getOption3(),item.getOption4(),int choiceselected);
        mPhotoURL.add(item.getPhotoURL());
        mQuiz.add(item.getQuiz());
        mOption1.add(item.getOption1());
        mOption2.add(item.getOption1());
        mOption3.add(item.getOption1());
        mOption4.add(item.getOption1());
        mChoice.add(choiceselected);*/
        //mPageViews.add(basePageView);//为数据源添加一项数据Add a data to the data source.

    }
}

