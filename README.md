# JokesCrawler
爬段子数据加载到Swing窗体  
  
项目简要说明：  
1.由于不能上传空文件夹，所以缺少一个，应该再创建一个htmlsource文件夹跟src同级

2.先运行 com.igeek.service包下的Creeper类抓取网页数据之后，然后会自动将过滤好的数据存进数据库  

3.然后再运行com.igeek.view包下的RunApplication类启动 Swing窗体程序，有个登录窗体，随便加的  

4.所以数据库有三张表，一个是存储段子的duanzi表，一个是存储发段子的用户的user表，一个是用来应付登录的login表

写这个程序碰到了很多关于Swing的问题，因为本身用Swing写窗体程序就不多，所以可以记录一下下  
  
  -------------
  
### 程序需要改进的敌方
* 关于实体类的字段类型，本程序用的都是String类型，所以遇到数据库中有别的类型的时候，不管是任何操作都得转相关的类型，
所以建议是类型尽量跟数据库设计一样
* 本程序是单独运行爬虫程序爬取数据之后然后初始化放到数据库的，应该直接在Main窗体中加个按钮连接起来，这样就可以运行一次就足够了  

### 关于控件的有关用法
1.表格
   * 表格只有放进JScrollPane才会有原始的表头列名，不然只能用model的第一行做表头了  
   * JTable和DefaultTableModel和JScrollPane之间的关系为  
   `new model -> new table(model) -> new JScrollPane(table)`
   * DefaultTableModel有个构造方法的参数是  
   `public DefaultTableModel(Object[] columnNames, int rowCount)`  
   所以它可以传进一个列名数组和表格的行数构建一个table  
   * 如果使用DefaultTableModel的话，每次使用之前记得先清除一下数据，不然会残留上一次加载的数据，可以写一个方法  
   ```
   private void clearModel(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {//用原始表头之后，直接从0开始
            for (int j = 0; j < model.getColumnCount(); j++) {
                model.setValueAt("", i, j);
            }
        }
    }
```  
   * 如果想设置表格不可编辑，可以在new DefaultTableModel对象的时候， 重写一下方法，代码如下  
   ```  
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
```  
  
  
2.下拉框  
   * 由于我前提是不重写下拉框，所以有很多东西都很不友好，想要达到想要的效果就只能想别的方法了  
   * 本程序的需求是，将用户名加载到下拉框的选项中，然后获取选中的用户名的值去加载数据，所以需要用到map来使用键值对  
   * 常见的设置  
   `cmb_duanzi.setEditable(false);//只能选择不能编辑`  
   * 将map的键加载到combox下  
   ```  
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
```  
   * 下拉框选择事件的监听器为addItemListener 值得注意的是，如果直接用getSelectedItem（）方法获取选中值的话，会获取两次，所以需要改动一下  
   ```  
   //这种方式只获取一次
   if (e.getStateChange() == ItemEvent.SELECTED) {
                    String value = (String) cmb_duanzi.getSelectedItem();  
    }  
```  
  
  
3.Map  
   * java只有根据key获取value 所以需要自己写个根据value获取key的方法  
   ```  
   private Object mapGetKey(Map map, Object value) {
        for (Object key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }
```  
   
