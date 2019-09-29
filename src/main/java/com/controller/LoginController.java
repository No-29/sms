package com.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/toLogin")
    public static String toLogin(){
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
