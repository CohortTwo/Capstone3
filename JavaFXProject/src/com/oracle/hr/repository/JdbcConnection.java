package com.oracle.hr.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

    private static Connection connection = null;

    private JdbcConnection(){}

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:8888/ORCLPDB1.localdomain","hr","hr");
        }
        return connection;
    }


}
