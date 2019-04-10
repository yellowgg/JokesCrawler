package com.igeek.pojo;

/**
 * @Author:黄广
 * @Description:段子类的pojo类
 * @Date:Created in 下午7:42 19-1-3
 * @Modified By:
 */
public class Duanzi {
    private String did;
    private String uid;
    private String title;
    private String content;
    private String good;
    private String bad;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    @Override
    public String toString() {
        return "Duanzi{" +
                "did='" + did + '\'' +
                ", uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", good='" + good + '\'' +
                ", bad='" + bad + '\'' +
                '}';
    }
}

