package com.enndfp.view.member;

import com.enndfp.pojo.Member;
import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Vector;

/**
 * 会员管理界面
 *
 * @author Enndfp
 */
public class MemberManagementView extends JPanel {
    // 滚动面板，根据内容大小自动判断是否显示滚动条
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private JTextField queryField = new JTextField();
    private JButton addButton = new JButton("添加");
    private JButton deleteButton = new JButton("删除");
    private JButton updateButton = new JButton("编辑");
    private JButton queryButton = new JButton("查询");

    public MemberManagementView() {
        setLayout(null); // 绝对布局

        scrollPane.setBounds(11, 120, 840, 300);
        scrollPane.getViewport().add(table); // 把表格加入到滚动面板中
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 调整单元格滚动速度
        scrollPane.getVerticalScrollBar().setBlockIncrement(64); // 调整页面滚动速度

        queryField.setBounds(275, 50, 120, 30);
        queryField.setText("Search");
        queryField.setForeground(Color.lightGray);
        queryField.setFont(new Font("黑体", Font.BOLD, 15));
        queryField.setMargin(new Insets(0, 10, 0, 0));
        add(queryField);

        queryButton.setBounds(480, 50, 80, 30);
        queryButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(queryButton);

        updateButton.setBounds(190, 470, 80, 30);
        updateButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(updateButton);

        deleteButton.setBounds(390, 470, 80, 30);
        deleteButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(deleteButton);

        addButton.setBounds(580, 470, 80, 30);
        addButton.setFont(new Font("黑体", Font.BOLD, 18));
        add(addButton);

        updateContent();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMemberView((DefaultTableModel) table.getModel());
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // 得到用户选中了哪一行
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }
                int count = JOptionPane.showConfirmDialog(null, "确认删除此位会员吗?", "删除界面", JOptionPane.YES_NO_OPTION);
                // 如果用户选中了就要删除对应数据
                if (count == 0) {
                    // 选中行的第一列  就是员工编号
                    String memberAccount = (String) table.getValueAt(row, 0);

                    Connection connection = null;
                    String sql = "delete from member where member_account=?";
                    PreparedStatement ps = null;

                    try {
                        connection = JDBCUtil.getConnection();
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, memberAccount);

                        int n = ps.executeUpdate();
                        // 删除成功
                        if (n > 0) {
                            // 从界面中删除
                            ((DefaultTableModel) table.getModel()).removeRow(row);

                            JOptionPane.showMessageDialog(null, "删除成功");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        JDBCUtil.getClose(connection, ps, null);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // 得到用户选中了哪一行
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }
                Member member = new Member();
                String memberAccount = (String) table.getValueAt(row, 0);

                String memberName = (String) table.getValueAt(row, 1);
                String memberGender = (String) table.getValueAt(row, 2);
                String memberAge = (String) table.getValueAt(row, 3);
                String memberCourse = (String) table.getValueAt(row, 4);
                String memberCourse2 = (String) table.getValueAt(row, 5);
                String memberPhone = (String) table.getValueAt(row, 6);
                String memberCardTime = (String) table.getValueAt(row, 7);

                member.setMemberAccount(Integer.valueOf(memberAccount));
                member.setMemberName(memberName);
                member.setMemberGender(memberGender);
                member.setMemberAge(Integer.valueOf(memberAge));
                member.setCardClass(Integer.valueOf(memberCourse));
                member.setCardNextClass(Integer.valueOf(memberCourse2));
                member.setMemberPhone(memberPhone);
                member.setCardTime(memberCardTime);

                new UpdateMemberView(member, (DefaultTableModel) table.getModel());
            }
        });

        // 搜索框失焦和获得焦点的行为
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
        // 进入页面搜索框失去焦点
        queryField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryField.requestFocusInWindow();
            }
        });
        // 回车查询
        queryField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {//点击的是回车按钮
                    queryButton.doClick();//点击一次查询按钮
                }
            }
        });

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String queryInfo = queryField.getText();
                Vector<String> thVector = new Vector<>();//表头集合
                thVector.add("会员卡号");
                thVector.add("姓名");
                thVector.add("性别");
                thVector.add("年龄");
                thVector.add("购买课时");
                thVector.add("剩余课时");
                thVector.add("联系方式");
                thVector.add("办卡时间");
                Vector<Vector<String>> dataVector = new Vector<>();

                Connection connection = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String sql;
                try {
                    connection = JDBCUtil.getConnection();

                    if (queryInfo.isEmpty() || queryInfo.equals("Search")) {
                        // 查询全部信息
                        sql = "SELECT * from member";
                        ps = connection.prepareStatement(sql);
                    } else {
                        sql = "SELECT * from member where  member_account like ? or member_name like ? " +
                                "or member_gender like ? or member_age like ?  or member_phone like ? or card_class like ? " +
                                "or card_next_class like ? or card_time like ?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, "%" + queryInfo + "%");
                        ps.setString(2, "%" + queryInfo + "%");
                        ps.setString(3, "%" + queryInfo + "%");
                        ps.setString(4, "%" + queryInfo + "%");
                        ps.setString(5, "%" + queryInfo + "%");
                        ps.setString(6, "%" + queryInfo + "%");
                        ps.setString(7, "%" + queryInfo + "%");
                        ps.setString(8, "%" + queryInfo + "%");
                    }

                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Vector<String> vector = new Vector<>();
                        vector.add(rs.getString(1));
                        vector.add(rs.getString(3));
                        vector.add(rs.getString(4));
                        vector.add(rs.getString(5));
                        vector.add(rs.getString(10));
                        vector.add(rs.getString(11));
                        vector.add(rs.getString(8));
                        vector.add(rs.getString(9));
                        dataVector.add(vector);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    JDBCUtil.getClose(connection, ps, rs);
                }

                // 构建表模型
                DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
                table.setModel(defaultTableModel);// 告知表格  要显示的数据

                // 表格的数据居中
                DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
                cellRenderer.setHorizontalAlignment(JLabel.CENTER);
                // 给表格指定渲染器
                table.setDefaultRenderer(Object.class, cellRenderer);
                table.getTableHeader().setReorderingAllowed(false); // 禁止通过拖动表头来重新排序表格列
                table.getTableHeader().setResizingAllowed(false); // 禁止调整表格列的宽度
                table.setDefaultEditor(Object.class, readOnlyEditor); // 设置只读的单元格编辑器
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 设置选择模式为只能选择整行
                table.setRowHeight(30);
                // 设置表格字体样式
                Font font = new Font("黑体", Font.BOLD, 18);
                table.setFont(font);
                table.getTableHeader().setFont(font);
                TableColumnModel columnModel = table.getColumnModel();
                TableColumn firstColumn = columnModel.getColumn(0);
                TableColumn seventhColumn = columnModel.getColumn(6);
                TableColumn eighthColumn = columnModel.getColumn(7);
                firstColumn.setPreferredWidth(100);
                seventhColumn.setPreferredWidth(100);
                eighthColumn.setPreferredWidth(100);

                queryField.setText("Search");
                queryField.setForeground(Color.lightGray);
                queryField.setMargin(new Insets(0, 10, 0, 0));
            }
        });

        add(scrollPane);
        setVisible(true);
    }

    public void updateContent() {
        Vector<String> thVector = new Vector<>(); // 表头集合
        thVector.add("会员卡号");
        thVector.add("姓名");
        thVector.add("性别");
        thVector.add("年龄");
        thVector.add("购买课时");
        thVector.add("剩余课时");
        thVector.add("联系方式");
        thVector.add("办卡时间");

        Vector<Vector<String>> dataVector = new Vector<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * from member";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                vector.add(rs.getString(10));
                vector.add(rs.getString(11));
                vector.add(rs.getString(8));
                vector.add(rs.getString(9));
                dataVector.add(vector);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        table.getTableHeader().setReorderingAllowed(false); // 禁止通过拖动表头来重新排序表格列
        table.getTableHeader().setResizingAllowed(false); // 禁止调整表格列的宽度
        table.setDefaultEditor(Object.class, readOnlyEditor); // 设置只读的单元格编辑器
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 设置选择模式为只能选择整行
        table.setRowHeight(30);
        // 设置表格字体样式
        Font font = new Font("黑体", Font.BOLD, 18);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn firstColumn = columnModel.getColumn(0);
        TableColumn seventhColumn = columnModel.getColumn(6);
        TableColumn eighthColumn = columnModel.getColumn(7);
        firstColumn.setPreferredWidth(100);
        seventhColumn.setPreferredWidth(100);
        eighthColumn.setPreferredWidth(100);
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
        Image image = new ImageIcon("src/com/enndfp/image/main.png").getImage(); // 背景图片路径
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}