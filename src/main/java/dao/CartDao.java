package dao;

import bean.CartGood;
import bean.Good;
import client.MainPage;
import utils.CF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartDao {
    static int id = client.MainPage.getId();
    static String c_id;

    // 添加商品
    public int addGood(bean.Good good) {
        String sql = "INSERT INTO carts VALUES(?,?,?,1)";
        String cid = UUID.randomUUID().toString().replaceAll("-", "");
        try(Connection conn = CF.getConn();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cid);
            ps.setInt(2, id);
            ps.setInt(3, good.getGid());
            return ps.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Good> getGoods(String name) {
        String sql = "SELECT * FROM goods WHERE good LIKE ?";
        try(Connection conn = CF.getConn();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            List<Good> goods = new ArrayList<>();
            while(rs.next()){
                Good good = new Good(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4)
                );
                goods.add(good);
            }
            return goods;
        }catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<CartGood> getCGoods(String name) {
        String sql = "SELECT c_id,good,brand,price,num FROM goods,carts WHERE goods.g_id=carts.g_id AND id=?";
        try(Connection conn = CF.getConn();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<CartGood> goods = new ArrayList<>();
            while(rs.next()){
                CartGood cartGood = new CartGood(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5)
                );
                goods.add(cartGood);
            }
            return goods;
        }catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int delGood(String good) {
        String sql = "DELETE FROM carts WHERE c_id=?";
        try(Connection conn = CF.getConn();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, getC_id(getG_id(good)));
            return ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 获取 g_id
    public static int getG_id(String good) {
        String sql = "SELECT * FROM goods WHERE good=?";
        try (Connection conn = CF.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, good);

            ResultSet rs  = ps.executeQuery();
            if (rs.next()) {
                int g_id = rs.getInt("g_id");
                return g_id;
            }else {
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // 获取 c_id
    public String getC_id(int g_id) {
        // 获取 c_id
        String sql = "SELECT * FROM carts WHERE id=? AND g_id=?";
        try (Connection conn = CF.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.setInt(2, g_id);

            ResultSet rs  = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("c_id");
            }else {
                return "error";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
