package com.enndfp.view.equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

/**
 * @author Enndfp
 * @date 2023/3/17
 */
public class AddEquipmentView extends JDialog {
    private static final String DEFAULT_NAME = "器材名称";
    private static final String DEFAULT_LOCATION = "X号房间";
    private static final String DEFAULT_STATUS = "器材状态";
    private static final String DEFAULT_MESSAGE = "备注信息";
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private static final Color DEFAULT_COLOR = Color.lightGray;

    private JLabel equipmentNameLabel = new JLabel("器材名称");
    private JTextField equipmentNameField = new JTextField();

    private JLabel equipmentLocationLabel = new JLabel("器材位置");
    private JTextField equipmentLocationField = new JTextField();

    private JLabel equipmentStatusLabel = new JLabel("器材状态");
    private JTextField equipmentStatusField = new JTextField();

    private JLabel equipmentMessageLabel = new JLabel("备注信息");
    private JTextField equipmentMessageField = new JTextField();

    private JButton resetButton = new JButton("重置");
    private JButton addButton = new JButton("添加");

    public AddEquipmentView(DefaultTableModel defaultTableModel) {
        setTitle("添加器材信息");
        setSize(400, 310);
        setLocationRelativeTo(null);
        setModal(true);//设置为模式对话框

        // 创建一个继承自JPanel的匿名类，并重写它的paintComponent方法来绘制背景图片
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("D:\\图片\\gym3.jpg").getImage(); // 背景图片路径
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        equipmentNameLabel.setBounds(100, 40, 60, 20);
        panel.add(equipmentNameLabel);
        equipmentNameField.setBounds(180, 40, 100, 20);
        equipmentNameField.setText(DEFAULT_NAME);
        equipmentNameField.setForeground(DEFAULT_COLOR);
        equipmentNameField.setMargin(new Insets(0, 8, 0, 0));
        equipmentNameField.setFont(DEFAULT_FONT);
        panel.add(equipmentNameField);

        equipmentLocationLabel.setBounds(100, 80, 60, 20);
        panel.add(equipmentLocationLabel);
        equipmentLocationField.setBounds(180, 80, 100, 20);
        equipmentLocationField.setText(DEFAULT_LOCATION);
        equipmentLocationField.setForeground(DEFAULT_COLOR);
        equipmentLocationField.setMargin(new Insets(0, 8, 0, 0));
        equipmentLocationField.setFont(DEFAULT_FONT);
        panel.add(equipmentLocationField);

        equipmentStatusLabel.setBounds(100, 120, 60, 20);
        panel.add(equipmentStatusLabel);
        equipmentStatusField.setBounds(180, 120, 100, 20);
        equipmentStatusField.setText(DEFAULT_STATUS);
        equipmentStatusField.setForeground(DEFAULT_COLOR);
        equipmentStatusField.setMargin(new Insets(0, 8, 0, 0));
        equipmentStatusField.setFont(DEFAULT_FONT);
        panel.add(equipmentStatusField);

        equipmentMessageLabel.setBounds(100, 160, 60, 20);
        panel.add(equipmentMessageLabel);
        equipmentMessageField.setBounds(180, 160, 100, 20);
        equipmentMessageField.setText(DEFAULT_MESSAGE);
        equipmentMessageField.setForeground(DEFAULT_COLOR);
        equipmentMessageField.setMargin(new Insets(0, 8, 0, 0));
        equipmentMessageField.setFont(DEFAULT_FONT);
        panel.add(equipmentMessageField);

        resetButton.setBounds(100, 210, 60, 30);
        resetButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(resetButton);
        addButton.setBounds(220, 210, 60, 30);
        addButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(addButton);

        // 给resetButton按钮添加一个ActionListener，使得当用户点击该按钮时会调用reset()方法
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                resetButton.requestFocusInWindow(); // 将光标移到 resetButton 按钮上
            }
        });

        // 为所有文本框添加FocusListener，使得在获取焦点时清空提示文字并改变字体和颜色，失去焦点时重新设置为默认提示文字
        equipmentNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (equipmentNameField.getText().equals(DEFAULT_NAME)) {
                    equipmentNameField.setText("");
                    equipmentNameField.setFont(DEFAULT_FONT);
                    equipmentNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (equipmentNameField.getText().isEmpty()) {
                    equipmentNameField.setText(DEFAULT_NAME);
                    equipmentNameField.setForeground(DEFAULT_COLOR);
                    equipmentNameField.setFont(DEFAULT_FONT);
                } else {
                    equipmentNameField.setForeground(Color.BLACK);
                }
                equipmentNameField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        equipmentLocationField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (equipmentLocationField.getText().equals(DEFAULT_LOCATION)) {
                    equipmentLocationField.setText("");
                    equipmentLocationField.setFont(DEFAULT_FONT);
                    equipmentLocationField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (equipmentLocationField.getText().isEmpty()) {
                    equipmentLocationField.setText(DEFAULT_LOCATION);
                    equipmentLocationField.setForeground(DEFAULT_COLOR);
                    equipmentLocationField.setFont(DEFAULT_FONT);
                } else {
                    equipmentLocationField.setForeground(Color.BLACK);
                }
                equipmentLocationField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        equipmentStatusField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (equipmentStatusField.getText().equals(DEFAULT_STATUS)) {
                    equipmentStatusField.setText("");
                    equipmentStatusField.setFont(DEFAULT_FONT);
                    equipmentStatusField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (equipmentStatusField.getText().isEmpty()) {
                    equipmentStatusField.setText(DEFAULT_STATUS);
                    equipmentStatusField.setForeground(DEFAULT_COLOR);
                    equipmentStatusField.setFont(DEFAULT_FONT);
                } else {
                    equipmentStatusField.setForeground(Color.BLACK);
                }
                equipmentStatusField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        equipmentMessageField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (equipmentMessageField.getText().equals(DEFAULT_MESSAGE)) {
                    equipmentMessageField.setText("");
                    equipmentMessageField.setFont(DEFAULT_FONT);
                    equipmentMessageField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (equipmentMessageField.getText().isEmpty()) {
                    equipmentMessageField.setText(DEFAULT_MESSAGE);
                    equipmentMessageField.setForeground(DEFAULT_COLOR);
                    equipmentMessageField.setFont(DEFAULT_FONT);
                } else {
                    equipmentMessageField.setForeground(Color.BLACK);
                }
                equipmentMessageField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        //给添加按钮绑定监听事件，添加员工信息
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (equipmentNameField.getText().equals(DEFAULT_NAME) || equipmentLocationField.getText().equals(DEFAULT_LOCATION) ||
                        equipmentStatusField.getText().equals(DEFAULT_STATUS)) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String equipmentName = equipmentNameField.getText();
                    String equipmentLocation = equipmentLocationField.getText();
                    String equipmentStatus = equipmentStatusField.getText();
                    String equipmentMessage = equipmentMessageField.getText();
                    if (equipmentMessageField.getText().equals(DEFAULT_MESSAGE)) {
                        equipmentMessage = "";
                    }

                    Connection connection = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "insert into equipment(equipment_name,equipment_location,equipment_status,equipment_message) values(?,?,?,?)";

                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, equipmentName);
                        ps.setString(2, equipmentLocation);
                        ps.setString(3, equipmentStatus);
                        ps.setString(4, equipmentMessage);

                        int n = ps.executeUpdate();
                        if (n == 1) {
                            JOptionPane.showMessageDialog(null, "添加成功");
                            dispose();

                            //刷新表数据
                            defaultTableModel.setRowCount(0);
                            Connection connection2 = null;
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                connection2 = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                                String sql2 = "SELECT * FROM equipment";
                                PreparedStatement ps2 = connection2.prepareStatement(sql2);
                                ResultSet rs = ps2.executeQuery();
                                while (rs.next()) {
                                    // 将查询结果添加到表模型中
                                    defaultTableModel.addRow(new Object[]{
                                            rs.getString(1),
                                            rs.getString(2),
                                            rs.getString(3),
                                            rs.getString(4),
                                            rs.getString(5)
                                    });
                                }
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } finally {
                                try {
                                    connection2.close();
                                    defaultTableModel.fireTableDataChanged();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败");
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

        add(panel);
        setVisible(true);
    }

    // 将所有文本框和单选按钮恢复到初始状态的reset()方法
    private void reset() {
        equipmentNameField.setText(DEFAULT_NAME);
        equipmentNameField.setForeground(DEFAULT_COLOR);
        equipmentNameField.setFont(DEFAULT_FONT);

        equipmentLocationField.setText(DEFAULT_LOCATION);
        equipmentLocationField.setForeground(DEFAULT_COLOR);
        equipmentLocationField.setFont(DEFAULT_FONT);

        equipmentStatusField.setText(DEFAULT_STATUS);
        equipmentStatusField.setForeground(DEFAULT_COLOR);
        equipmentStatusField.setFont(DEFAULT_FONT);

        equipmentMessageField.setText(DEFAULT_MESSAGE);
        equipmentMessageField.setForeground(DEFAULT_COLOR);
        equipmentMessageField.setFont(DEFAULT_FONT);
    }
}
