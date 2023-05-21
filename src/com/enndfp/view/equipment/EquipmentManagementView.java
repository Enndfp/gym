package com.enndfp.view.equipment;

import com.enndfp.pojo.Equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.EventObject;
import java.util.Vector;

/**
 * @author Enndfp
 * @date 2023/3/12
 */
public class EquipmentManagementView extends JPanel{
    //滚动面板
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private JTextField queryField = new JTextField();
    private JButton deleteButton = new JButton("删除");
    private JButton updateButton = new JButton("编辑");
    private JButton addButton = new JButton("添加");
    private JButton queryButton = new JButton("查询");

    public EquipmentManagementView() {
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
        queryButton.setFont(new Font("黑体",Font.BOLD,18));
        add(queryButton);

        updateButton.setBounds(190, 470, 80, 30);
        updateButton.setFont(new Font("黑体",Font.BOLD,18));
        add(updateButton);

        deleteButton.setBounds(390, 470, 80, 30);
        deleteButton.setFont(new Font("黑体",Font.BOLD,18));
        add(deleteButton);

        addButton.setBounds(580, 470, 80, 30);
        addButton.setFont(new Font("黑体",Font.BOLD,18));
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
                int count = JOptionPane.showConfirmDialog(null, "确认删除这个器材吗?", "删除界面", JOptionPane.YES_NO_OPTION);
                if (count == 0) {

                    //如果用户选中了就要删除对应数据
                    Connection connection = null;
                    try {
                        //选中行的第一列  就是员工编号
                        String equipmentId = (String) table.getValueAt(row, 0);
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "delete from equipment where equipment_id=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, equipmentId);
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
                if(queryField.getText().isEmpty()){
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
                if(e.getKeyCode()==10){//点击的是回车按钮
                    queryButton.doClick();//点击一次查询按钮
                    queryButton.requestFocus();
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEquipmentView((DefaultTableModel)table.getModel());
            }
        });
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String queryInfo = queryField.getText();
                Vector<String> thVector = new Vector<>();
                thVector.add("器材编号");
                thVector.add("名称");
                thVector.add("位置");
                thVector.add("状态");
                thVector.add("备注信息");
                Vector<Vector<String>> dataVector = new Vector<>();

                Connection connection = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                    String sql;
                    PreparedStatement ps;
                    if (queryInfo.isEmpty() || queryInfo.equals("Search")) {
                        // 查询全部信息
                        sql = "SELECT * from equipment";
                        ps = connection.prepareStatement(sql);
                    } else {
                        // 按条件查询
                        sql = "SELECT * from equipment where  equipment_id like ? or equipment_name like ? " +
                                "or equipment_location like ? or equipment_status like ? or equipment_message like ? ";
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
                TableColumn fivthColumn = columnModel.getColumn(4);
                fivthColumn.setPreferredWidth(150);

                //重置查询框的显示文字和颜色
                queryField.setText("Search");
                queryField.setForeground(Color.lightGray);
                queryField.setMargin(new Insets(0, 10, 0, 0));
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Equipment equipment=new Equipment();
                int row = table.getSelectedRow();//得到用户选中了哪一行
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中");
                    return;
                }
                String equipmentId = (String) table.getValueAt(row, 0);
                String equipmentName = (String) table.getValueAt(row, 1);
                String equipmentLocation = (String) table.getValueAt(row, 2);
                String equipmentStatus = (String) table.getValueAt(row, 3);
                String equipmentMessage = (String) table.getValueAt(row, 4);

                equipment.setEquipmentId(Integer.valueOf(equipmentId));
                equipment.setEquipmentName(equipmentName);
                equipment.setEquipmentLocation(equipmentLocation);
                equipment.setEquipmentStatus(equipmentStatus);
                equipment.setEquipmentMessage(equipmentMessage);

                new UpdateEquipmentView(equipment,(DefaultTableModel)table.getModel());
            }
        });

        add(scrollPane);
        setVisible(true);
    }

    public void updateContent() {
        Vector<String> thVector = new Vector<>();//表头集合
        thVector.add("器材编号");
        thVector.add("名称");
        thVector.add("位置");
        thVector.add("状态");
        thVector.add("备注信息");

        Vector<Vector<String>> dataVector = new Vector<>();

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
            String sql = "SELECT * from equipment";
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
        TableColumn fivthColumn = columnModel.getColumn(4);
        fivthColumn.setPreferredWidth(150);

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
