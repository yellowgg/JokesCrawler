package com.igeek.view;

import com.igeek.pojo.Duanzi;
import com.igeek.service.DuanziService;
import com.igeek.service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 主界面窗体
 */
public class Main extends JFrame {

    UserService userSer = new UserService();
    DuanziService duanSer = new DuanziService();
    Map<String, String> map = new HashMap<>();
    String id; //用于记录下拉框选中的对应的键
    ArrayList<Duanzi> duanzis; // 用于记录被选中的用户的段子
    private JPanel contentPane;
    private JScrollPane pane;
    //private DefaultTableModel model = new DefaultTableModel(45,
    //        4) {
    //    /**
    //     * 重写后可以选中行但不可编辑
    //     */
    //    @Override
    //    public boolean isCellEditable(int row, int column) {
    //        return false;//父类中是true  所以能被编辑
    //    }
    //};
    String[] coluns = {"序号", "标题", "好评数", "差评数"};
    private DefaultTableModel model = new DefaultTableModel(coluns,
            125) {
        /**
         * 重写后可以选中行但不可编辑
         */
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;//父类中是true  所以能被编辑
        }
    };

    private JTable tb_duanzi = new JTable(model);


    /**
     * 在构造函数中布置好控件
     */
    public Main() {
        setTitle("段子");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 995, 668);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("用      户      名");
        label.setBounds(14, 13, 150, 18);
        contentPane.add(label);

        JComboBox cmb_duanzi = new JComboBox();
        cmb_duanzi.setBounds(14, 44, 242, 24);
        getUsersToMap();
        setMapToCmb(cmb_duanzi);
        cmb_duanzi.setEditable(false);//只能选择不能编辑
        contentPane.add(cmb_duanzi);
        cmb_duanzi.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //获取用户名对应的id
                //这种方式会获取两次值，打印两次
                //String value = (String) cmb_duanzi.getSelectedItem();
                //System.out.println(value);
                //这种方式只获取一次，打印一次
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String value = (String) cmb_duanzi.getSelectedItem();
                    //System.out.println(value);
                    id = (String) mapGetKey(map, value);

                    //然后获取这个用户id的段子list
                    if (duanSer.findAllById(id) != null) {
                        duanzis = duanSer.findAllById(id);
                    }

                    //然后将段子挂到table
                    loadModel(duanzis);
                }
            }
        });

        JTextArea txta_duanzi = new JTextArea();
        txta_duanzi.setBounds(493, 105, 484, 517);
        txta_duanzi.setEditable(false);//不可编辑
        txta_duanzi.setLineWrap(true);//自动换行
        txta_duanzi.setFont(new Font("宋体", Font.BOLD, 20));
        contentPane.add(txta_duanzi);

        /**
         * table表格
         */
        tb_duanzi.getTableHeader().setBackground(Color.PINK);
        tb_duanzi.setBounds(0, 0, 450, 510);
        tb_duanzi.getTableHeader().setPreferredSize(new Dimension(1,
                25));//修改列名高度
        tb_duanzi.addMouseListener(new MouseListener() {
            //只需重写点击方法即可
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    //1.获取选中的段子id
                    //1.1 获取选中的行
                    int count = tb_duanzi.getSelectedRow();

                    String did = tb_duanzi.getValueAt(count,
                            0).toString();

                    //然后找到这个id对应的内容 放到txtArea中
                    for (int i = 0; i < duanzis.size(); i++) {
                        Duanzi duanzi = duanzis.get(i);
                        if (did.equals(duanzi.getDid())) {
                            txta_duanzi.setText(duanzi.getContent());
                            return;
                        }
                    }
                } catch (Exception e1) {
                    //e1.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        //contentPane.add(tb_duanzi);


        pane = new JScrollPane(tb_duanzi,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.
                HORIZONTAL_SCROLLBAR_NEVER);
        pane.setBounds(14, 105, 465, 517);
        contentPane.add(pane);

        JLabel label_1 = new JLabel("段子列表");
        label_1.setBounds(14, 81, 72, 18);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("段子详情");
        label_2.setBounds(493, 81, 72, 18);
        contentPane.add(label_2);

        ////初始化表格的表头   雅虎！ 不用表格做表头了！！！！！！
        //model.setValueAt("序号", 0, 0);
        //model.setValueAt("标题", 0, 1);
        //model.setValueAt("好评数", 0, 2);
        //model.setValueAt("差评数", 0, 3);

        setResizable(false);//设置窗体不可变大小
        setLocationRelativeTo(null);//设置窗体居中
        setVisible(true);
    }


    /**
     * 将map的键挂到combox上
     *
     * @param cmb
     */
    private void setMapToCmb(JComboBox cmb) {
        Set set = map.keySet();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            String value = map.get(key);
            cmb.addItem(value);
        }
    }


    /**
     * 自定义方法，清除表格的内容，以防重复显示
     *
     * @param model
     */
    private void clearModel(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {//用原始表头之后，直接从0开始
            for (int j = 0; j < model.getColumnCount(); j++) {
                model.setValueAt("", i, j);
            }
        }
    }

    /**
     * 根据集合然后装载数据到model 这个集合中装的是某个用户的段子
     *
     * @param list
     */
    private void loadModel(ArrayList<Duanzi> list) {
        Duanzi duanzi;
        //先清空上一个用户的数据
        clearModel(model);
        for (int i = 0; i < list.size(); i++) { //可以从0开始装载 因为有了原始表头
            duanzi = (Duanzi) list.get(i);
            model.setValueAt(duanzi.getDid(), i, 0);
            model.setValueAt(duanzi.getTitle(), i, 1);
            model.setValueAt(duanzi.getGood(), i, 2);
            model.setValueAt(duanzi.getBad(), i, 3);
        }
    }

    /**
     * 自定义方法，获取段子用户列表放到map集合中
     *
     * @return
     */
    private void getUsersToMap() {
        userSer.findAll(map);
    }


    /**
     * java只有根据key获取value 所以需要自己写个根据value获取key的方法
     *
     * @param map
     * @param value
     * @return
     */
    private Object mapGetKey(Map map, Object value) {
        for (Object key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }
}
