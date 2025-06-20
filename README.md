---
title: 简易购物车系统
date: 2025-06-19 23:06:52
tags:
---
## 项目简介
本项目使用 Java 与 mysql 数据库建立一个简易购物车系统，模拟用户网购时使用购物车的操作，支持用户把特定商品加入购物车，并挑选购物车中的商品进行结算。
## 项目采用技术
- 开发语言：Java 17
- IDE: IntelliJ IDEA
- UI 框架：Java Swing
- 数据库：mysql
- 辅助工具：maven（打包）
## 功能需求分析
#### 1. **核心功能**
- **商品浏览**
  - 展示商品列表（编号、名称、品牌、价格、数量）
  - 支持商品分类/搜索（支持模糊查询寻找商品）
- **购物车管理**
  - 添加商品（输入商品信息：名称、品牌、价格、数量）
  - 删除商品（指定商品移除）
  - 支付商品（支付零钱并移除所支付的商品）
#### 2. **关键交互流程**
```mermaid
  A[用户界面] --> B[浏览商品]
  B --> C{选择操作}
  C -->|添加| D[加入购物车]
  C -->|查看| E[查看购物车]
  D --> F[显示购物车内容]
  E --> G{管理操作}
  G -->|删除| H[移除商品]
  H -->|结算| I[支付费用]
```
## 项目亮点
使用 JDBC 将 java 与 mysql 数据库结合起来，用户、商品、购物车信息均储存在 mysql 数据库中，这些信息通过 java 的 java swing 展现出来，用户在 UI 界面进行的操作会及时反馈到数据库中，使其成为一个动态的数据管理系统
## 项目结构
```mermaid
./
+-- lib/
│   +-- mysql-connector-j-8.0.33.jar
+-- out/
+-- pom.xml
+-- src/
│   +-- main/
│     +-- java/
│     │   +-- bean
│     │   │   +-- CartGood.java
│     │   │   +-- Good.java
│     │   +-- client
│     │   │   +-- CartPage.java
│     │   │   +-- GoodsPage.java
│     │   │   +-- MainPage.java
│     │   +-- dao
│     │   │   +-- CartDao.java
│     │   │   +-- UserDao.java
│     │   +-- dialog
│     │   │   +-- PayDialog.java
│     │   +-- model
│     │   │   +-- MyTableModel.java
│     │   +-- utils
│     │       +-- CF.java
│     +-- resources/
│         +-- META-INF
│         │   +-- MANIFEST.MF
│         +-- db.properties
+-- target/
```
## 相关包介绍
| 包名 | 功能 |
| :-: | :- | 
| bean | 存放 JavaBean 类即可重用类 Good、 CartGood|
| client | 与用户进行交互的页面，如 MainPage、GoodsPage |
| dao | 进行数据库相关操作，分为 UserDao 和 CartDao|
| dialog   | 存放模态框组件    |
| model   | 重构表格模型    |
|  utils  | 工具包    |
## 主要功能代码示例
1.系统登陆页面
```java
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
```
2.注册或登录数据库用户
```java
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
```
3.商品浏览页面
```java
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
```
4.加入购物车操作
```java
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
```
5.支付并把商品从购物车中移除
```java
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
```
## 运行效果展示
1.登陆页面
![1](https://ze-2gij738154dcf45f-1338084978.tcloudbaseapp.com/img/1.png)
2.商品浏览页面
![2](https://ze-2gij738154dcf45f-1338084978.tcloudbaseapp.com/img/2.png)
3.操作页面
![3](https://ze-2gij738154dcf45f-1338084978.tcloudbaseapp.com/img/3.png)
4.购物车页面
![4](https://ze-2gij738154dcf45f-1338084978.tcloudbaseapp.com/img/4.png)
5.支付成功页面
![5](https://ze-2gij738154dcf45f-1338084978.tcloudbaseapp.com/img/5.png)
