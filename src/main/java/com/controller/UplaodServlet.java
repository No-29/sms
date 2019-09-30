package com.controller;

import com.util.DatabaseUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:TODO
 * @Author hjsoft
 * @Date 2019/9/29 13:18
 * @Version V1.0
 **/
@Controller
@WebServlet(urlPatterns = "/uploadServlet")
//@RequestMapping(value = "/uploadServlet")
public class UplaodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    //@RequestMapping(value = "/post",method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("username");
        String password = (String)session.getAttribute("password");
        String filePath = "";
        String fileName = "";
        String ext = "";
        Connection dbConn = null;
        PreparedStatement pstmt = null;
        try {
            /*String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
            // 连接服务器和数据库ServletUser
            String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=mydatabase";
            String userName = "sa"; // 默认用户名
            String userPwd = "123"; // 密码
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);*/
            //System.out.println("Connection Successful!"); // 如果连接成功

            dbConn = DatabaseUtils.getConnection(dbConn);
            // 配置上传参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(req);

            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    String[] fileNameArr = item.getName().split("\\.");
                    fileName = fileNameArr[0];
                    ext = "." + fileNameArr[1];
                    if(StringUtils.isEmpty(fileName)){
                        continue;
                    }
                    //定义上传文件的存放路径
                    String path = req.getServletContext().getRealPath("/uploadFiles");
                    //定义上传文件的完整路径
                    BASE64Encoder base64 = new BASE64Encoder();
                    fileName = base64.encode(fileName.getBytes());
                    //fileName = URLEncoder.encode(fileName,"utf-8");
                    filePath = String.format("%s/%s%s",path, fileName, ext);
                    File storeFile = new File(filePath);
                    File uploadDir = new File(filePath.replaceAll(fileName,""));
                    if(!uploadDir.exists()){
                        uploadDir.mkdir();
                    }
                    // 在控制台输出文件的上传路径
                    //System.out.println(filePath);
                    // 保存文件到硬盘
                    item.write(storeFile);
                }
            }

            filePath = "/uploadFiles/" + fileName + ext;
            Statement stmt = dbConn.createStatement();
            pstmt = dbConn.prepareStatement("update userInfo set userInfo.user_photo = ? where user_name = ? and user_password = ?");
            pstmt.setString(1,filePath);
            pstmt.setString(2,username);
            pstmt.setString(3,password);
            pstmt.execute();

            req.setAttribute("username",username);
            req.setAttribute("password",password);
            req.getRequestDispatcher("/login/singleSignOn").forward(req,resp);
            List resource = new ArrayList();
            resource.add(dbConn);
            resource.add(pstmt);
            DatabaseUtils.close(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeResource(PreparedStatement pstmt,Connection conn){
        try {
            if(pstmt != null){
                pstmt.close();
            }
            if(conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}