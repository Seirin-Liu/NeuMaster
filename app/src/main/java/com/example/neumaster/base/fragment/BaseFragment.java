package com.example.neumaster.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected LayoutInflater mInflater;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            initView();
            initData();
            initEvent();
        }
        return mRootView;
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
    protected abstract void initEvent();
}
