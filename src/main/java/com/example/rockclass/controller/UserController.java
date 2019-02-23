package com.example.rockclass.controller;


import com.alibaba.fastjson.JSON;
import com.example.rockclass.config.JwtPayload;

import com.example.rockclass.dao.UserDao;
import com.example.rockclass.entity.Student;
import com.example.rockclass.entity.Teacher;
import com.example.rockclass.entity.User;
import com.example.rockclass.exception.UserNotFoundException;
import com.example.rockclass.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    //登录
    @PostMapping(value = "/user/login",produces = "application/json; charset=utf-8")
    public void login(HttpServletRequest request, HttpServletResponse response)  {

    }

    //忘记密码

    @GetMapping("/user/password/{account}")
    public void forgetPassword(HttpServletRequest request, HttpServletResponse response,@PathVariable("account")String account)throws UserNotFoundException{
        userService.forgetPassword(account);
        response.setStatus(200);
    }

    //修改密码

    @PutMapping("/user/password")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response){
        JwtPayload jwtPayload = (JwtPayload) request.getAttribute("jwtPayload");
        userService.updatePassword(jwtPayload.getAccount(),request.getParameter("password"));
        response.setStatus(200);
    }


    @GetMapping("/user/information")
    public void getInformation(HttpServletRequest request, HttpServletResponse response)throws IOException{
        JwtPayload jwtPayload = (JwtPayload)request.getAttribute("jwtPayload");
        String account = jwtPayload.getAccount();
        User user=userDao.findByAccount(account);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(userService.getInformation(user.getAccount())));
    }


    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/student/active")
    public void activeStudent(HttpServletRequest request, HttpServletResponse response)throws IOException{
        JwtPayload jwtPayload = (JwtPayload)request.getAttribute("jwtPayload");
        String account = jwtPayload.getAccount();
        Student student=userService.activeStudent(account,request.getParameter("password"),request.getParameter("email"));
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(student));
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/teacher/active")
    public void activeTeacher(HttpServletRequest request, HttpServletResponse response)throws IOException{
        JwtPayload jwtPayload = (JwtPayload)request.getAttribute("jwtPayload");
        String account = jwtPayload.getAccount();
        Teacher teacher =userService.activeTeacher(account,request.getParameter("password"));
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(teacher));
    }

//修改邮箱

    @PutMapping("/user/email")
    public void updateEmail(HttpServletRequest request, HttpServletResponse response){
        JwtPayload jwtPayload = (JwtPayload) request.getAttribute("jwtPayload");
        userService.updateEmail(jwtPayload.getAccount(),request.getParameter("email"));
        response.setStatus(200);
    }



}
