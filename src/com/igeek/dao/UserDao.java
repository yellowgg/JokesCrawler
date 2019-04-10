package com.igeek.dao;

import com.igeek.pojo.User;

import java.util.List;

/**
 * @Author:黄广
 * @Description:有关用户操作的接口类
 * @Date:Created in 下午2:42 19-1-4
 * @Modified By:
 */
public interface UserDao {


    /**
     * 查看是否存在有这个id的用户
     *
     * @param user
     * @return
     */
    boolean findUserById(User user);

    /**
     * 添加用户到数据库
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> findAll();
}
