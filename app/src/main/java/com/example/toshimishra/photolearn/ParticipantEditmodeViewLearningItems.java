package com.example.toshimishra.photolearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toshimishra on 14/03/18.
 */

public class ParticipantEditmodeViewLearningItems extends AppCompatActivity{
        private Button mButton;
        private ViewPager mViewPager;

        //ViewPager总共多少个页面
        List<ParticipantPagerViewLI> mPageViews = new ArrayList<>();

        //数据源The data source
       // List<String> mStrings = new ArrayList<>();
        List<String> mPhotoURL = new ArrayList<>();
        List<String> mPhotoDesc = new ArrayList<>();
        List<String> mGPS = new ArrayList<>();
        private MyAdapter mAdapter;
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
        mDatabase = database.getReference().child("LearningSessions-LearningTitles-LearningItems").child(State.getCurrentSession().getSessionID()).child(State.getCurrentLearningTitle().getTitleID());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    mGPS.clear();
                    mPhotoDesc.clear();
                    mPhotoURL.clear();
                    mPageViews.clear();
                for ( DataSnapshot val : dataSnapshot.getChildren()){
                        addPage(val.getValue(LearningItem.class));

                }
                    mAdapter.notifyDataSetChanged();
                    if(mPhotoDesc.size()==0)
                        mTvNum.setText(0+ " / " + 0);
                    else
                        mTvNum.setText(mCurrentCount + " / " + mAdapter.getCount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

        private void initView() {
            setContentView(R.layout.activity_participant_editmode_view_learningitems);
            mButton = (Button) findViewById(R.id.bt_Add);
            mTvNum = (TextView) findViewById(R.id.tvnum);
            if(mPhotoDesc.size()==0)
                mTvNum.setText(0+ " / " + 0);
            else
                mTvNum.setText(mCurrentCount + " / " + mAdapter.getCount());


            //获取viewpage实例
            // Get the viewpage instance.
            mViewPager = (ViewPager) findViewById(R.id.viewPage);

            //PagerAdapter关联ViewPager，数据源间接绑定到ViewPager
            // The PagerAdapter is associated with ViewPager, and the data source is indirectly bound to the ViewPager.
            mAdapter = new MyAdapter();
            mViewPager.setAdapter(mAdapter);

            if(!State.isEditMode() || State.isTrainerMode())
                mButton.setVisibility(View.GONE);
        }

private class MyAdapter extends PagerAdapter {

            @Override
    public int getCount() {
        return mPhotoDesc.size();
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
        ParticipantPagerViewLI basePageView = mPageViews.get(position);
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

    private void initEvent() {
        mButton.setOnClickListener(new View.OnClickListener() {
    @Override
            public void onClick(View v) {
                //跳转至第二个Activity
                // Jump to the second Activity.
                Intent intent = new Intent(ParticipantEditmodeViewLearningItems.this, ParticipantEditmodeAddLearningItem.class);
                startActivityForResult(intent, 0);
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
    public void addPage(LearningItem item) {

        ParticipantPagerViewLI basePageView = new ParticipantPagerViewLI(this, item.getPhotoDesc(),item.getGps(),item.getPhotoURL());
        mPhotoURL.add(item.getPhotoURL());
        mPhotoDesc.add(item.getPhotoDesc());
        mGPS.add(item.getGps());
        mPageViews.add(basePageView);//为数据源添加一项数据Add a data to the data source.

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
            //Toggle the Trainer mode;
            State.setTrainerMode(!State.isTrainerMode());
            startActivity(new Intent(this, TrainerSessionsActivity.class));
            finish();
            return  true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}

