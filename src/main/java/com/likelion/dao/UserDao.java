package com.likelion.dao;

import com.likelion.domain.User;
import com.mysql.cj.result.Row;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            return user;
        }
    };


    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id,name,password) values(?,?,?);", user.getId(), user.getName(), user.getPassword());
    }


    public User get(String id)  {
        String sql = "SELECT * FROM users WHERE id =?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);

    }


    public void deleteAll()  {
        this.jdbcTemplate.update("delete from users");//쿼리만 넘기는 형태

    }

    public int getCount()  {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) from users;", Integer.class);
    }
    public List<User> getAll() {
        String sql = "SELECT * FROM users order by id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

}

