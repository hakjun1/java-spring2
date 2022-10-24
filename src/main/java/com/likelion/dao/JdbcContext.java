package com.likelion.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//다른 dao (ex = HospitalDao에서도 같이 쓸수 있다)
public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }
//종속성이 dataSource에 있으므로 생성자로 받아준다
    public void workjdbcContextWithStatmentStrategy(StatementStrategy stmt) {
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

}
