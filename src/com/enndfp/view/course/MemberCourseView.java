package com.enndfp.view.course;

import com.enndfp.pojo.Course;
import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.EventObject;
import java.util.Vector;

/**
 * @author Enndfp
 * @date 2023/3/19
 */
public class MemberCourseView extends JPanel {
    // 滚动面板
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private JButton deleteButton = new JButton("退课");

    public MemberCourseView(String account) {
        setSize(840, 400);
        setLayout(null); // 绝对布局

        scrollPane.setBounds(11, 120, 840, 300);
        scrollPane.getViewport().add(table); // 把表格加入到滚动面板中
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 调整单元格滚动速度
        scrollPane.getVerticalScrollBar().setBlockIncrement(64); // 调整页面滚动速度

        deleteButton.setBounds(375, 470, 120, 30);
        deleteButton.setFont(new Font("黑体", Font.BOLD, 20));
        add(deleteButton);

        selectCourse(account);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 得到用户选中了哪一行
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }

                int count = JOptionPane.showConfirmDialog(null, "确认退选该课程吗?", "退课界面", JOptionPane.YES_NO_OPTION);
                if (count == 0) {
                    // 选中行的第一列  就是课程编号
                    String classId = (String) table.getValueAt(row, 0);
                    String classBegin = (String) table.getValueAt(row, 2);
                    String str = null;

                    Connection connection = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "SELECT class_time from class_table where class_id = ?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, classId);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            str = rs.getString(1);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JDBCUtil.getClose(connection, ps, rs);
                    }

                    Integer classTime = Integer.valueOf(str.substring(0, str.indexOf("分钟")));
                    // 获取当前日期
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
                            // 说明没有过开课时间，退还会员课时
                            connection = JDBCUtil.getConnection();

                            // 创建一个PreparedStatement对象
                            ps = connection.prepareStatement("UPDATE member SET card_next_class=? WHERE member_account = ?");

                            String sql = "SELECT * FROM member WHERE member_account = ?";
                            PreparedStatement ps2 = connection.prepareStatement(sql);
                            ps2.setString(1, String.valueOf(account));
                            rs = ps2.executeQuery();

                            if (rs.next()) {
                                String str2 = rs.getString(11);
                                if (!str2.isEmpty()) {
                                    int cardNextClass = Integer.parseInt(str2);
                                    // 进行修改
                                    cardNextClass += classTime;
                                    String cardNextClass2 = String.valueOf(cardNextClass);

                                    // 将修改后的数据存入PreparedStatement对象中
                                    ps.setString(1, cardNextClass2);
                                    ps.setString(2, account);
                                    // 执行更新操作
                                    ps.executeUpdate();
                                }
                            }
                            ps2.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        JDBCUtil.getClose(connection, ps, rs);
                    }

                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "delete from class_order where class_id = ? and member_account = ?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, classId);
                        ps.setString(2, account);
                        int n = ps.executeUpdate();
                        // 删除成功
                        if (n > 0) {
                            // 从界面中删除
                            ((DefaultTableModel) table.getModel()).removeRow(row);

                            JOptionPane.showMessageDialog(null, "退课成功");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JDBCUtil.getClose(connection, ps, rs);
                    }
                }
            }
        });

        add(scrollPane);
        setVisible(true);
    }

    public void selectCourse(String account) {
        Vector<String> thVector = new Vector<>(); // 表头集合
        thVector.add("编号");
        thVector.add("名称");
        thVector.add("时间");
        thVector.add("教练");

        Vector<Vector<String>> dataVector = new Vector<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * from class_order where member_account=? order by class_id";
            ps = connection.prepareStatement(sql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(7));
                vector.add(rs.getString(4));
                dataVector.add(vector);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            JDBCUtil.getClose(connection, ps, rs);
        }

        // 构建表模型
        DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
        table.setModel(defaultTableModel); // 告知表格  要显示的数据

        // 表格的数据居中
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        // 给表格指定渲染器
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
        Image image = new ImageIcon("src/com/enndfp/image/member.jpg").getImage(); // 背景图片路径
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
