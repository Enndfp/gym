package com.enndfp.view.course;

import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Vector;

/**
 * 选课界面
 *
 * @author Enndfp
 */
public class ChooseCourseView extends JPanel {
    // 滚动面板
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private JButton signUpButton = new JButton("报名");

    public ChooseCourseView(String account) {
        setSize(840, 400);
        setLayout(null); // 绝对布局

        scrollPane.setBounds(11, 120, 840, 300);
        scrollPane.getViewport().add(table); // 把表格加入到滚动面板中
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 调整单元格滚动速度
        scrollPane.getVerticalScrollBar().setBlockIncrement(64); // 调整页面滚动速度

        signUpButton.setBounds(375, 470, 120, 30);
        signUpButton.setFont(new Font("黑体", Font.BOLD, 20));
        add(signUpButton);

        updateContent();

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 得到用户选中了哪一行
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选课");
                    return;
                }
                int count = JOptionPane.showConfirmDialog(null, "确认选择该课程吗?", "选课界面", JOptionPane.YES_NO_OPTION);
                if (count == 0) {
                    // 选中行的第一列  就是课程编号
                    String classId = (String) table.getValueAt(row, 0);
                    String className = (String) table.getValueAt(row, 1);
                    String classBegin = (String) table.getValueAt(row, 2);
                    String coach = (String) table.getValueAt(row, 4);
                    String str = (String) table.getValueAt(row, 3);
                    Integer classTime = Integer.valueOf(str.substring(0, str.indexOf("分钟")));
                    Integer remainTime = null;
                    String memberName = null;

                    Connection connection = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "SELECT member_name,card_next_class from member where member_account = ?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, account);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            memberName = rs.getString(1);
                            remainTime = rs.getInt(2);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JDBCUtil.getClose(connection, ps, rs);
                    }
                    if (classTime > remainTime) {
                        // 说明会员课时不够买课
                        JOptionPane.showMessageDialog(null, "对不起,您的课时不足!");
                        return;
                    }
                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "SELECT * from class_order where class_id = ? and member_account=?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, classId);
                        ps.setString(2, account);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            // 会员已报过该课程
                            JOptionPane.showMessageDialog(null, "您已报过该课程，请重新选课!");
                            return;
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JDBCUtil.getClose(connection, ps, rs);
                    }
                    try {
                        connection = JDBCUtil.getConnection();
                        PreparedStatement ps2 = null;
                        String sql = "insert into class_order(class_id,class_name,coach,member_name,member_account,class_begin) " +
                                "values (?,?,?,?,?,?)";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, classId);
                        ps.setString(2, className);
                        ps.setString(3, coach);
                        ps.setString(4, memberName);
                        ps.setString(5, account);
                        ps.setString(6, classBegin);
                        int n = ps.executeUpdate();
                        if (n == 1) {
                            // 更新会员课时
                            String sql2 = "update member set card_next_class = ? where member_account=?";
                            ps2 = connection.prepareStatement(sql2);
                            ps2.setString(1, String.valueOf(remainTime - classTime));
                            ps2.setString(2, account);
                            int n2 = ps2.executeUpdate();
                            if (n2 == 1) {
                                JOptionPane.showMessageDialog(null, "报名成功");
                            } else {
                                JOptionPane.showMessageDialog(null, "报名失败");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "报名失败");
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

    public void updateContent() {
        Vector<String> thVector = new Vector<>(); // 表头集合
        thVector.add("编号");
        thVector.add("名称");
        thVector.add("时间");
        thVector.add("时长");
        thVector.add("教练");

        Vector<Vector<String>> dataVector = new Vector<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * from class_table";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                dataVector.add(vector);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            JDBCUtil.getClose(connection, ps, rs);
        }

        // 构建表模型
        DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
        table.setModel(defaultTableModel);//告知表格  要显示的数据

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
