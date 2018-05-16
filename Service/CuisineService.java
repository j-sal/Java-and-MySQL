package Service;

import Dao.ConMySQL;
import Models.Cuisine;
import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CuisineService {

    private Connection connection = null;
    private ConMySQL conMySQL = new ConMySQL();

    public List viewCuisine(){
        connection = conMySQL.getCon();
        String sql = "select * from cuisine;";

        PreparedStatement pstmt;
        List list = new LinkedList();
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                Cuisine cuisine = new Cuisine();
                cuisine.setDishName(rs.getString(1));
                cuisine.setPrice(rs.getInt(2));
                cuisine.setIntroduction(rs.getString(3));
                cuisine.setEvaluation(rs.getString(4));
                list.add(cuisine);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addCuisine(String name, int price, String introduction, String evaluation){
        connection = conMySQL.getCon();
        int i = 0;
        String sql = "insert into cuisine (Dishname, Price, Introduction, Evaluation) values(?,?,?,?);";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setString(3, introduction);
            pstmt.setString(4, evaluation);
            i = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int deleteCuisine(String dishname){
        connection = conMySQL.getCon();
        int i = 0;
        String sql = "delete from cuisine where Dishname='" + dishname + "';";
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

}
