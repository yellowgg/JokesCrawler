package com.igeek.pojo;

/**
 * @Author:黄广
 * @Description:用于登录系统的pojo类
 * @Date:Created in 下午3:01 19-1-4
 * @Modified By:
 */
public class Login {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
