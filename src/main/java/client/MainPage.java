package client;

import dao.UserDao;

import javax.swing.*;
import java.awt.*;


public class MainPage {
    JFrame frame;
    JPanel top, center, bottom;
    JLabel lab1, lab2;
    JTextField username, password;
    JButton login, register;

    public static int id;

    public MainPage() {
        frame = new JFrame("购物车系统登陆页面");

        top = new JPanel();
        lab1 = new JLabel("用户名");
        username = new JTextField(11);
        addAll(top, lab1, username);

        center = new JPanel();
        lab2 = new JLabel("密码");
        password = new JTextField(12);
        addAll(center, lab2, password);


        bottom = new JPanel();
        login = new JButton("登陆");
        login.addActionListener(ac-> {
            String name = username.getText();
            String pwd = password.getText();
            if ("".equals(name)){
                JOptionPane.showMessageDialog(frame, "用户名不能为空！");
                return;
            }
            if ("".equals(pwd)){
                JOptionPane.showMessageDialog(frame, "密码不能为空！");
                return;
            }
            UserDao dao = new UserDao();
            int i = UserDao.login(name, pwd);
            if (i == 0){
                JOptionPane.showMessageDialog(frame, "用户不存在！");
                System.out.println(id);
            }else if (i == 1){
                JOptionPane.showMessageDialog(frame, "登陆成功！");
                id = UserDao.getId(name);
                // 隐藏登陆界面，显示物品界面
                frame.setVisible(false);
                new client.GoodsPage();
            }else{
                JOptionPane.showMessageDialog(frame, "密码错误!");
            }
        });
        register = new JButton("注册");
        register.addActionListener(ac->{
            String name = username.getText();
            String pwd = password.getText();
            if ("".equals(name)){
                JOptionPane.showMessageDialog(frame, "用户名不能为空！");
                return;
            }
            if ("".equals(pwd)){
                JOptionPane.showMessageDialog(frame, "密码不能为空！");
                return;
            }
            UserDao dao = new UserDao();
            int i = UserDao.reg(name, pwd);
            if (i == 1) {
                JOptionPane.showMessageDialog(frame, "注册成功！");
            }else {
                JOptionPane.showMessageDialog(frame, "注册失败！");
            }
        });
        addAll(bottom, login, register);

        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(330, 150);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        System.out.println("欢迎使用购物车系统！");
        new MainPage();
    }

    public static int getId() {
        return id;
    }
    public static void addAll(Container ctn, Component ...cs) {
        for (Component c: cs){
            ctn.add(c);
        }
    }
}
