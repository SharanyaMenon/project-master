package com.example.toshimishra.photolearn;

import android.view.LayoutInflater;
import android.view.View;

/**
 * 作者：${Howard} on 2018/3/14 17:01
 *
 * @version 1.0
 *          说明：ViewPager的内容View的基类ViewPager's content is The base class of the  View class.
 *          Some simple encapsulation is done on this page.
 */
public abstract class BasePageView {

    private final View mRootView;
  ;

    public BasePageView(){

        mRootView = initView();
        initEvent();
    }

    public void initEvent() {

    }

    /**
     * 因为几个页面都不一样，因此定义为抽象的，让子类继承实现具体的View
     * Because several pages are different, the definition is abstract, and the subclass inheritance implements the concrete View.
     * @return
     */
    public abstract View initView();

    /**
     * 初始化数据，谁需要数据，重写即可。调用数据的位置在ViewPager加载的时候调用initData方法
     * Initializing the data, who needs the data, overrides it.
     The location of the call data calls the initData method when the ViewPager loads.
     */
    public void initData(){

    }

    /**
     * 调用这里可以到自定义的View。得到这个View，可以放入ViewPager里面作为它的一个孩子
     * So I'm going to call it here and I can go to the custom View.
     You get this View, you can put it in the ViewPager as a child of it.
     * @return
     */
    public View getRootView(){
        return mRootView;
    }

}
