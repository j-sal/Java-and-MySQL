package Dao;

import com.mysql.jdbc.Connection;
import java.sql.*;

public  class ConMySQL {
    private Connection connection = null;

    public ConMySQL(){

    }

    public static Connection getCon() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/onlineorderingsystem?autoReconnect=true&amp;‌​useSSL=false;";
        String username = "root";
        String password = "taurus";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader
            conn = (Connection) DriverManager.getConnection(url, username, password);
            //System.out.println("db connection successful！！！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
