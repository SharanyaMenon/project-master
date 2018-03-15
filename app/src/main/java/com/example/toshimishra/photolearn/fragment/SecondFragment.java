package com.example.toshimishra.photolearn.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toshimishra.photolearn.QuizTitle;
import com.example.toshimishra.photolearn.R;
import com.example.toshimishra.photolearn.SampleRecyclerAdapter;
import com.example.toshimishra.photolearn.Strings;

import java.util.ArrayList;

/**
 *
 */
public class SecondFragment extends BaseFragment implements SampleRecyclerAdapter.OnItemClickListener {

    private View mSecondFragmentView;
    private RecyclerView mRecyclerView;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSecondFragmentView = inflater.inflate(R.layout.fragment_second,container,false);

        mRecyclerView = (RecyclerView) mSecondFragmentView.findViewById(R.id.recy_quiz);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    //    ArrayList<String> list = new ArrayList<>();
  //      list.add("test1");
     //   list.add("test2");
        SampleRecyclerAdapter adapter = new SampleRecyclerAdapter(getContext(), Strings.string_frag2, QuizTitle.class);

        //设置adapter
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        return mSecondFragmentView;
    }

    @Override
    public void onItemClick(View view, int position, String name) {
        Toast.makeText(getContext(),"click "+position,Toast.LENGTH_SHORT).show();
    }
}
