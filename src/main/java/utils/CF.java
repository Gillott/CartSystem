package utils;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class CF {
    static Properties pro = new Properties();

    static {
        try {
            InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
            pro.load(input);
            Class.forName(pro.getProperty("m.driver"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 获取连接
    public static Connection getConn() throws Exception {
        return DriverManager.getConnection(
                pro.getProperty("m.url"),
                pro.getProperty("m.username"),
                pro.getProperty("m.password")
        );
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getConn());
    }
}
