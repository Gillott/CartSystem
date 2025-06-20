package client;

import bean.CartGood;
import dao.CartDao;
import dialog.PayDialog;
import model.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CartPage {
    JFrame frame;
    JPanel top, center, bottom;
    JButton del, pay;
    JTable table;
    JScrollPane scrollpane;
    JTextField good_name;

    static int id = client.MainPage.getId();

    // 表格模版
    static MyTableModel model;
    // 表格数据
    static List<Object[]> data = new ArrayList<>();

    public CartPage() {
        // 定义字体
        Font font = new Font("Serif", Font.BOLD, 20);

        frame = new JFrame("购物车");
        top = new JPanel(new BorderLayout());
        del = new JButton("移除");
        del.addActionListener(ac -> {
            // 获取想要删除的商品
            // 获取当前选中的行的下标
            int i = table.getSelectedRow();
            if (i == -1){
                JOptionPane.showMessageDialog(frame, "请先选择要移除的商品！");
                return;
            }
            String good = table.getValueAt(i, 1).toString();
            CartDao dao = new CartDao();
            int j = dao.delGood(good);
            if (j > 0) {
                // 回显操作
                query();
                JOptionPane.showMessageDialog(frame, "移除成功！");
            }else {
                JOptionPane.showMessageDialog(frame, "移除失败！");
            }
        });
        good_name = new JTextField(10);
        top.add(del, BorderLayout.EAST);

        center = new JPanel(new BorderLayout());
        query();
        table = new JTable(model);
        // 隐藏第一列 c_id
        // table.getColumnModel().getColumn(0).setMinWidth(0);
        // table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.setFont(font);
        table.setSize(600, 400);
        // 设置表格行高
        table.setRowHeight(30);
        scrollpane = new JScrollPane();
        scrollpane.getViewport().add(table);
        center.add(scrollpane);

        bottom = new JPanel(new BorderLayout());
        pay = new JButton("支付");
        pay.addActionListener(ac->{
            // 获取想要删除的商品
            // 获取当前选中的行的下标
            int i = table.getSelectedRow();
            if (i == -1){
                JOptionPane.showMessageDialog(frame, "请先选择要支付的商品！");
                return;
            }
            String good = table.getValueAt(i, 1).toString();
            CartDao dao = new CartDao();
            int j = dao.delGood(good);
            if (j > 0) {
                // 回显操作
                query();
                new PayDialog(frame).setVisible(true);
            }else {
                JOptionPane.showMessageDialog(frame, "支付失败！");
            }

        });
        bottom.add(pay, BorderLayout.EAST);

        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new CartPage();
    }

    public void query() {
        // 查询事件处理
        String[] headers = {"序号","商品", "品牌", "价格", "数量"};
        // 获取条件 物品名称
        String name = good_name.getText();
        List<CartGood> list = new CartDao().getCGoods(name);

        Object[][] datas = new Object[list.size()][headers.length];
        // 遍历过滤后的集合 封装每一行数据
        int i = 0;
        for (CartGood cartGood: list) {
            datas[i][0] = i + 1;
            datas[i][1] = cartGood.getGood();
            datas[i][2] = cartGood.getBrand();
            datas[i][3] = cartGood.getPrice();
            datas[i][4] = cartGood.getNum();
            i++;
        }
        if (model == null) {
            model = new MyTableModel(headers, datas);
        }
        // 重新渲染表格
        model.updateDataAndRefresh(datas);
    }

    public static void addAll(Container ctn, Component ...cs) {
        for (Component c: cs){
            ctn.add(c);
        }
    }
}
