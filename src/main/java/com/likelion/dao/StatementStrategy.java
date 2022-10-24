package com.likelion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

    public PreparedStatement makePreParedStatement(Connection connection) throws SQLException;
}
