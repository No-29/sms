package com.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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
        String filePath = "";
        String fileName = "";
        try {
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
                    fileName = item.getName();
                    if(StringUtils.isEmpty(fileName)){
                        continue;
                    }
                    //定义上传文件的存放路径
                    String path = req.getServletContext().getRealPath("/uploadFiles");
                    //定义上传文件的完整路径
                    filePath = String.format("%s/%s",path,fileName);
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

            filePath = "/uploadFiles/" + fileName;
            HttpSession session = req.getSession();
            req.setAttribute("photoPath",filePath);
            req.getRequestDispatcher("/login/toLogin").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}