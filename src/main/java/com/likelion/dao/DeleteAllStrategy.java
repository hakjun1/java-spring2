package com.likelion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy{
    @Override
    public PreparedStatement makePreParedStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("delete from users");
    }
}
