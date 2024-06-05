package com.enndfp.view.equipment;

import com.enndfp.pojo.Equipment;
import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 修改器材信息界面
 *
 * @author Enndfp
 */
public class UpdateEquipmentView extends JDialog {
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private JLabel equipmentIdLabel = new JLabel("器材编号");
    private JTextField equipmentIdField = new JTextField();

    private JLabel equipmentNameLabel = new JLabel("器材名称");
    private JTextField equipmentNameField = new JTextField();

    private JLabel equipmentLocationLabel = new JLabel("器材位置");
    private JTextField equipmentLocationField = new JTextField();

    private JLabel equipmentStatusLabel = new JLabel("器材状态");
    private JTextField equipmentStatusField = new JTextField();

    private JLabel equipmentMessageLabel = new JLabel("备注信息");
    private JTextField equipmentMessageField = new JTextField();

    private JButton updateButton = new JButton("确认修改");

    public UpdateEquipmentView(Equipment equipment, DefaultTableModel defaultTableModel) {
        setTitle("修改器材信息");
        setSize(400, 360);
        setLocationRelativeTo(null);
        setModal(true); // 设置为模式对话框

        // 创建一个继承自JPanel的匿名类，并重写它的paintComponent方法来绘制背景图片
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/com/enndfp/image/editor.jpg").getImage(); // 背景图片路径
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        equipmentIdLabel.setBounds(100, 40, 60, 20);
        panel.add(equipmentIdLabel);
        equipmentIdField.setBounds(180, 40, 100, 20);
        equipmentIdField.setText(String.valueOf(equipment.getEquipmentId()));
        equipmentIdField.setFont(DEFAULT_FONT);
        equipmentIdField.setMargin(new Insets(0, 8, 0, 0));
        equipmentIdField.setEditable(false);
        panel.add(equipmentIdField);

        equipmentNameLabel.setBounds(100, 80, 60, 20);
        panel.add(equipmentNameLabel);
        equipmentNameField.setBounds(180, 80, 100, 20);
        equipmentNameField.setText(equipment.getEquipmentName());
        equipmentNameField.setFont(DEFAULT_FONT);
        equipmentNameField.setMargin(new Insets(0, 8, 0, 0));
        panel.add(equipmentNameField);

        equipmentLocationLabel.setBounds(100, 120, 60, 20);
        panel.add(equipmentLocationLabel);
        equipmentLocationField.setBounds(180, 120, 100, 20);
        equipmentLocationField.setText(equipment.getEquipmentLocation());
        equipmentLocationField.setMargin(new Insets(0, 8, 0, 0));
        equipmentLocationField.setFont(DEFAULT_FONT);
        panel.add(equipmentLocationField);

        equipmentStatusLabel.setBounds(100, 160, 60, 20);
        panel.add(equipmentStatusLabel);
        equipmentStatusField.setBounds(180, 160, 100, 20);
        equipmentStatusField.setText(equipment.getEquipmentStatus());
        equipmentStatusField.setMargin(new Insets(0, 8, 0, 0));
        equipmentStatusField.setFont(DEFAULT_FONT);
        panel.add(equipmentStatusField);

        equipmentMessageLabel.setBounds(100, 200, 60, 20);
        panel.add(equipmentMessageLabel);
        equipmentMessageField.setBounds(180, 200, 100, 20);
        equipmentMessageField.setText(equipment.getEquipmentMessage());
        equipmentMessageField.setMargin(new Insets(0, 8, 0, 0));
        equipmentMessageField.setFont(DEFAULT_FONT);
        panel.add(equipmentMessageField);

        updateButton.setBounds(150, 250, 100, 30);
        updateButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (equipmentNameField.getText().isEmpty() || equipmentLocationField.getText().isEmpty() ||
                        equipmentStatusField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String equipmentId = String.valueOf(equipment.getEquipmentId());
                    String equipmentName = equipmentNameField.getText();
                    String equipmentLocation = equipmentLocationField.getText();
                    String equipmentStatus = equipmentStatusField.getText();
                    String equipmentMessage = equipmentMessageField.getText();

                    Connection connection = null;
                    PreparedStatement ps = null;
                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "update equipment set equipment_name=?,equipment_location=?,equipment_status=?," +
                                "equipment_message=? WHERE equipment_id =?";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, equipmentName);
                        ps.setString(2, equipmentLocation);
                        ps.setString(3, equipmentStatus);
                        ps.setString(4, equipmentMessage);
                        ps.setString(5, equipmentId);

                        int n = ps.executeUpdate();
                        if (n == 1) {
                            JOptionPane.showMessageDialog(null, "修改成功");
                            dispose();

                            // 刷新表数据
                            defaultTableModel.setRowCount(0);
                            Connection connection2 = null;
                            PreparedStatement ps2 = null;
                            ResultSet rs = null;
                            try {
                                connection2 = JDBCUtil.getConnection();
                                String sql2 = "SELECT * FROM equipment";
                                ps2 = connection2.prepareStatement(sql2);
                                rs = ps2.executeQuery();
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
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } finally {
                                JDBCUtil.getClose(connection2, ps2, rs);
                                defaultTableModel.fireTableDataChanged();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "修改失败");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JDBCUtil.getClose(connection, ps, null);
                    }
                }
            }
        });
        add(panel);
        setVisible(true);
    }
}


