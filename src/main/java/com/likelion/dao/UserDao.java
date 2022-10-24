package com.likelion.dao;

import com.likelion.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final DataSource dataSource;
    //final
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatmentStrategy(StatementStrategy stmt) {
    Connection c = null;
    PreparedStatement ps = null;
    try {
        c = dataSource.getConnection();
        ps = stmt.makePreParedStatement(c);
        ps.executeUpdate();
    } catch (SQLException e) {
    } finally {
        if (ps != null) {
            try {
                ps.close();
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



    public void add(User user) throws SQLException {
      jdbcContextWithStatmentStrategy(new StatementStrategy() {
          public PreparedStatement makePreParedStatement(Connection c) throws SQLException {
              PreparedStatement ps = c.prepareStatement("INSERT INTO users(id,name,password) values(?,?,?)");
              ps.setString(1, user.getId());
              ps.setString(2, user.getName());
              ps.setString(3, user.getPassword());
              return ps;
          }
        });
    }




    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT *FROM users WHERE id=?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public void deleteAll() throws SQLException {
        jdbcContextWithStatmentStrategy(new StatementStrategy() {
            public PreparedStatement makePreParedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("delete from users");
            }
        });

    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("SELECT COUNT(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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
