package com.example.neumaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.neumaster.databinding.ItemBinding;
import com.example.neumaster.databinding.ItemListClassBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CruseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Cruse> mDatas;
    private Map<String, Integer> colorMap = new HashMap<>();

    public CruseAdapter(Context context,List<Cruse> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_class, parent, false);
//        Log.i("222","s");
        return new NormalHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder h = (NormalHolder) holder;
        h.name.setText(mDatas.get(position).getName());
        h.point.setText(mDatas.get(position).getTerm()+"     学分："+mDatas.get(position).getPoint());
        h.score.setText(mDatas.get(position).getScore());
        h.score1.setText("平时："+mDatas.get(position).getScore1());
        h.score2.setText("期中："+mDatas.get(position).getScore2());
        h.score3.setText("期末："+mDatas.get(position).getScore3());
        h.score4.setText("最终："+mDatas.get(position).getScore4());
        h.imageView.addView(new RoundTextView(mContext,25,getColor(colorMap, mDatas.get(position).getName())));
//        Log.i("222","s");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {

        public TextView name,point,score,score1,score2,score3,score4;
        public FrameLayout imageView;
        public NormalHolder(View itemView) {
            super(itemView);
            ItemListClassBinding binding = ItemListClassBinding.bind(itemView);
            name = binding.txName;
            point = binding.txPoint;
            score = binding.txScore;
            score1 = binding.score1;
            score2 = binding.score2;
            score3 = binding.score3;
            score4 = binding.score4;
            imageView = binding.imageView;

        }

    }


    private int color[] = {
            R.color.one, R.color.two, R.color.three,
            R.color.four, R.color.five, R.color.six,
            R.color.seven, R.color.eight, R.color.nine,
            R.color.ten, R.color.eleven, R.color.twelve,
            R.color.thirteen, R.color.fourteen, R.color.fifteen
    };

    private int getColor(Map<String, Integer> map, String name) {
        Integer tip = map.get(name);
        if (null != tip) {
            return tip;
        } else {
            int i = mContext.getResources().getColor(color[map.size() % color.length]);
            map.put(name, i);
            return i;
        }
    }





}