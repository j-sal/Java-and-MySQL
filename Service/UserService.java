package Service;

import Dao.ConMySQL;
import Models.User;
import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserService {

    private Connection connection = null;
    private ConMySQL conMySQL = new ConMySQL();

    public void closeCon(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int registUser(String number, String name, String psw, String address){
        connection = conMySQL.getCon();
        int i = 0;
        String sql = "insert into users (Number,Name,Password,Address,Registrationtime) values(?,?,?,?,?)";
        Date date = new Date();
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, psw);
            pstmt.setString(4, address);
            pstmt.setDate(5, new java.sql.Date(date.getTime()));
            i = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public boolean loginUser(String number, String psw){
        connection = conMySQL.getCon();
        String sql = "select Password from users where Number=" + number + ";";
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(psw.trim().equals(pswdb.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public User viewUser(String number){
        connection = conMySQL.getCon();
        String sql = "select Number,Name,Address,Registrationtime from users where Number ='" + number + "';";
        PreparedStatement pstmt;
        User user = new User();
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            //int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                user.setNumber(rs.getString(1));
                user.setName(rs.getString(2));
                user.setAddress(rs.getString(3));
                user.setRegistrationTime(rs.getDate(4));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
