package com.igeek.daoimpl;

import com.igeek.dao.DuanziDao;
import com.igeek.pojo.Duanzi;
import com.igeek.utils.JDBCutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:黄广
 * @Description:段子接口的实现类
 * @Date:Created in 下午3:05 19-1-4
 * @Modified By:
 */
public class DuanziDaoImpl implements DuanziDao {
    @Override
    public void addAllDuanzi(ArrayList<Duanzi> listduanzi) {
        //获取数据库链接
        Connection conn = JDBCutils.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Duanzi duanzi;
        if (conn != null) {
            try {


                //创建处理器
                pstmt = conn.prepareStatement("insert into duanzi " +
                        "values(?,?,?,?,?,?)");
                for (int i = 0; i < listduanzi.size(); i++) {

                    duanzi = listduanzi.get(i);
                    duanzi.setDid(String.valueOf(i + 1));//给段子设置编号

                    //注入参数

                    //如果没有下边这个  会报null的异常 唉
                    if (duanzi.getDid() == null) {
                        duanzi.setDid("");
                    }
                    if (duanzi.getUid() == null) {
                        duanzi.setUid("");
                    }
                    if (duanzi.getTitle() == null) {
                        duanzi.setTitle("段子内容异常");
                    }
                    if (duanzi.getContent() == null) {
                        duanzi.setContent("段子内容异常");
                    }
                    if (duanzi.getGood() == null) {
                        duanzi.setGood(String.valueOf(0));
                    }
                    if (duanzi.getBad() == null) {
                        duanzi.setBad(String.valueOf(0));
                    }
                    pstmt.setInt(1, Integer.parseInt(duanzi.getDid()));
                    pstmt.setInt(2, Integer.parseInt(duanzi.getUid()));
                    pstmt.setString(3, duanzi.getTitle());
                    pstmt.setString(4, duanzi.getContent());
                    pstmt.setInt(5, Integer.parseInt(duanzi.getGood()));

                    pstmt.setInt(6, Integer.parseInt(duanzi.getBad()));

                    //执行新增
                    pstmt.executeUpdate();
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
    }

    @Override
    public ArrayList<Duanzi> findAllById(String id) {
        //获取数据库链接
        List<Duanzi> duanzis = new ArrayList<>();
        Connection conn = JDBCutils.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                //创建处理器
                pstmt = conn.prepareStatement("select * from duanzi where " +
                        "uid = ?");

                pstmt.setInt(1, Integer.parseInt(id));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Duanzi duanzi = new Duanzi();
                    duanzi.setDid(String.valueOf(rs.getInt(1)));
                    duanzi.setUid(String.valueOf(rs.getInt(2)));
                    duanzi.setTitle(rs.getString(3));
                    duanzi.setContent(rs.getString(4));
                    duanzi.setGood(String.valueOf(rs.getInt(5)));
                    duanzi.setBad(String.valueOf(rs.getInt(6)));
                    duanzis.add(duanzi);
                }
                return (ArrayList<Duanzi>) duanzis;
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
        return null;
    }
}
