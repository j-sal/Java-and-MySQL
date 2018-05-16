package Service;

import Dao.ConMySQL;
import Models.Order;
import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OrderService {

    private Connection connection = null;
    private ConMySQL conMySQL = new ConMySQL();

    public void closeCon() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List selectOrders(String number) {
        connection = conMySQL.getCon();
        String sql = "select SerialNumber from orders where Number ='" + number + "';";
        PreparedStatement pstmt;
        List list = new LinkedList();
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order viewOrder(int serNum) {
        connection = conMySQL.getCon();
        String sql = "select SerialNumber,Number,Status,Address,OrderTime,Remarks from orders where SerialNumber =" + serNum + ";";
        PreparedStatement pstmt;
        Order order = new Order();
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                order.setSerialNum(rs.getInt(1));
                order.setNumber(rs.getString(2));
                order.setStatus(rs.getString(3));
                order.setAddress(rs.getString(4));
                order.setOrdertime(rs.getDate(5));
                order.setRemarks(rs.getString(6));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public int determineOrder(String number, String address, String remarks, List cuisine) {
        connection = conMySQL.getCon();
        int orderNum = 0;
        String sql = "insert into orders(Number,Status,Address,OrderTime,Remarks) values(?,?,?,?,?);";
        String sql2 = "insert into order_menu(SerialNumber,Dishname) values(?,?);";
        Date date = new Date();
        PreparedStatement pstmt,pstmt2;
        try {
            connection.setAutoCommit(false);
            pstmt = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, number);
            pstmt.setString(2, "Unpaid");
            pstmt.setString(3, address);
            pstmt.setDate(4, new java.sql.Date(date.getTime()));
            pstmt.setString(5, remarks);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            orderNum = rs.getInt(1);

            pstmt2 = (PreparedStatement) connection.prepareStatement(sql2);

            Iterator it = cuisine.iterator();
            while (it.hasNext()) {
                //Cuisine cui = (Cuisine) it.next();
                pstmt2.setInt(1, orderNum);
                pstmt2.setString(2, it.next().toString());
                pstmt2.executeUpdate();
            }
            connection.commit();
            pstmt.close();
            pstmt2.close();
        } catch (SQLException e) {
            orderNum = 0;
            System.out.println("The information of order is wrong!");
            //e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                //设置事务提交方式为自动提交：
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderNum;
    }

    public int changeOrderStatus(int serialNum, String status) {
        connection = conMySQL.getCon();
        int i = 0;
        String sql = "update orders set Status=? where SerialNumber=?;";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, serialNum);
            i = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public String viewOrderStatus(int serialNum) {
        connection = conMySQL.getCon();
        String status = null;
        String sql = "select Status from orders where SerialNumber =" + serialNum + ";";
        PreparedStatement pstmt;
        Order order = new Order();
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                status = rs.getString(1);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public List viewCuisines(int serialNum){
        connection = conMySQL.getCon();
        String sql = "select Dishname from order_menu where SerialNumber =" + serialNum + ";";
        PreparedStatement pstmt;
        List list = new LinkedList();
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
