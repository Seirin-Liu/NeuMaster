package com.example.neumaster;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.example.neumaster.base.activity.BaseActivity;
import com.example.neumaster.databinding.ActivityHealthBinding;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HealthActivity extends BaseActivity {
    private ActivityHealthBinding binding;

    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    public  String username;
    public  String password;

    static AlertDialog.Builder dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_health;
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_health);

        getSupportActionBar().setTitle("健康上报");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        username = SharepreferencesUtilSystemSettings.getValue(this,"username","");
        password = SharepreferencesUtilSystemSettings.getValue(this,"password","");

        try {
            int code = loginDaka();
            if (code==201){
                System.out.println("打卡成功");
                showDialog(this,"打卡成功!");
            }
            else {
                showDialog(this,"打卡失败，请检查网络和账号密码");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showDialog(this,"打卡失败，请检查网络和账号密码");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }


    /**
     * 登录并访问成绩页面 （通过cookie保持会话）
     * @throws IOException
     */
    public int loginDaka() throws IOException {

        try {


            String loginUrl =  "https://e-report.neu.edu.cn/login";
            String lt = "";
            String createUrl = "https://e-report.neu.edu.cn/notes/create";
            String notesUrl = "https://e-report.neu.edu.cn/api/notes";
            String token = "";
            String name = "";

            //webvpn需要登录两次
            //第一次登录
            Connection con = Jsoup.connect(loginUrl).header(USER_AGENT, USER_AGENT_VALUE);  // 获取connection 配置模拟浏览器
            Connection.Response rs = con.execute();                // 获取响应
            Document d1 = rs.parse();     // 通过Jsoup将返回信息转换为Dom树

            //校园网验证的lt是动态变化的，因此需要提前获取
            List<Element> eleList = d1.select("input");
            for (Element e : eleList) {
                // 获取lt
                if (e.attr("name").equals("lt")) {
                    System.out.println(e.attr("value"));
                    lt = e.attr("value");
                }
            }
            //Post数据
            Map<String, String> data = packageData(lt);

            // 设置cookie和post上面的map数据
            Connection.Response login1 = con.ignoreContentType(true).followRedirects(true).method(Connection.Method.POST)
                    .data(data).cookies(rs.cookies()).execute();

            //第二次访问create页面
            Connection con2 = Jsoup.connect(createUrl).header(USER_AGENT, USER_AGENT_VALUE);
            Connection.Response rs2 = con2.ignoreContentType(true).followRedirects(true).method(Connection.Method.GET).cookies(login1.cookies()).execute();
            Document d2 = rs2.parse();     // 通过Jsoup将返回信息转换为Dom树
            String d2s = d2.toString();

            String reg = "当前用户：\\s*(\\w+)\\s*";//指定为字符串的正则表达式必须首先被便以为此类的实例
            Pattern p =Pattern.compile(reg);//使用正则对象匹配字符串用于产生一个mather对象
            Matcher m =p.matcher(d2s);
            System.out.println(m.find());
    //        System.out.println(m.group(1));
            name = m.group(1);

            String reg1 = "name=\\\"_token\\\"\\s+value=\\\"([0-9a-zA-Z]+)\\\"";//指定为字符串的正则表达式必须首先被便以为此类的实例
            Pattern p1 =Pattern.compile(reg1);//使用正则对象匹配字符串用于产生一个mather对象
            Matcher m1 =p1.matcher(d2s);
            System.out.println(m1.find());
    //        System.out.println(m1.group(1));
            token = m1.group(1);

            Map<String, String> data2 = packageData2(token,name);

    //        第三次访问打卡页面
            Connection con3 = Jsoup.connect(notesUrl).header(USER_AGENT, USER_AGENT_VALUE);
            Connection.Response rs3 = con3.ignoreContentType(true).followRedirects(true).method(Connection.Method.POST)
                    .data(data2).cookies(rs2.cookies()).execute();
            return  rs3.statusCode();



        }
        catch (Exception e){

        }

        return  0 ;

//        return Jsoup.parse(login2.body());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
    }


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


    public Map<String,String> packageData2(String token,String name){
        Map<String, String> data = new HashMap<>();
        data.put("token",token);
        data.put("jibenxinxi_shifoubenrenshangbao","1");
        data.put("profile[xuegonghao]",username);
        data.put("profile[xingming]",name);
        data.put("profile[suoshubanji]","");
        data.put("jiankangxinxi_muqianshentizhuangkuang","正常");
        data.put("xingchengxinxi_weizhishifouyoubianhua","0");
        data.put("cross_city","无");
        data.put("qitashixiang_qitaxuyaoshuomingdeshixiang","");
        return data;
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




}