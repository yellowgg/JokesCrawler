package com.igeek.service;

import com.igeek.daoimpl.UserDaoImpl;
import com.igeek.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:黄广
 * @Description:用户的业务
 * @Date:Created in 下午3:07 19-1-4
 * @Modified By:
 */
public class UserService {

    /**
     * 将一个User添加到数据库,在那之前先确认是否已存在
     *
     * @param user
     */
    public void addUser(User user) {
        UserDaoImpl dao = new UserDaoImpl();
        //先查一下所添加的用户是否已存在
        if (dao.findUserById(user)) {
            return;
        } else {//否则就添加
            dao.addUser(user);
        }
    }

    /**
     * 获取User列表
     *
     * @return
     */
    public void findAll(Map<String, String> map) {
        UserDaoImpl dao = new UserDaoImpl();
        List<User> users = new ArrayList<>();
        if (dao.findAll() != null) {
            users = dao.findAll();
        }
        for (int i = 0; i < users.size(); i++) {
            map.put(users.get(i).getUid(), users.get(i).getUsername());
        }

    }


}
