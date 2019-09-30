package com.controller;

import com.util.DatabaseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/toLogin")
    public static String toLogin(Model model){
        //request.getAttribute("photoPath");
        return "login";
    }

    @RequestMapping(value = "/toHome")
    public static String upload(String username, String password, Model model){
        String messgse = "success";
        String page = "login";
        Connection dbConn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String photoPath = "/uploadFiles/1.jpg";

        if(model.containsAttribute("username") && model.containsAttribute("password")){
            Map map = model.asMap();
            username = (String) map.get("username");
            password = (String) map.get("password");
        }

        try {
            /*String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
            // 连接服务器和数据库ServletUser
            String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=mydatabase";
            String userName = "sa"; // 默认用户名
            String userPwd = "123"; // 密码
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);*/
            dbConn = DatabaseUtils.getConnection(dbConn);
            String sql = "select user_photo from userInfo where user_name = ? and user_password = ?";
            pstmt = dbConn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                /*sql = "select user_photo from userInfo where user_id = '1'";
                pstmt = dbConn.prepareStatement(sql);
                resultSet = pstmt.executeQuery();*/
                String path = resultSet.getString("user_photo");
                if(StringUtils.isNotEmpty(path)){
                    photoPath = path;
                }
                //model.addAttribute("photoPath",photoPath);
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                page = "home";
            }else{
                messgse = "用户名或密码错误！";
            }
            model.addAttribute("photoPath",photoPath);
            model.addAttribute("message",messgse);
        }catch (Exception e){
            e.printStackTrace();
        }
        return page;
    }

    @RequestMapping(value = "/singleSignOn",method = RequestMethod.POST)
    public static RedirectView singleSignOn(HttpServletRequest request, RedirectAttributes attr){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        attr.addFlashAttribute("username",username);
        attr.addFlashAttribute("password",password);
        RedirectView redirectView = new RedirectView("/login/toHome", true /*是否使用相对路径*/, false/* 兼容http1.0*/ , false/* 是否暴露查询参数*/);
        //upload(username,password,null);
        //return "redirect:/login/toHome?username="+username+"&password="+password;
        return redirectView;
    }
}
