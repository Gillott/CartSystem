package client;

import bean.Good;
import dao.CartDao;
import model.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsPage {
        JFrame frame;
        JPanel top, center, bottom, order;
        JTable table;
        JScrollPane scrollpane;
        JLabel lab1;
        JTextField good_name;
        JButton search, add_cart, view_cart;
        static Integer id = client.MainPage.getId();

    // 表格模版
    static MyTableModel model;
    // 表格数据
    static List<Object[]> data = new ArrayList<>();

    public GoodsPage() {
        // 定义字体
        Font font = new Font("Serif", Font.BOLD, 20);

        frame = new JFrame("商品浏览页面");

        top = new JPanel(new BorderLayout());
        lab1 = new JLabel("搜索商品");
        good_name = new JTextField(10);
        search = new JButton("搜索");
        search.addActionListener(ac->{
            query();
        });
        add_cart = new JButton("加入购物车");
        add_cart.addActionListener(ac->{
            // 获取想要添加的ID
            // 获取当前选中的行的下标
            int i = table.getSelectedRow();
            if (i == -1){
                JOptionPane.showMessageDialog(frame, "请先选择要添加的商品！");
                return;
            }
            int g_id = (Integer) table.getValueAt(i, 0);
            CartDao dao = new CartDao();
            Good good = new Good(
                    (Integer)table.getValueAt(i, 0),
                    table.getValueAt(i, 2).toString(),
                    table.getValueAt(i, 3).toString(),
                    0.00
            );
            int j = dao.addGood(good);
            if (j > 0) {
                // 回显操作
                query();
                JOptionPane.showMessageDialog(frame, "添加成功！");
            }else {
                JOptionPane.showMessageDialog(frame, "添加失败！");
            }
        });
        JPanel west = new JPanel();
        JPanel east = new JPanel();
        addAll(west, lab1, good_name, search);
        addAll(east, add_cart);
        top.add(west, BorderLayout.WEST);
        top.add(east, BorderLayout.EAST);

        center = new JPanel(new BorderLayout());
        query();
        table = new JTable(model);

        // 隐藏第一列 ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.setFont(font);
        table.setSize(600, 400);
        // 设置表格行高
        table.setRowHeight(30);
        scrollpane = new JScrollPane();
        scrollpane.getViewport().add(table);
        center.add(scrollpane);


        bottom = new JPanel(new BorderLayout());
        view_cart = new JButton("查看购物车");
        view_cart.addActionListener(ac->{
            // frame.setVisible(false);
            new client.CartPage();
        });
        bottom.add(view_cart, BorderLayout.EAST);

        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        System.out.println(id);
    }

    public static void main(String[] args) {
        new GoodsPage();
    }

    public void query() {
        // 查询事件处理
        String[] headers = {"ID","编号", "商品", "品牌", "价格"};
        // 获取条件 物品名称
        String name = good_name.getText();
        List<Good> list = new CartDao().getGoods(name);

        Object[][] datas = new Object[list.size()][headers.length];
        // 遍历过滤后的集合 封装每一行数据
        int i = 0;
        for (Good good: list) {
            datas[i][0] = good.getGid();
            datas[i][1] = i + 1;
            datas[i][2] = good.getGood();
            datas[i][3] = good.getBrand();
            datas[i][4] = good.getPrice();
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
