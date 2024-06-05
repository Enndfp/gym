package com.enndfp.view.employee;

import com.enndfp.pojo.Employee;
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
 * 员工管理界面
 *
 * @author Enndfp
 */
public class EmployeeManagementView extends JPanel {
    // 滚动面板
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private JTextField queryField = new JTextField();
    private JButton addButton = new JButton("添加");
    private JButton deleteButton = new JButton("解雇");
    private JButton updateButton = new JButton("编辑");
    private JButton queryButton = new JButton("查询");

    public EmployeeManagementView() {
        setLayout(null); // 绝对布局

        scrollPane.setBounds(11, 120, 840, 300);
        scrollPane.getViewport().add(table); // 把表格加入到滚动面板中
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
                new AddEmployeeView((DefaultTableModel) table.getModel());
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
                int count = JOptionPane.showConfirmDialog(null, "确认解雇此位员工吗?", "解雇界面", JOptionPane.YES_NO_OPTION);
                // 如果用户选中了就要删除对应数据
                if (count == 0) {
                    Connection connection = null;
                    PreparedStatement ps = null;
                    try {
                        // 选中行的第一列  就是员工编号
                        String employeeAccount = (String) table.getValueAt(row, 0);
                        connection = JDBCUtil.getConnection();
                        String sql = "delete from employee where employee_account=?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, employeeAccount);
                        int n = ps.executeUpdate();
                        // 删除成功
                        if (n > 0) {
                            // 从界面中删除
                            ((DefaultTableModel) table.getModel()).removeRow(row);

                            JOptionPane.showMessageDialog(null, "解雇成功");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JDBCUtil.getClose(connection, ps, null);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = new Employee();
                int row = table.getSelectedRow(); // 得到用户选中了哪一行
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }
                String employeeAccount = (String) table.getValueAt(row, 0);
                String employeeName = (String) table.getValueAt(row, 1);
                String employeeGender = (String) table.getValueAt(row, 2);
                String employeeAge = (String) table.getValueAt(row, 3);
                String entryTime = (String) table.getValueAt(row, 4);
                String staff = (String) table.getValueAt(row, 5);
                String employeeMessage = (String) table.getValueAt(row, 6);

                employee.setEmployeeAccount(Integer.valueOf(employeeAccount));
                employee.setEmployeeName(employeeName);
                employee.setEmployeeGender(employeeGender);
                employee.setEmployeeAge(Integer.valueOf(employeeAge));
                employee.setEntryTime(entryTime);
                employee.setStaff(staff);
                employee.setEmployeeMessage(employeeMessage);

                new UpdateEmployeeView(employee, (DefaultTableModel) table.getModel());
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
                if (e.getKeyCode() == 10) { // 点击的是回车按钮
                    queryButton.doClick(); // 点击一次查询按钮
                }
            }
        });

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String queryInfo = queryField.getText();
                Vector<String> thVector = new Vector<>(); // 表头集合
                thVector.add("工号");
                thVector.add("姓名");
                thVector.add("性别");
                thVector.add("年龄");
                thVector.add("入职时间");
                thVector.add("职务");
                thVector.add("备注信息");
                Vector<Vector<String>> dataVector = new Vector<>();

                Connection connection = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    connection = JDBCUtil.getConnection();
                    String sql;

                    if (queryInfo.isEmpty() || queryInfo.equals("Search")) {
                        // 查询全部信息
                        sql = "SELECT * from employee";
                        ps = connection.prepareStatement(sql);
                    } else {
                        // 按条件查询
                        sql = "SELECT * from employee where  employee_account like ? or employee_name like ? " +
                                "or employee_gender like ? or employee_age like ? or entry_time like ? or staff like ? or employee_message like ? ";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, "%" + queryInfo + "%");
                        ps.setString(2, "%" + queryInfo + "%");
                        ps.setString(3, "%" + queryInfo + "%");
                        ps.setString(4, "%" + queryInfo + "%");
                        ps.setString(5, "%" + queryInfo + "%");
                        ps.setString(6, "%" + queryInfo + "%");
                        ps.setString(7, "%" + queryInfo + "%");
                    }
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Vector<String> vector = new Vector<>();
                        vector.add(rs.getString(1));
                        vector.add(rs.getString(2));
                        vector.add(rs.getString(3));
                        vector.add(rs.getString(4));
                        vector.add(rs.getString(5));
                        vector.add(rs.getString(6));
                        vector.add(rs.getString(7));
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
                TableColumn thirdColumn = columnModel.getColumn(2);
                TableColumn forthColumn = columnModel.getColumn(3);
                TableColumn fifthColumn = columnModel.getColumn(4);
                thirdColumn.setPreferredWidth(30);
                forthColumn.setPreferredWidth(30);
                fifthColumn.setPreferredWidth(100);

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
        thVector.add("工号");
        thVector.add("姓名");
        thVector.add("性别");
        thVector.add("年龄");
        thVector.add("入职时间");
        thVector.add("职务");
        thVector.add("备注信息");

        Vector<Vector<String>> dataVector = new Vector<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * from employee";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                vector.add(rs.getString(6));
                vector.add(rs.getString(7));
                dataVector.add(vector);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        TableColumn thirdColumn = columnModel.getColumn(2);
        TableColumn forthColumn = columnModel.getColumn(3);
        TableColumn fifthColumn = columnModel.getColumn(4);
        thirdColumn.setPreferredWidth(30);
        forthColumn.setPreferredWidth(30);
        fifthColumn.setPreferredWidth(100);
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
