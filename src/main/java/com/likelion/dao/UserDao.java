package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;


    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update("INSERT INTO users(id,name,password) values(?,?,?);",user.getId(),user.getName(),user.getPassword());
    }


    public User get(String id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id =?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);

    }


    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");//쿼리만 넘기는 형태

    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) from users;", Integer.class);
    }

}

