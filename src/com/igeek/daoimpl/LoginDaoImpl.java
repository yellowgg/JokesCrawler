package com.igeek.daoimpl;

import com.igeek.dao.LoginDao;
import com.igeek.pojo.Login;
import com.igeek.utils.JDBCutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author:黄广
 * @Description:登录接口的实现类
 * @Date:Created in 下午3:05 19-1-4
 * @Modified By:
 */
public class LoginDaoImpl implements LoginDao {

    /**
     * 根据用户名验证用户是否存在
     *
     * @param name
     * @return
     */
    @Override
    public Login findUserByName(String name) {
        Login login = null;
        //获取数据库链接
        Connection conn = JDBCutils.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                //创建处理器
                pstmt = conn.prepareStatement("select * from login where " +
                        "name=?");
                //注入参数
                pstmt.setString(1, name);
                //执行查询
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    login = new Login();
                    login.setName(rs.getString(1));
                    login.setPassword(rs.getString(2));
                }
            } catch (Exception e) {
            } finally {
                try {
                    JDBCutils.closeAll(conn, pstmt, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return login;
    }
}
