package com.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/toLogin")
    public static String toLogin(Model model){
        Connection dbConn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String photoPath = "";
        try {
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
            // 连接服务器和数据库ServletUser
            String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=mydatabase";
            String userName = "sa"; // 默认用户名
            String userPwd = "123"; // 密码
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            pstmt = dbConn.prepareStatement("select user_photo from userInfo where user_id = '1'");
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                photoPath = resultSet.getString("user_photo");
            }
            model.addAttribute("photoPath",photoPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        //request.getAttribute("photoPath");
        return "test";
    }

    @RequestMapping("/toUpload")
    public static void upload(){
        String str = "ff";
        System.out.println();
        System.out.println(str);
    }
}
