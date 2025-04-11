package com.example.neumaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.neumaster.base.activity.BaseActivity;
import com.example.neumaster.databinding.ActivityMainBinding;
import com.example.neumaster.databinding.ActivityScoreBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



public class ScoreActivity extends BaseActivity {

    private ActivityScoreBinding binding;


    private RecyclerView list;
    private TextView avg;

    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    public  String username;
    public  String password;
    public  int selection;

    static AlertDialog.Builder dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score;
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score);
        list = binding.list;
        avg = binding.avg;
        getSupportActionBar().setTitle("成绩");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        username = SharepreferencesUtilSystemSettings.getValue(this,"username","");
        password = SharepreferencesUtilSystemSettings.getValue(this,"password","");
        selection = SharepreferencesUtilSystemSettings.getValue(this,"selection",0);
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
        String key = spinners.get(selection);
        try {
            List<Cruse> cruses = getCruse(key);
            CruseAdapter adapter  = new CruseAdapter(this,cruses);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            layoutManager.setStackFromEnd(true);
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initEvent() {

    }

    public List<Cruse> getCruse(String key) throws IOException {
        List<Cruse> cruses = new ArrayList<>();
        Document gradeBody = login();
        //抓取行
        List<Element> coursesElements = gradeBody.select("tr");
        if(coursesElements.size()==0){
            showDialog(this,"学号密码输入错误或者没有成绩信息!");
            return cruses;
        }
        //删除表头
        coursesElements.remove(0);
        float allClassPoints = 0;
        float allScore = 0;
        if(key.equals("全部")) {
            for (Element course : coursesElements) {
                //对每一行抓取列
                List<Element> tdElements = course.select("td");
                //拆分后按顺序：学年学期 课程代码 课程序号 课程名称 课程类别 是否选修 学分 平时成绩 期中成绩 期末成绩 总评成绩 最终 绩点 考试情况
                //通过index输出
//            System.out.println(tdElements.get(3).text());
                allClassPoints += Float.parseFloat(tdElements.get(6).text());
//            System.out.println(tdElements.get(12).text());
                //计算平均绩点的方法：sum(单科分数*学分)/总学分
                allScore += Float.parseFloat(tdElements.get(12).text()) * Float.parseFloat(tdElements.get(6).text());
                Cruse cruse = new Cruse(tdElements.get(3).text(), tdElements.get(6).text(), tdElements.get(12).text());
                cruse.setScore1(tdElements.get(7).text());
                cruse.setScore2(tdElements.get(8).text());
                cruse.setScore3(tdElements.get(9).text());
                cruse.setScore4(tdElements.get(10).text());
                cruse.setTerm(tdElements.get(0).text());
                cruses.add(cruse);
            }
        }
        else{
            for (Element course : coursesElements) {
                //对每一行抓取列
                List<Element> tdElements = course.select("td");
                //拆分后按顺序：学年学期 课程代码 课程序号 课程名称 课程类别 是否选修 学分 平时成绩 期中成绩 期末成绩 总评成绩 最终 绩点 考试情况
                //通过index输出
//            System.out.println(tdElements.get(3).text());
                allClassPoints += Float.parseFloat(tdElements.get(6).text());
//            System.out.println(tdElements.get(12).text());
                //计算平均绩点的方法：sum(单科分数*学分)/总学分
                allScore += Float.parseFloat(tdElements.get(12).text()) * Float.parseFloat(tdElements.get(6).text());
                Cruse cruse = new Cruse(tdElements.get(3).text(), tdElements.get(6).text(), tdElements.get(12).text());
                cruse.setScore1(tdElements.get(7).text());
                cruse.setScore2(tdElements.get(8).text());
                cruse.setScore3(tdElements.get(9).text());
                cruse.setTerm(tdElements.get(0).text());
                cruse.setScore4(tdElements.get(10).text());
                if(cruse.getTerm().equals(key)){
                    cruses.add(cruse);
                }
            }
        }
        avg.setText(" 平均绩点："+String.format("%.4f", allScore/allClassPoints));

        return cruses;
    }

    /**
     * 组装请求数据
     * @param lt lt
     * @return 数据map
     */
    public Map<String,String> packageData(String lt){
        Map<String, String> data = new HashMap<>();
        data.put("rsa",username+password+lt);
        data.put("ul",username.length()+"");
        data.put("pl",password.length()+"");
        data.put("lt",lt);
        data.put("execution","e1s1");
        data.put("_eventId","submit");
        return data;
    }


    /**
     * 登录并访问成绩页面 （通过cookie保持会话）
     * @return 数据body
     * @throws IOException
     */
    public  Document login() throws IOException {
        String loginUrl1 = "https://webvpn.neu.edu.cn/https/62304135386136393339346365373340a0e0b72cc4cb43c8bc1d6f66c806db/tpass/login?service=https%3A%2F%2Fwebvpn.neu.edu.cn%2Flogin%3Fcas_login%3Dtrue";
        String loginUrl2 = "https://webvpn.neu.edu.cn/http/62304135386136393339346365373340e2b0fd71d8941093ab4e2527/eams/teach/grade/course/person!historyCourseGrade.action?projectType=MAJOR";
        String lt1 = "";
        String lt2 = "";

        //webvpn需要登录两次
        //第一次登录
        Connection con = Jsoup.connect(loginUrl1).header(USER_AGENT, USER_AGENT_VALUE);  // 获取connection 配置模拟浏览器
        Connection.Response rs = con.execute();                // 获取响应
        Document d1 = rs.parse();     // 通过Jsoup将返回信息转换为Dom树

        //校园网验证的lt是动态变化的，因此需要提前获取
        List<Element> eleList = d1.select("input");
        for (Element e : eleList) {
            // 获取lt
            if (e.attr("name").equals("lt")) {
                System.out.println(e.attr("value"));
                lt1 = e.attr("value");
            }
        }
        //Post数据
        Map<String, String> data = packageData(lt1);

        // 设置cookie和post上面的map数据
        Connection.Response login1 = con.ignoreContentType(true).followRedirects(true).method(Connection.Method.POST)
                .data(data).cookies(rs.cookies()).execute();

        //第二次登录
        Connection con2 = Jsoup.connect(loginUrl2).header(USER_AGENT, USER_AGENT_VALUE);
        Connection.Response rs2 = con2.ignoreContentType(true).followRedirects(true).method(Connection.Method.GET).cookies(login1.cookies()).execute();
        Document d2 = rs2.parse();     // 通过Jsoup将返回信息转换为Dom树

        List<Element> eleList2 = d2.select("input");
        for (Element e : eleList2) {
            // 获取lt
            if (e.attr("name").equals("lt")) {
                System.out.println(e.attr("value"));
                lt2 = e.attr("value");
            }
        }
        Map<String, String> data2 = packageData(lt2);

        // 设置cookie和post上面的map数据
        Connection.Response login2 = con2.ignoreContentType(true).followRedirects(true).method(Connection.Method.POST)
                .data(data2).cookies(rs2.cookies()).execute();
        return Jsoup.parse(login2.body());
    }

    public static void showDialog(Context context, String msg){

        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("提示");
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activity activity = (Activity)context;
                activity.finish();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}