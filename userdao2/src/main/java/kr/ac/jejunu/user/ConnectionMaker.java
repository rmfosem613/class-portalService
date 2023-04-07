package kr.ac.jejunu.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionMaker {
//    public ConnectionMaker() {
//    }//    abstract public Connection getConnection() throws ClassNotFoundException, SQLException;

    public Connection getConnection() throws ClassNotFoundException, SQLException;
//    {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        return DriverManager.getConnection("jdbc:mysql://localhost/halla", "halla", "hallapw");
//    }
}