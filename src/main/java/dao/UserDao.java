package dao;

import utils.CF;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    // 注册
    public static int reg(String username, String password) {
        String sql = "INSERT INTO users VALUES(NULL, ?, ?)";
        try (Connection conn = CF.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // 登陆
    public static int login(String username, String password) {
        String sql = "SELECT * FROM users WHERE name=?";
        try (Connection conn = CF.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);

            ResultSet rs  = ps.executeQuery();
            // .next() 判断结果集是否有数据
            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    return 1;
                }else return -1;
            }
            return ps.executeQuery().next()?1:0;
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库连接失败！");
            return 0;
        }
    }
    // 获取 id
    public static int getId(String username) {
        String sql = "SELECT * FROM users WHERE name=?";
        try (Connection conn = CF.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);

            ResultSet rs  = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }else {
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
