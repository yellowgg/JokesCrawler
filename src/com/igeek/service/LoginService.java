package com.igeek.service;

import com.igeek.daoimpl.LoginDaoImpl;
import com.igeek.pojo.Login;

/**
 * @Author:黄广
 * @Description:登录的业务
 * @Date:Created in 下午3:07 19-1-4
 * @Modified By:
 */
public class LoginService {

    /**
     * 根据用户名查找用户是否存在
     *
     * @param name
     * @return
     */
    public Login findUserByName(String name) {
        LoginDaoImpl dao = new LoginDaoImpl();
        return dao.findUserByName(name);
    }
}
