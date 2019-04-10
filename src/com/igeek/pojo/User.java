package com.igeek.pojo;

/**
 * @Author:黄广
 * @Description:用户表的pojo类
 * @Date:Created in 下午7:41 19-1-3
 * @Modified By:
 */
public class User {
    private String uid;
    private String username;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
