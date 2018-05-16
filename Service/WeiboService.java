package Service;

import Dao.ConMySQL;
import Models.Weibo;
import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class WeiboService {
	
	private Connection connection = null;
    private ConMySQL conMySQL = new ConMySQL();

    @SuppressWarnings("rawtypes") 
	public List viewSinaUsers(){
        connection = ConMySQL.getCon();
        String sql = "select count(*) from userprofile where web like '%sina%';";

        PreparedStatement pstmt;
        List list = new LinkedList();
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
            	Weibo weibo = new Weibo();
            	weibo.setSinaUsers(rs.getInt(1));
                list.add(weibo);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @SuppressWarnings("rawtypes") 
	public List viewNonSina(){
        connection = ConMySQL.getCon();
        String sql = "select count(*) from userprofile where web not like '%sina%';";

        PreparedStatement pstmt;
        List list = new LinkedList();
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
            	Weibo weibo = new Weibo();
            	weibo.setNonSina(rs.getInt(1));
                list.add(weibo);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @SuppressWarnings("rawtypes") 
	public List viewTotal(){
        connection = ConMySQL.getCon();
        String sql = "select count(*) from userprofile;";

        PreparedStatement pstmt;
        List list = new LinkedList();
        try {
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while(rs.next()) {
            	Weibo weibo = new Weibo();
            	weibo.setTotal(rs.getInt(1));
                list.add(weibo);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
