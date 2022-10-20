package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;


    @Test
    void addselect() {
        UserDao userDao = context.getBean("awsUserDao",UserDao.class);


        String id = "5";
        userDao.add(new User(id,"hakjun","1234"));

        User user = userDao.findById("5");
        System.out.println(user.getName());
        assertEquals("hakjun",user.getName());

    }
}