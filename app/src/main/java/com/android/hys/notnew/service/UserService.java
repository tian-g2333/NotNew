package com.android.hys.notnew.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserService {
    public static boolean signIn(String name, String password, String phone) {
        MyThread myThread = new MyThread("http://10.0.2.2:8080/MyWeb/SignIn",name,password,phone);
        try
        {
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return myThread.getResult();
    }

    public static boolean signUp(String name, String password, String phone) {
        MyThread myThread = new MyThread("http://10.0.2.2:8080/MyWeb/SignUp",name,password,phone);
        try
        {
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return myThread.getResult();
    }
}

class MyThread extends Thread
{
    private String path;
    private String name;
    private String password;
    private String phone;
    private boolean result = false;

    public MyThread(String path,String name,String password, String phone)
    {
        this.path = path;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
    @Override
    public void run()
    {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
            httpURLConnection.setReadTimeout(8000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post

            String data = "name=" + URLEncoder.encode(name, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8") + "&phone=" + URLEncoder.encode(phone, "utf-8");//设置数据
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            httpURLConnection.setDoOutput(true);//允许输出
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));//写入数据
            result = (httpURLConnection.getResponseCode() == 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getResult()
    {
        return result;
    }
}


