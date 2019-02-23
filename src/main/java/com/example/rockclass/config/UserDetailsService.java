package com.example.rockclass.config;

import com.example.rockclass.dao.UserDao;
import com.example.rockclass.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Qualifier("customUserDetailsService")
public class UserDetailsService {

    @Autowired
    private UserDao userDao;

    /**
     * 获取用户信息
     *
     * @param account
     * @return
     * @throws UsernameNotFoundException
     */
    public User loadUserByUsername(String account) throws UsernameNotFoundException{
        return userDao.findByAccount(account);
    }
}
