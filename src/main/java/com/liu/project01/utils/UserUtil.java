package com.liu.project01.utils;//@date :2022/5/5 13:46


import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.project01.pojo.User;
import com.liu.project01.vo.RespBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

////生成用户工具类
//public class UserUtil {
//
//
//    private static void createUser(int count) throws Exception {
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            User user = new User();
//            user.setId(18000000000L + i);
//            user.setNickname("user" + i);
//            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSlat()));
//            user.setSlat("1a2b3c4d");
//            user.setLoginCount(1);
//            user.setRegisterDate(new Date());
//            users.add(user);
//        }
//        System.out.println("create account:");
//        //插入数据库
//
//
//        Connection conn = getConn();
//
//        String sql = "insert into t_user(login_count,nickname,register_date,slat,password,id) values(?,?,?,?,?,?)";
//
//        PreparedStatement ps = conn.prepareStatement(sql);
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            ps.setInt(1, user.getLoginCount());
//            ps.setString(2, user.getNickname());
//            ps.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
//            ps.setString(4, user.getSlat());
//            ps.setString(5, user.getPassword());
//            ps.setLong(6, user.getId());
//            ps.addBatch();
//        }
//        ps.execute();
//        ps.clearParameters();
//        conn.close();
//        System.out.println("insert to DB:");
//        String urlString = "http://localhost:8080/login/toLogin";
//        File file = new File("C:\\Users\\LiuZhengWei\\Desktop\\config.txt");
//        if (file.exists()) {
//            file.delete();
//        }
//        RandomAccessFile raf = new RandomAccessFile(file, "rw");
//        raf.seek(0);
//
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            URL url = new URL(urlString);
//            HttpURLConnection co = (HttpURLConnection) url.openConnection();
//            co.setRequestMethod("POST");
//            co.setDoOutput(true);
//            OutputStream out = co.getOutputStream();
//            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFromPass("123456");
//            out.write(params.getBytes());
//            out.flush();
//            InputStream in = co.getInputStream();
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            byte buff[] = new byte[1024];
//            int len = 0;
//            while ((len = in.read(buff)) >= 0) {
//                bout.write(buff, 0, len);
//            }
//            in.close();
//            bout.close();
//
//            String response = new String(bout.toByteArray());
//
//
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            RespBean respBean = objectMapper.readValue(response, RespBean.class);
//            String userTicket = (String) respBean.getObject();
//
//            System.out.println("create userTicket :" + user.getId());
//            String row = user.getId() + "," + userTicket;
//            raf.seek(raf.length());
//            raf.write(row.getBytes());
//            raf.write("\r\n".getBytes());
//            System.out.println("write to file :" + user.getId());
//        }
//        raf.close();
//        System.out.println("create over!");
//    }
//
//
//    private static Connection getConn() throws Exception {
//        String url = "jdbc:mysql://192.168.163.56:3306/project01?useUnicode=true&characterEncoding=utf-8&userSSL=false";
//        String username = "root";
//        String password = "123456";
//        String driver = "com.mysql.cj.jdbc.Driver";
//
//        Class.forName(driver);
//        return DriverManager.getConnection(url, username, password);
//    }
//
//    public static void main(String[] args) throws Exception {
//        createUser(5000);
//    }
//
//}


public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>(count);
        //生成用户
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(19000000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSlat("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSlat()));
            users.add(user);
        }
        System.out.println("create user");
//        //插入数据库
//        Connection conn = getConn();
//        String sql = "insert into t_user(login_count, nickname, register_date, slat, password, id)values(?,?,?,?,?,?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            pstmt.setInt(1, user.getLoginCount());
//            pstmt.setString(2, user.getNickname());
//            pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
//            pstmt.setString(4, user.getSlat());
//            pstmt.setString(5, user.getPassword());
//            pstmt.setLong(6, user.getId());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        conn.close();
        System.out.println("create user finished,create config.txt");
        //登录，生成userTicket
        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("C:\\\\Users\\\\LiuZhengWei\\\\Desktop\\\\config.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFromPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = ((String) respBean.getObject());
            System.out.println("create userTicket : " + user.getId());

            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");
    }

    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://192.168.163.56:3306/project01?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}