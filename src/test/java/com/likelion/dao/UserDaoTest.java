package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith,@ContextConfiguration을해야 @Autowired를 사용할수있다
@ExtendWith(SpringExtension.class)//스프링에있는 기능을 junit5에서 쓰기위함
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired//싱글톤으로 한번만 생성(@BeforeEach)
    ApplicationContext context;
    UserDao userDao;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setup(){
        this.userDao = context.getBean("awsUserDao",UserDao.class);
        //System.out.println("before each");
        this.user1 = new User("1","hakjun1","123");
        this.user2 = new User("2","hakjun2","456");
        this.user3 = new User("3","hakjun3","789");


    }


    @Test
    void addAndget() throws SQLException {

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        System.out.println(userDao.getCount());


        User user = userDao.findById(user1.getId());
//        System.out.println(user.getId());
//        System.out.println(user.getName());
//        System.out.println(user.getPassword());
        assertEquals(user1.getName(),user.getName());
        assertEquals(user1.getPassword(),user.getPassword());
    }

    @Test
    void count() throws SQLException {

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        userDao.add(user2);
        assertEquals(2,userDao.getCount());
        userDao.add(user3);
        assertEquals(3,userDao.getCount());
    }

    @Test
    void findById() {
       assertThrows(EmptyResultDataAccessException.class,()->{
           userDao.findById("30");
       });
        //rs.next()에서 null발생

    }
}