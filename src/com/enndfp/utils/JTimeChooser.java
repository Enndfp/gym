package com.enndfp.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * 时间选择器
 *
 * @author Enndfp
 */
public class JTimeChooser extends JDialog implements ActionListener {
    private static final long serialVersionUID = -3758522951261503946L;

    // 默认宽度与高度
    private static final int DEFAULT_WIDTH = 405;
    private static final int DEFAULT_HEIGHT = 387;
    // 默认显示的年份为100年,即当年的前后50年
    private int showYears = 100;
    // 确认按钮
    private JButton confirm = null;
    // 上一个月,下一个月按钮
    private JButton lastMonth = null;
    private JButton nextMonth = null;
    // 临时按钮
    private JButton button = null;
    // 选择年与月的下拉框
    private JComboBox comboBox1 = null;
    private JComboBox comboBox2 = null;
    // 日历对象
    private Calendar calendar = null;
    // 年与月的选择集合对象
    private String[] years = null;
    private String[] months = null;
    // 当前年,月,日
    private int year1, month1, day1;
    // 时分秒
    private int hour, minute, second;
    private JPanel panel = null;
    private String tits[] = {"日", "一", "二", "三", "四", "五", "六"};
    // 时,分,秒下拉列表框
    private JComboBox comboBox3 = null;
    private JComboBox comboBox4 = null;
    private JComboBox comboBox5 = null;
    // 下拉框显示的行数
    private int rowlens = 5;
    private String title = "选择时间";
    private Point location = null;
    // 标识变量
    private boolean flag;

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent 父组件
     * @wbp.parser.constructor
     */
    public JTimeChooser(Dialog parent) {
        super(parent, true);
        this.setTitle(title);
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent 父组件
     * @param title  标题
     */
    public JTimeChooser(Dialog parent, String title) {
        super(parent, title, true);
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent   父组件
     * @param title    标题
     * @param location 组件显示的位置
     */
    public JTimeChooser(Dialog parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent    父组件
     * @param title     标题
     * @param location  组件显示的位置
     * @param showYears 显示的年数值,默认为显示100年,即前后50年<br>
     *                  比如 当前年份为2010年,参数showYears为30年,则界面显示的年份下拉框值从1995-2024<br>
     *                  注意: 若showYears值必须大于0,否则使用默认年数值
     */
    public JTimeChooser(Dialog parent, String title, Point location, int showYears) {
        super(parent, title, true);
        this.location = location;
        if (showYears > 0) {
            this.showYears = showYears;
        }
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent 父组件
     */
    public JTimeChooser(Frame parent) {
        super(parent, true);
        this.setTitle(title);
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent   父组件
     * @param location 界面显示的位置坐标
     */
    public JTimeChooser(Frame parent, Point location) {
        super(parent, true);
        this.setTitle(title);
        this.location = location;
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent   父组件
     * @param title    标题
     * @param location 界面显示的位置坐标
     */
    public JTimeChooser(Frame parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent    父组件
     * @param title     标题
     * @param location  界面显示的位置坐标
     * @param showYears 显示的年数值,默认为显示100年,即前后50年<br>
     *                  比如 当前年份为2010年,参数showYears为30年,则界面显示的年份下拉框值从1995-2024<br>
     *                  注意: 若showYears值必须大于0,否则使用默认年数值
     */
    public JTimeChooser(Frame parent, String title, Point location, int showYears) {
        super(parent, title, true);
        this.location = location;
        if (showYears > 0) {
            this.showYears = showYears;
        }
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent   父组件
     * @param location 界面显示的位置坐标
     */
    public JTimeChooser(Dialog parent, Point location) {
        super(parent, true);
        this.setTitle(title);
        this.location = location;
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showTimeDialog()方法获取选择的时间值
     *
     * @param parent 父组件
     * @param title  标题
     */
    public JTimeChooser(Frame parent, String title) {
        super(parent, title, true);
        // init
        this.initDatas();
        this.setResizable(false);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        this.calendar = Calendar.getInstance();
        this.year1 = this.calendar.get(Calendar.YEAR);
        this.month1 = this.calendar.get(Calendar.MONTH);
        this.day1 = this.calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = this.calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = this.calendar.get(Calendar.MINUTE);
        this.second = this.calendar.get(Calendar.SECOND);
        this.years = new String[showYears];
        this.months = new String[12];
        // init months
        for (int i = 0; i < this.months.length; i++) {
            this.months[i] = " " + formatDay(i + 1);
        }
        // init years
        int start = this.year1 - showYears / 2;
        for (int i = start; i < start + showYears; i++) {
            this.years[i - start] = String.valueOf(i);
        }
        // 设置时分秒为0
        this.calendar.set(Calendar.HOUR_OF_DAY, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        this.calendar.set(Calendar.SECOND, 0);

    }

    /**
     * 根据界面的长度与宽度确定界面的左上角坐标值
     *
     * @param width  长度
     * @param height 宽度
     * @return
     */
    private Dimension getStartDimension(int width, int height) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.width = dim.width / 2 - width / 2;
        dim.height = dim.height / 2 - height / 2;
        return dim;
    }

    /**
     * 在构造对象后调用此方法获取选择的时间值
     *
     * @return 选择时间对象Calendar
     */
    public Calendar showTimeDialog() {
        this.initCompents();
        return this.calendar;
    }

    /**
     * 初始化组件
     */
    private void initCompents() {
        getContentPane().setLayout(new BorderLayout());
        // 北面面板
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3.setBackground(Color.WHITE);
        showNorthPanel(panel3);
        getContentPane().add(panel3, BorderLayout.NORTH);
        // 中间面板
        getContentPane().add(printCalendar(), BorderLayout.CENTER);
        // 南边面板
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.showSouthPanel(panel2);
        getContentPane().add(panel2, BorderLayout.SOUTH);

        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // 设置显示的位置
        if (this.location == null) {
            Dimension dim = getStartDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            setLocation(dim.width, dim.height);
        } else {
            setLocation(this.location);
        }
        this.setVisible(true);
    }

    /**
     * 显示北面面板
     *
     * @param panel
     */
    private void showNorthPanel(JPanel panel) {
        this.lastMonth = new JButton("上一月");
        this.lastMonth.setToolTipText("上一月");
        this.lastMonth.addActionListener(this);
        panel.add(this.lastMonth);
        this.comboBox1 = new JComboBox(this.years);
        this.comboBox1.setSelectedItem(String.valueOf(year1));
        this.comboBox1.setToolTipText("年份");
        this.comboBox1.setMaximumRowCount(rowlens);
        this.comboBox1.setActionCommand("year");
        this.comboBox1.addActionListener(this);
        panel.add(this.comboBox1);
        this.comboBox2 = new JComboBox(this.months);
        this.comboBox2.setSelectedItem(" " + formatDay(month1 + 1));
        this.comboBox2.setToolTipText("月份");
        this.comboBox2.setMaximumRowCount(rowlens);
        this.comboBox2.addActionListener(this);
        this.comboBox2.setActionCommand("month");
        panel.add(this.comboBox2);
        this.nextMonth = new JButton("下一月");
        this.nextMonth.setToolTipText("下一月");
        this.nextMonth.addActionListener(this);
        panel.add(this.nextMonth);
    }

    /**
     * 显示南边面板信息
     *
     * @param panel
     */
    private void showSouthPanel(JPanel panel) {
        // 选择时间的界面
        JPanel panel_23 = new JPanel();
        panel_23.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel label21 = new JLabel("时间: ");
        label21.setForeground(Color.black);
        panel_23.add(label21);
        this.comboBox3 = new JComboBox(this.getHours());
        this.comboBox3.setMaximumRowCount(rowlens);
        this.comboBox3.setToolTipText("时");
        this.comboBox3.setActionCommand("hour");
        this.comboBox3.addActionListener(this);

        this.comboBox3.setSelectedItem(hour);

        panel_23.add(this.comboBox3);
        JLabel label22 = new JLabel("时 ");
        label22.setForeground(Color.black);
        panel_23.add(label22);
        this.comboBox4 = new JComboBox(this.getMins());
        this.comboBox4.setToolTipText("分");
        this.comboBox4.setMaximumRowCount(rowlens);
        this.comboBox4.setActionCommand("minute");
        this.comboBox4.addActionListener(this);

        this.comboBox4.setSelectedItem(minute);

        panel_23.add(this.comboBox4);
        JLabel label23 = new JLabel("分 ");
        label23.setForeground(Color.black);
        panel_23.add(label23);
        this.comboBox5 = new JComboBox(this.getMins());
        this.comboBox5.setToolTipText("秒");
        this.comboBox5.setActionCommand("second");
        this.comboBox5.addActionListener(this);
        this.comboBox5.setMaximumRowCount(rowlens);

        this.comboBox5.setSelectedItem(second);

        panel_23.add(this.comboBox5);
        JLabel label24 = new JLabel("秒");
        label24.setForeground(Color.black);
        panel_23.add(label24);
        panel.add(panel_23);
        this.confirm = new JButton("确定");
        this.confirm.setToolTipText("确定");
        this.confirm.addActionListener(this);
        panel.add(confirm);
    }

    /**
     * 获取小时数组
     *
     * @return
     */
    private Object[] getHours() {
        Object[] hs = new Object[24];
        for (int i = 0; i < hs.length; i++) {
            hs[i] = i;
        }
        return hs;
    }

    /**
     * 获取分钟或秒数数组
     *
     * @return
     */
    private Object[] getMins() {
        Object[] hs = new Object[60];
        for (int i = 0; i < hs.length; i++) {
            hs[i] = i;
        }
        return hs;
    }

    /**
     * 输出日期的面板
     *
     * @return
     */
    private JPanel printCalendar() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 7, 0, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        // 将日期设为当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 获取第一天是星期几
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        // 打印标头
        JButton b = null;
        for (int i = 0; i < tits.length; i++) {
            // b = new JButton("<html><b>" + tits[i] + "</b></html>");
            b = new JButton(tits[i]);
            b.setForeground(new Color(100, 0, 102));
            b.setEnabled(false);
            panel.add(b);
        }
        int count = 0;
        for (int i = Calendar.SUNDAY; i < weekDay; i++) {
            b = new JButton(" ");
            b.setEnabled(false);
            panel.add(b);
            count++;
        }
        int currday = 0;
        String dayStr = null;
        do {
            currday = calendar.get(Calendar.DAY_OF_MONTH);
            dayStr = formatDay(currday);
            // 日,月,年相等则显示
            if (currday == day1 && month1 == month2 && year1 == year2) {
                b = new JButton("[" + dayStr + "]");
                b.setForeground(Color.red);
            } else {
                b = new JButton(dayStr);
                b.setForeground(Color.black);
            }
            count++;
            b.setToolTipText(year2 + "/" + formatDay(month2 + 1) + "/" + dayStr);
            b.setBorder(BorderFactory.createEtchedBorder());
            b.addActionListener(this);
            panel.add(b);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            // 循环完成时月份实际上已经加1
        } while (calendar.get(Calendar.MONTH) == month2);
        // 减1,保持为当前月
        this.calendar.add(Calendar.MONTH, -1);
        if (!flag) {
            // 设置日期为当天
            this.calendar.set(Calendar.DAY_OF_MONTH, this.day1);
            flag = true;
        }
        for (int i = count; i < 42; i++) {
            b = new JButton(" ");
            b.setEnabled(false);
            panel.add(b);
        }
        return panel;
    }

    /**
     * 设置显示的数字,若小于10则在前面加0
     *
     * @param day
     * @return
     */
    private String formatDay(int day) {
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }

    /**
     * 事件处理
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("下一月".equals(command)) {
            // 1.月份加一
            this.calendar.add(Calendar.MONTH, 1);
            // 2.更新显示的年与月
            int year5 = calendar.get(Calendar.YEAR);
            // 判断是否超出显示的最大范围
            int maxYear = this.year1 + this.showYears / 2 - 1;
            if (year5 > maxYear) {
                this.calendar.add(Calendar.MONTH, -1);
                return;
            }
            int month5 = calendar.get(Calendar.MONTH) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(year5));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            // 3.更新界面
            this.updatePanel();
        } else if ("上一月".equals(command)) {
            // 1.月份减一
            this.calendar.add(Calendar.MONTH, -1);
            // 2.更新显示的年与月
            int year5 = calendar.get(Calendar.YEAR);
            // 判断是否超出显示的最大范围
            int minYear = this.year1 - this.showYears / 2;
            if (year5 < minYear) {
                this.calendar.add(Calendar.MONTH, 1);
                return;
            }
            int month5 = calendar.get(Calendar.MONTH) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(year5));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            // 3.更新界面
            this.updatePanel();
        } else if ("确定".equals(command)) {
            this.dispose();
        } else if (command.matches("^\\d+$")) {
            // 修改选择的日期的前景色
            JButton b = (JButton) e.getSource();
            if (this.button == null) {
                this.button = b;
            } else {
                this.button.setForeground(Color.black);
                this.button.setFont(b.getFont());
                this.button = b;
            }
            b.setForeground(new Color(0XFFD700));
            b.setFont(button.getFont().deriveFont(Font.BOLD));
            // 设置日期
            int day9 = Integer.parseInt(command);
            this.calendar.set(Calendar.DAY_OF_MONTH, day9);
        } else if (command.startsWith("[")) {
            // 修改选择的日期的前景色
            JButton b = (JButton) e.getSource();
            if (this.button == null) {
                this.button = b;
            } else {
                this.button.setForeground(Color.black);
                this.button.setFont(b.getFont());
                this.button = b;
            }
            b.setForeground(new Color(0XFFD700));
            b.setFont(button.getFont().deriveFont(Font.BOLD));
            // 处理为当前日期的情况
            this.calendar.set(Calendar.DAY_OF_MONTH, this.day1);
        } else if ("hour".equalsIgnoreCase(command)) {
            // 设置小时值
            int value = Integer.parseInt(this.comboBox3.getSelectedItem().toString().trim());
            this.calendar.set(Calendar.HOUR_OF_DAY, value);
        } else if ("minute".equalsIgnoreCase(command)) {
            // 设置分钟值
            int value = Integer.parseInt(this.comboBox4.getSelectedItem().toString().trim());
            this.calendar.set(Calendar.MINUTE, value);
        } else if ("second".equalsIgnoreCase(command)) {
            // 设置秒数值
            int value = Integer.parseInt(this.comboBox5.getSelectedItem().toString().trim());
            this.calendar.set(Calendar.SECOND, value);
        } else if ("year".equalsIgnoreCase(command)) {
            // 选择年事件
            int value = Integer.parseInt(this.comboBox1.getSelectedItem().toString().trim());
            this.calendar.set(Calendar.YEAR, value);
            this.updatePanel();
        } else if ("month".equalsIgnoreCase(command)) {
            // 选择月事件
            int value = Integer.parseInt(this.comboBox2.getSelectedItem().toString().trim());
            this.calendar.set(Calendar.MONTH, value - 1);
            this.updatePanel();
        }
    }

    /**
     * 更新界面
     */
    private void updatePanel() {
        this.remove(this.panel);
        getContentPane().add(this.printCalendar(), BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }

    /**
     * 获取显示的年数,默认为100年,即当前年份的前后50年
     *
     * @return
     */
    public int getShowYears() {
        return showYears;
    }

}
