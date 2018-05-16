package Service;

import Dao.ConMySQL;
import Models.Administrator;
import com.mysql.jdbc.Connection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminService {

    private Connection connection = null;
    private ConMySQL conMySQL = new ConMySQL();

    public boolean loginAdmin(int number, String psw){
        connection = conMySQL.getCon();
        String sql = "select Password from administrator where SerialNumber=" + number + ";";
        PreparedStatement pstmt;
        String pswdb = null;
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                pswdb = rs.getString(1);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(psw.trim().equals(pswdb.trim())) {
            String sql2 = "update administrator set LoginIP=?,LastLoginTime=? where SerialNumber=?;";
            PreparedStatement pstmt2;
            try {
                Date date = new Date();
                InetAddress addr = InetAddress.getLocalHost();
                pstmt2 = (PreparedStatement)connection.prepareStatement(sql2);
                pstmt2.setString(1, addr.getHostAddress());
                pstmt2.setDate(2, new java.sql.Date(date.getTime()));
                pstmt2.setInt(3, number);
                pstmt2.executeUpdate();
                pstmt2.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public int addAdministrator(String name, String psw){
        connection = conMySQL.getCon();
        int i = 0;
        String sql = "insert into administrator (Name,Password) values(?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, psw);
            i = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int deleteAdministrator(int serNum){
        connection = conMySQL.getCon();
        int i = 0;
        String sql = "delete from administrator where SerialNumber=" + serNum + ";";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public Administrator selectAdministrator(String column, String value) {
        connection = conMySQL.getCon();
        String sql = null;
        if (column == "Name"){
            sql = "select SerialNumber,Name,LoginIP,LastLoginTime from administrator where Name='" + value + "'";
        }else{
            sql = "select SerialNumber,Name,LoginIP,LastLoginTime from administrator where SerialNumber=" + value + ";";
        }
        PreparedStatement pstmt;
        Administrator admin = new Administrator();
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                admin.setSerialNum(rs.getInt(1));
                admin.setName(rs.getString(2));
                admin.setLoginIP(rs.getString(3));
                admin.setLastLoginTime(rs.getString(4));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

}
