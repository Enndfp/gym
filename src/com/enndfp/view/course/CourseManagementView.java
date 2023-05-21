package com.enndfp.view.course;

import com.enndfp.pojo.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.Vector;

/**
 * @author Enndfp
 * @date 2023/3/12
 */
public class CourseManagementView extends JPanel {
    //滚动面板
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private JTextField queryField = new JTextField();
    private JButton deleteButton = new JButton("删除课程");
    private JButton selectButton = new JButton("报名信息");
    private JButton addButton = new JButton("添加课程");
    private JButton queryButton = new JButton("查询");

    public CourseManagementView() {
        setSize(840, 400);
        setLayout(null);//绝对布局

        scrollPane.setBounds(11, 120, 840, 300);
        scrollPane.getViewport().add(table);//把表格加入到滚动面板中
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 调整单元格滚动速度
        scrollPane.getVerticalScrollBar().setBlockIncrement(64); // 调整页面滚动速度

        queryField.setBounds(275, 50, 120, 30);
        queryField.setText("Search");
        queryField.setForeground(Color.lightGray);
        queryField.setFont(new Font("黑体", Font.BOLD, 15));
        queryField.setFocusTraversalKeysEnabled(false);
        queryField.setMargin(new Insets(0, 10, 0, 0));
        add(queryField);

        queryButton.setBounds(480, 50, 80, 30);
        queryButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(queryButton);

        selectButton.setBounds(170, 470, 120, 30);
        selectButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(selectButton);

        deleteButton.setBounds(380, 470, 120, 30);
        deleteButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(deleteButton);

        addButton.setBounds(590, 470, 120, 30);
        addButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(addButton);

        updateContent();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();//得到用户选中了哪一行
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }
                int count = JOptionPane.showConfirmDialog(null, "确认删除此课程吗?", "删除界面", JOptionPane.YES_NO_OPTION);
                if (count == 0) {
                    //如果用户选中了就要删除对应数据
                    Connection connection = null;
                    ArrayList memberAccounts = new ArrayList();
                    //选中行的第一列  就是课程编号
                    String classId = (String) table.getValueAt(row, 0);
                    String classBegin = (String) table.getValueAt(row, 2);
                    String str = (String) table.getValueAt(row, 3);
                    Integer classTime = Integer.valueOf(str.substring(0, str.indexOf("分钟")));

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "SELECT * from class_order where class_id = ?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, classId);
                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            memberAccounts.add(rs.getString(6));
                        }
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "delete from class_table where class_id = ?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, classId);
                        int n = ps.executeUpdate();
                        if (n > 0) {//删除成功   数据库删除成功
                            //从界面中删除
                            ((DefaultTableModel) table.getModel()).removeRow(row);

                            JOptionPane.showMessageDialog(null, "删除成功");
                        }
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    //获取当前日期
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                    String nowTime = simpleDateFormat.format(date);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
                    LocalDateTime classBegin2 = LocalDateTime.parse(classBegin, formatter);
                    LocalDateTime nowTime2 = LocalDateTime.parse(nowTime, formatter);

                    // 使用compareTo()方法比较两个时间的大小
                    int result = classBegin2.compareTo(nowTime2);
                    try {
                        if (result > 0) {
                            //说明没有过开课时间，退还会员课时
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");

                            // 创建一个PreparedStatement对象
                            PreparedStatement ps = connection.prepareStatement("UPDATE member SET card_next_class=? WHERE member_account = ?");
                            // 遍历id集合
                            for (Object memberAccount : memberAccounts) {
                                // 查询id对应的记录
                                String sql = "SELECT * FROM member WHERE member_account = ?";
                                PreparedStatement ps2 = connection.prepareStatement(sql);
                                ps2.setString(1, String.valueOf(memberAccount));
                                ResultSet rs = ps2.executeQuery();


                                if (rs.next()) {
                                    String str2 = rs.getString(11);
                                    if (!str2.isEmpty()) {
                                        int cardNextClass = Integer.parseInt(str2);
                                        // 进行修改
                                        cardNextClass += classTime;
                                        String cardNextClass2 = String.valueOf(cardNextClass);

                                        // 将修改后的数据存入PreparedStatement对象中
                                        ps.setString(1, cardNextClass2);
                                        ps.setString(2, String.valueOf(memberAccount));
                                        // 执行更新操作
                                        ps.executeUpdate();
                                    }
                                }
                                ps2.close();
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        queryField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                queryField.setText("");
                queryField.setFont(new Font("黑体", Font.BOLD, 15));
                queryField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (queryField.getText().isEmpty()) {
                    queryField.setText("Search");
                    queryField.setForeground(Color.lightGray);
                }
                queryField.setMargin(new Insets(0, 10, 0, 0));
            }
        });
        queryField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryField.requestFocusInWindow();
            }
        });
        //回车查询
        queryField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {//点击的是回车按钮
                    queryButton.doClick();//点击一次查询按钮
                    queryButton.requestFocus();
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCourseView((DefaultTableModel) table.getModel());
            }
        });
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String queryInfo = queryField.getText();
                Vector<String> thVector = new Vector<>();//表头集合
                thVector.add("编号");
                thVector.add("名称");
                thVector.add("时间");
                thVector.add("时长");
                thVector.add("教练");
                Vector<Vector<String>> dataVector = new Vector<>();

                Connection connection = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                    String sql;
                    PreparedStatement ps;
                    if (queryInfo.isEmpty() || queryInfo.equals("Search")) {
                        // 查询全部信息
                        sql = "SELECT * from class_table";
                        ps = connection.prepareStatement(sql);
                    } else {
                        // 按条件查询
                        sql = "SELECT * from class_table where  class_id like ? or class_name like ? " +
                                "or class_begin like ? or class_time like ? or coach like ? ";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, "%" + queryInfo + "%");
                        ps.setString(2, "%" + queryInfo + "%");
                        ps.setString(3, "%" + queryInfo + "%");
                        ps.setString(4, "%" + queryInfo + "%");
                        ps.setString(5, "%" + queryInfo + "%");
                    }
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Vector<String> vector = new Vector<>();
                        vector.add(rs.getString(1));
                        vector.add(rs.getString(2));
                        vector.add(rs.getString(3));
                        vector.add(rs.getString(4));
                        vector.add(rs.getString(5));
                        dataVector.add(vector);
                    }
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                //构建表模型
                DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
                table.setModel(defaultTableModel);//告知表格  要显示的数据

                //表格的数据居中
                DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
                cellRenderer.setHorizontalAlignment(JLabel.CENTER);
                //给表格指定渲染器
                table.setDefaultRenderer(Object.class, cellRenderer);
                table.getTableHeader().setReorderingAllowed(false);
                table.getTableHeader().setResizingAllowed(false);
                table.setDefaultEditor(Object.class, readOnlyEditor); // 设置只读的单元格编辑器
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 设置选择模式为只能选择整行
                table.setRowHeight(30);
                // 设置表格字体样式
                Font font = new Font("黑体", Font.BOLD, 18);
                table.setFont(font);
                table.getTableHeader().setFont(font);
                TableColumnModel columnModel = table.getColumnModel();
                TableColumn firstColumn = columnModel.getColumn(0);
                TableColumn thirdColumn = columnModel.getColumn(2);
                firstColumn.setPreferredWidth(30);
                thirdColumn.setPreferredWidth(150);

                queryField.setText("Search");
                queryField.setForeground(Color.lightGray);
                queryField.setMargin(new Insets(0, 10, 0, 0));
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course course = new Course();
                int row = table.getSelectedRow();//得到用户选中了哪一行
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }

                Connection connection = null;
                Vector<Vector<String>> dataVector = new Vector<>();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                    String sql = "SELECT * from class_order where class_id = ?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(table.getValueAt(row, 0)));
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "暂无报名信息!");
                        return;
                    } else {
                        String classId = (String) table.getValueAt(row, 0);
                        String className = (String) table.getValueAt(row, 1);
                        String classBegin = (String) table.getValueAt(row, 2);
                        String classTime = (String) table.getValueAt(row, 3);
                        String classCoach = (String) table.getValueAt(row, 4);

                        course.setClassId(Integer.valueOf(classId));
                        course.setClassName(className);
                        course.setClassBegin(classBegin);
                        course.setClassTime(classTime);
                        course.setClassCoach(classCoach);
                        do {
                            Vector<String> vector = new Vector<>();
                            vector.add(rs.getString(6));
                            vector.add(rs.getString(5));
                            dataVector.add(vector);
                        } while (rs.next());
                    }

                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                new SelectMemberOrderView(course, dataVector);
            }
        });

        add(scrollPane);
        setVisible(true);
    }

    public void updateContent() {
        Vector<String> thVector = new Vector<>();//表头集合
        thVector.add("编号");
        thVector.add("名称");
        thVector.add("时间");
        thVector.add("时长");
        thVector.add("教练");

        Vector<Vector<String>> dataVector = new Vector<>();

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
            String sql = "SELECT * from class_table";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                dataVector.add(vector);
            }
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        //构建表模型
        DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
        table.setModel(defaultTableModel);//告知表格  要显示的数据

        //表格的数据居中
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        //给表格指定渲染器
        table.setDefaultRenderer(Object.class, cellRenderer);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setDefaultEditor(Object.class, readOnlyEditor); // 设置只读的单元格编辑器
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 设置选择模式为只能选择整行
        table.setRowHeight(30);
        // 设置表格字体样式
        Font font = new Font("黑体", Font.BOLD, 18);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn firstColumn = columnModel.getColumn(0);
        TableColumn thirdColumn = columnModel.getColumn(2);
        firstColumn.setPreferredWidth(30);
        thirdColumn.setPreferredWidth(150);

    }

    // 创建一个只读的单元格编辑器
    DefaultCellEditor readOnlyEditor = new DefaultCellEditor(new JTextField()) {
        @Override
        public boolean isCellEditable(EventObject event) {
            return false; // 禁止编辑单元格
        }
    };
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageIcon("D:\\图片\\gym.jpg").getImage(); // 背景图片路径
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
