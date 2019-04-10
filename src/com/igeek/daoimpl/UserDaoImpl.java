package com.igeek.daoimpl;

import com.igeek.dao.UserDao;
import com.igeek.pojo.User;
import com.igeek.utils.JDBCutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:黄广
 * @Description:用户接口的实现类
 * @Date:Created in 下午3:05 19-1-4
 * @Modified By:
 */
public class UserDaoImpl implements UserDao {
    @Override
    public boolean findUserById(User user) {
        //获取数据库链接
        Connection conn = JDBCutils.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                //创建处理器
                pstmt = conn.prepareStatement("select * from user where " +
                        "uid=?");
                //注入参数
                pstmt.setInt(1, Integer.parseInt(user.getUid()));
                //执行查询
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    //如果查到了，代表存在
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    JDBCutils.closeAll(conn, pstmt, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;//如果出意外，返回true以防插入
    }

    @Override
    public void addUser(User user) {
        //获取数据库链接
        Connection conn = JDBCutils.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                //创建处理器
                pstmt = conn.prepareStatement("insert into user values(?,?)");
                //注入参数
                pstmt.setInt(1, Integer.parseInt(user.getUid()));
                pstmt.setString(2, user.getUsername());
                //执行新增
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    JDBCutils.closeAll(conn, pstmt, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<User> findAll() {
        //获取数据库链接
        List<User> users = new ArrayList<>();
        Connection conn = JDBCutils.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                //创建处理器
                pstmt = conn.prepareStatement("select * from user ");

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setUid(rs.getString(1));
                    user.setUsername(rs.getString(2));
                    users.add(user);
                }
                return users;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    JDBCutils.closeAll(conn, pstmt, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }
}
