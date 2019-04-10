package com.igeek.dao;

import com.igeek.pojo.Login;

/**
 * @Author:黄广
 * @Description:有关登录操作的接口类
 * @Date:Created in 下午3:03 19-1-4
 * @Modified By:
 */
public interface LoginDao {

    /**
     * 根据用户名验证用户是否存在
     *
     * @param name
     * @return
     */
    Login findUserByName(String name);
}
