package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) {
        Connection c;
        try {
            c = connectionMaker.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        Connection c;
        try {
            c = connectionMaker.makeConnection();
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));

            }
            ;

            rs.close();
            pstmt.close();
            c.close();

            //없으면 exception
            if (user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            c = connectionMaker.makeConnection();
            pstmt = c.prepareStatement("delete from users");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {//error가 나도 실행되는 블럭
             if (pstmt != null) {
                 try {
                     pstmt.close();
                 } catch (SQLException e) {
                 }
             }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public int getCount() throws SQLException {

        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = connectionMaker.makeConnection();
            pstmt = c.prepareStatement("select count(*) from users");
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }

            }
        }


    }
}
