package com.likelion.domain;

import com.likelion.dao.LocalConnectionMaker;
import com.likelion.dao.UserDao;
import com.likelion.dao.UserDaoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {


    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user;
    User user1 = new User("3", "hakjun", "12344");
    User user2 = new User("4", "hakjun2", "12344");
    User user3 = new User("5", "hakjun2", "12344");

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
    }


    @Test
    void addAndget() throws SQLException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
        userDao.add(user1);
        userDao.add(user2);
        user = userDao.get("3");
        user = userDao.get("4");
        assertEquals(2, userDao.getCount());
    }

    @Test
    @DisplayName("없을때 빈 리스트 리턴하는지, 있을때 개수만큼 리턴하는지")
    void getAllTest(){
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertEquals(0,users.size());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        users = userDao.getAll();
        assertEquals(3,users.size());
    }
}