package com.igeek.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;


/**
 * @Author:黄广
 * @Description:连接数据库的工具类，使用c3p0连接池
 * @Date:Created in 下午7:53 19-1-3
 * @Modified By:
 */
public class JDBCutils {
    private static Connection conn;
    private static ComboPooledDataSource ds = new ComboPooledDataSource();

    public static Connection getConnection() {
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeAll(Connection conn, Statement st, ResultSet rs) throws Exception {
        if (conn != null) {
            conn.close();
        }
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
}
