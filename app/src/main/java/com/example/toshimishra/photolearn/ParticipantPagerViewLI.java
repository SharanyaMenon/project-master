package com.example.toshimishra.photolearn;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ParticipantPagerViewLI implements LoadImageTask.Listener{

    private final View mRootView;
    private TextView mPhotoDesc;
    private TextView mGPS;
    private TextView mPhotoURL;
    private String photoDesc;
    private String gps;
    private String photoURL;
    private ImageView mImageView;
    Button mDelete;
    Button mUpdate;
    public ParticipantEditmodeViewLearningItems mMainActivity;
    public final LayoutInflater mInflater;

    public ParticipantPagerViewLI(ParticipantEditmodeViewLearningItems mainActivity, String photoDesc, String gps, String photoURL) {
        super();
        mMainActivity = mainActivity;
        mInflater =  LayoutInflater.from(mMainActivity);
        mRootView = initView();


        this.photoDesc = photoDesc;
        this.gps = gps;
        this.photoURL = photoURL;

    }


    public View initView() {
        View itemView = mInflater.inflate(R.layout.activity_participant_pager_view_li,null);
        mPhotoDesc = (TextView) itemView.findViewById(R.id.descirbe);
        mGPS = (TextView) itemView.findViewById(R.id.GPS);
        mDelete = (Button)itemView.findViewById(R.id.delete);
        mUpdate=(Button)itemView.findViewById(R.id.update);
        mImageView = (ImageView)itemView.findViewById(R.id.img);
        if(!State.isEditMode() || State.isTrainerMode())
        {
            mDelete.setVisibility(View.GONE);
            mUpdate.setVisibility(View.GONE);
        }

        return itemView;
    }


    public void initData() {

        Log.d("debug123"," photoDesc "+photoURL);

        mPhotoDesc.setText(photoDesc);
        mGPS.setText(gps);
        new LoadImageTask(this).execute(photoURL);

    }
    @Override
    public void onImageLoaded(Bitmap bitmap) {

        mImageView.setImageBitmap(bitmap);
    }
    public View getRootView(){
        return mRootView;
    }
}
