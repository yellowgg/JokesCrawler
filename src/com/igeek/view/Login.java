package com.igeek.view;

import com.igeek.service.LoginService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录窗体
 */
public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField txt_name;
    private JTextField txt_pass;


    /**
     * 在构造函数中布置好控件
     */
    public Login() {
        setTitle("登录窗体");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 404, 496);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("用户：");
        label.setBounds(76, 160, 72, 18);
        contentPane.add(label);

        JLabel label_1 = new JLabel("密码：");
        label_1.setBounds(76, 230, 72, 18);
        contentPane.add(label_1);

        txt_name = new JTextField();
        txt_name.setBounds(137, 157, 144, 24);
        contentPane.add(txt_name);
        txt_name.setColumns(10);

        txt_pass = new JTextField();
        txt_pass.setBounds(137, 227, 144, 24);
        contentPane.add(txt_pass);
        txt_pass.setColumns(10);

        JButton bt_login = new JButton("登录");
        bt_login.setBounds(47, 316, 113, 27);
        bt_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取控件的值
                String name = txt_name.getText();
                String pass = txt_pass.getText();

                //判断是否正确
                LoginService service = new LoginService();
                com.igeek.pojo.Login login = service.findUserByName(name);
                if (login != null) {
                    //判断密码
                    if (login.getPassword().equals(pass)) {
                        //打开主菜单并隐藏当前窗口
                        new Main();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "密码错误，请重试！");
                    }
                } else {
                    JOptionPane.showMessageDialog(contentPane, "用户名不存在，请重试！");
                }
            }
        });
        contentPane.add(bt_login);

        JButton bt_exit = new JButton("退出");
        bt_exit.setBounds(214, 316, 113, 27);
        bt_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        contentPane.add(bt_exit);

        JLabel label_2 = new JLabel("爬虫小系统");
        label_2.setFont(new Font("宋体", Font.BOLD, 23));
        label_2.setBounds(137, 42, 144, 85);
        contentPane.add(label_2);

        setResizable(false);//设置窗体不可变大小
        setLocationRelativeTo(null);//设置窗体居中
        setVisible(true);
    }
}
