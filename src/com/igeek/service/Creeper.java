package com.igeek.service;

import com.igeek.pojo.Duanzi;
import com.igeek.pojo.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:黄广
 * @Description:爬虫类
 * @Date:Created in 下午8:08 19-1-3
 * @Modified By:
 */
public class Creeper {

    private static int numPage = 50; //从第二页开始爬 2-50 网站上不止50页

    public static void main(String[] args) {
        //爬取源代码
        crawlHTMLSource();
        //初始化数据到数据库
        initDataBase();
        System.out.println("初始化完成");
    }


    /**
     * 抓取源码放到txt中
     */
    private static void crawlHTMLSource() {
        try {
            //挖段子网的文字区是http://www.waduanzi.com/joke/page/2 后面的数字是页码 其它都相同
            URL url = null;
            HttpURLConnection urlConnection = null;
            InputStream is = null;
            BufferedReader reader = null;
            BufferedWriter writer = null;

            String urlString = null;
            String txtString = null;
            for (int i = 2; i <= numPage; i++) {
                urlString = "http://www.waduanzi.com/joke/page/" + i + "/";
                txtString = "htmlsource/" + i + ".txt";
                url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(txtString)));

                int line;
                while ((line = reader.read()) != -1) {
                    writer.write(line);
                }
            }

            //关闭资源
            writer.close();
            reader.close();
            urlConnection.disconnect();

            //结果
            System.out.println("爬取完成，请查看文件！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 爬取用户信息,返回一个有重复的用户集合
     */
    private static ArrayList<User> parseUserData() {
        ArrayList<User> list = new ArrayList<>();
        // 创建缓冲字符串用于拼接读取到的所有数据
        StringBuilder builder = new StringBuilder();
        String txtStr = null;
        BufferedReader reader = null;
        try {

            for (int i = 2; i <= numPage; i++) {
                txtStr = "htmlsource/" + i + ".txt";
                // 加载读取存储在本地的爬取数据
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(txtStr)));
                String line = null;
                // 读取数据 ，并将读取到的每一行数据写入至StringBuilder
                while ((line = reader.readLine()) != null) {
                    // 拼接字符
                    builder.append(line + "\n");
                }
            }
            // 关闭流
            reader.close();

            // 将StringBuilder转换成String
            String source = builder.toString();

            // 爬取每一个用户,包括用户名和编号
            /**
             * src="http://img01.waduanzi.com/ava
             * tars/2013/06/18/original_20130618123402_51bfe33a8c3f7.j
             * peg!savatar" alt="中高艺" />        	<a target="_blank"
             * href="http://www.waduanzi.com/u/65">中高艺</a>    	</div>
             */
            Pattern pattern = Pattern
                    .compile("src=\"http://img01.waduanzi.com/avatars/2013/06/18(.*?)</a>");
            Matcher matcher = pattern.matcher(source);
            // 反复查找满足正则规则的数据
            while (matcher.find()) {
                //创建一个用户对象
                User user = new User();
                //爬取对应的数据 获取的是上面那一段html代码
                String userInfo = matcher.group();

                //找到用户编号 为65
                Pattern pid = Pattern.compile("href=\"http://www.waduanzi" +
                        ".com/u/(.*?)\">");
                Matcher mid = pid.matcher(userInfo);
                if (mid.find()) {
                    String id = mid.group(1);
                    //写入id
                    user.setUid(id);
                }

                //找到用户名称 为中高艺
                Pattern pname = Pattern.compile("alt=\"(.*?)\" />");
                Matcher mname = pname.matcher(userInfo);
                if (mname.find()) {
                    String name = mname.group(1);
                    //写入名字
                    user.setUsername(name);
                }
                // 将每个用户写入集合
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 爬取段子信息
     */
    private static ArrayList<Duanzi> parseDuanziData() {
        ArrayList<Duanzi> list = new ArrayList<>();
        // 创建缓冲字符串用于拼接读取到的所有数据
        StringBuilder builder = new StringBuilder();
        String txtStr = null;
        BufferedReader reader = null;
        try {

            for (int i = 2; i <= numPage; i++) {
                txtStr = "htmlsource/" + i + ".txt";
                // 加载读取存储在本地的爬取数据
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(txtStr)));
                String line = null;
                // 读取数据 ，并将读取到的每一行数据写入至StringBuilder
                while ((line = reader.readLine()) != null) {
                    // 拼接字符
                    builder.append(line + "\n");
                }
            }
            // 关闭流
            reader.close();

            // 将StringBuilder转换成String
            String source = builder.toString();

            // 爬取每一个段子,包括作者，标题，内容，好评数，差评数
            Pattern pattern = Pattern.compile("<a target=\"_blank\" href=\"http:" +
                    "//www.waduanzi.com/u([\\s\\S]*?)分享");
            Matcher matcher = pattern.matcher(source);
            // 反复查找满足正则规则的数据
            while (matcher.find()) {
                //创建一个段子对象
                Duanzi duanzi = new Duanzi();
                //爬取整个段子的信息
                String duanziInfo = matcher.group();

                //如果有“继续阅读全文” 就不要这个段子
                Pattern p = Pattern.compile("继续阅读全文");
                Matcher m = p.matcher(duanziInfo);
                if (m.find()) {
                    continue;
                }


                //找到作者id 为67
                Pattern pid = Pattern.compile("href=\"http://www.waduanzi" +
                        ".com/u/(.*?)\">");
                Matcher mid = pid.matcher(duanziInfo);
                if (mid.find()) {
                    String id = mid.group(1);
                    //写入id
                    duanzi.setUid(id);
                }

                //找到标题 为内心一片荒芜
                Pattern ptitle = Pattern.compile("<h2 class=\"item-title\"><a " +
                        "class=\"cd-title-link\" target=\"_blank\" title=\"([\\s\\S]*?)\"");
                Matcher mtitle = ptitle.matcher(duanziInfo);
                if (mtitle.find()) {
                    String title = mtitle.group(1);
                    //写入标题
                    duanzi.setTitle(title);
                }

                //找到内容 为内容
                Pattern ptxt = Pattern.compile(" <div class=\"item-content\">" +
                        "([\\s\\S]*?)</div>");
                Matcher mtxt = ptxt.matcher(duanziInfo);
                if (mtxt.find()) {
                    String txt = mtxt.group(1);
                    //写入内容
                    duanzi.setContent(txt);
                }

                //找到好评数  按照这个其实匹配出了好评数和差评数 但差评数带有负号
                Pattern pgood = Pattern.compile("data-url=\"http://www." +
                        "waduanzi.com/post/score\">(.*?)</a></li>");
                Matcher mgood = pgood.matcher(duanziInfo);
                if (mgood.find()) {
                    String good = mgood.group(1);
                    //写入好评数
                    duanzi.setGood(good);
                }

                //找到差评数
                Pattern pbad = Pattern.compile("data-url=\"http://www.waduan" +
                        "zi.com/post/score\">-(.*?)</a>");
                Matcher mbad = pbad.matcher(duanziInfo);
                if (mbad.find()) {
                    String bad = mbad.group(1);
                    //写入差评数
                    duanzi.setBad(bad);
                }

                // 将每个合适的段子写入集合
                list.add(duanzi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    /**
     * 初始化数据存入数据库
     */
    private static void initDataBase() {
        UserService userSer = new UserService();
        DuanziService duanziSer = new DuanziService();
        ////存储用户 需要去重
        ArrayList<User> list = parseUserData();
        for (User user : list) {
            userSer.addUser(user);
        }

        //存储段子 不用去重
        ArrayList<Duanzi> listduanzi = parseDuanziData();
        duanziSer.addAllDuanzi(listduanzi);

    }
}
