package com.example.rockclass.service;


import com.example.rockclass.dao.StudentDao;
import com.example.rockclass.dao.TeacherDao;
import com.example.rockclass.dao.UserDao;
import com.example.rockclass.entity.Student;
import com.example.rockclass.entity.Teacher;
import com.example.rockclass.entity.User;
import com.example.rockclass.exception.UserNotFoundException;
import com.example.rockclass.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private EmailService emailService;

    public User login(User user) throws UserNotFoundException {

        User val = userDao.findByAccount(user.getAccount());
        if (val==null){
            throw new UserNotFoundException("用户名不存在");
        }
        else {
            if (val.getPassword().equals(user.getPassword())) {
                return val;
            }
            return null;
        }

    }

    public UserVO getInformation(String account){
        User user = userDao.findByAccount(account);
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setAccount(user.getAccount());
        userVO.setName(user.getName());
        userVO.setRole(user.getRole());
        if(user.getRole().equals("student")){
            userVO.setEmail(studentDao.selectByAccount(account).getEmail());
        }
        else if(user.getRole().equals("teacher")){
            userVO.setEmail(teacherDao.selectByAccount(account).getEmail());
        }
        return userVO;
    }

    public void forgetPassword(String account)throws UserNotFoundException{
        String email;
        String password;
        if(studentDao.selectByAccount(account)==null){
            if (teacherDao.selectByAccount(account)==null)
                throw new UserNotFoundException();
            else {
                email = teacherDao.selectByAccount(account).getEmail();
                password = teacherDao.selectByAccount(account).getPassword();

            }
        }else {
            email=studentDao.selectByAccount(account).getEmail();
            password = studentDao.selectByAccount(account).getPassword();
        }
       try{
            emailService.sentEmail(email,password);
       }catch (Exception e){
            e.printStackTrace();
       }
    }

    public void updatePassword(String account, String password) {
        if (studentDao.selectByAccount(account)!=null){
            studentDao.updatePassword(account,password);
        }
        else {
        teacherDao.updatePassword(account,password);
        }
    }
    public void updateEmail(String account, String email) {
        if (studentDao.selectByAccount(account)!=null){
            studentDao.updateEmail(account,email);
        }
        else {
            teacherDao.updateEmail(account,email);
        }
    }

    public Student activeStudent(String account, String password, String email) {
        return studentDao.active(account,password,email);
    }

    public Teacher activeTeacher(String account, String password) {
        return teacherDao.active(account,password);
    }


    public Teacher selectTeacherByTeacherAccount(String teacherAccount){return  teacherDao.selectByAccount(teacherAccount);}


    public Student selectByStudentAccount(String account) {
        return studentDao.selectByAccount(account);
    }
}
