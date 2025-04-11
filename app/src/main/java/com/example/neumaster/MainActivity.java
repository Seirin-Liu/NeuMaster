package com.example.neumaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.neumaster.base.activity.BaseActivity;
import com.example.neumaster.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    private EditText username;
    private EditText password;
    private Spinner spinner;
    static AlertDialog.Builder dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        username = binding.username;
        password = binding.password;
        spinner = binding.spinner;
        getSupportActionBar().setTitle("主页");




        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("全部");
        spinners.add("2019-2020 秋季");
        spinners.add("2019-2020 春季");
        spinners.add("2020-2021 秋季");
        spinners.add("2020-2021 春季");
        spinners.add("2021-2022 秋季");
        spinners.add("2021-2022 春季");
        spinners.add("2022-2023 秋季");
        spinners.add("2022-2023 春季");
        spinners.add("2023-2024 秋季");
        spinners.add("2023-2024 春季");
        spinners.add("2024-2025 秋季");
        spinners.add("2024-2025 春季");
        spinners.add("2025-2026 秋季");
        spinners.add("2025-2026 春季");
        spinners.add("2026-2027 秋季");
        spinners.add("2026-2027 春季");
        spinners.add("2027-2028 秋季");
        spinners.add("2027-2028 春季");
        //设置ArrayAdapter内置的item样式-这里是单行显示样式
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        //这里设置的是Spinner的样式 ， 输入 simple_之后会提示有4人，如果专属spinner的话应该是俩种，在特殊情况可自己定义样式
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //设置Adapter了
        spinner.setAdapter(adapter);
        spinner.setSelection(SharepreferencesUtilSystemSettings.getValue(this,"selection",0));
        username.setText(SharepreferencesUtilSystemSettings.getValue(this,"username",""));
        password.setText(SharepreferencesUtilSystemSettings.getValue(this,"password",""));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(FirstFragment.this.getContext(),"开始查询,请等待",Toast.LENGTH_SHORT).show();

                String usernameText  = username.getText().toString();
                String passwordText  = password.getText().toString();
                if(usernameText.length()==0||usernameText==null){
                    showDialog(MainActivity.this,"学号不能为空！");
                    return;
                }
                else if (passwordText.length()==0||passwordText==null){
                    showDialog(MainActivity.this,"密码不能为空！");
                    return;
                }
                else {
//                    Animation mAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotaterepeat);
//                    LinearInterpolator lin = new LinearInterpolator();
//                    mAnimation.setInterpolator(lin);
//                    binding.head.startAnimation(mAnimation);

                    SharepreferencesUtilSystemSettings.putValue(MainActivity.this,"username",usernameText);
                    SharepreferencesUtilSystemSettings.putValue(MainActivity.this,"password",passwordText);
                    SharepreferencesUtilSystemSettings.putValue(MainActivity.this,"selection",spinner.getSelectedItemPosition());
                    startActivity(new Intent(MainActivity.this,ScoreActivity.class));
                    binding.buttonFirst.setEnabled(false);

                }

            }
        });
        binding.buttonSecond.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String usernameText  = username.getText().toString();
                String passwordText  = password.getText().toString();
//                Animation mAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotaterepeat);
//                LinearInterpolator lin = new LinearInterpolator();
//                mAnimation.setInterpolator(lin);
//                binding.head.startAnimation(mAnimation);
                if(usernameText.length()==0||usernameText==null){
                    showDialog(MainActivity.this,"学号不能为空！");
                    return;
                }
                else if (passwordText.length()==0||passwordText==null){
                    showDialog(MainActivity.this,"密码不能为空！");
                    return;
                }
                else {


                    SharepreferencesUtilSystemSettings.putValue(MainActivity.this,"username",usernameText);
                    SharepreferencesUtilSystemSettings.putValue(MainActivity.this,"password",passwordText);
//                    SharepreferencesUtilSystemSettings.putValue(MainActivity.this,"selection",spinner.getSelectedItemPosition());
                    startActivity(new Intent(MainActivity.this,HealthActivity.class));
                    binding.buttonSecond.setEnabled(false);

                }

            }
        });
    }

    public static void showDialog(Context context, String msg){

        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("提示");
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.buttonFirst.setEnabled(true);
        binding.buttonSecond.setEnabled(true);
    }
}