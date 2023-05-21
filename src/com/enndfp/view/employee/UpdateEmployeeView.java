package com.enndfp.view.employee;

import com.enndfp.pojo.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * @author Enndfp
 * @date 2023/3/17
 */
public class UpdateEmployeeView extends JDialog {

    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private JLabel employeeAccountLabel = new JLabel("工号");
    private JTextField employeeAccountField = new JTextField();

    private JLabel employeeNameLabel = new JLabel("姓名");
    private JTextField employeeNameField = new JTextField();

    private JLabel employeeGenderLabel = new JLabel("性别");
    private JRadioButton maleButton = new JRadioButton("男");
    private JRadioButton femaleButton = new JRadioButton("女");

    private JLabel employeeAgeLabel = new JLabel("年龄");
    private JTextField employeeAgeField = new JTextField();

    private JLabel employeeStaffLabel = new JLabel("职务");
    private JTextField employeeStaffField = new JTextField();

    private JLabel employeeMessageLabel = new JLabel("备注信息");
    private JTextField employeeMessageField = new JTextField();

    private JButton updateButton = new JButton("确认修改");

    public UpdateEmployeeView(Employee employee, DefaultTableModel defaultTableModel) {
        setTitle("修改员工信息");
        setSize(400, 400);
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

        employeeAccountLabel.setBounds(100, 40, 60, 20);
        panel.add(employeeAccountLabel);
        employeeAccountField.setBounds(180, 40, 100, 20);
        employeeAccountField.setText(String.valueOf(employee.getEmployeeAccount()));
        employeeAccountField.setFont(DEFAULT_FONT);
        employeeAccountField.setMargin(new Insets(0, 8, 0, 0));
        employeeAccountField.setEditable(false);
        panel.add(employeeAccountField);

        employeeNameLabel.setBounds(100, 80, 60, 20);
        panel.add(employeeNameLabel);
        employeeNameField.setBounds(180, 80, 100, 20);
        employeeNameField.setText(employee.getEmployeeName());
        employeeNameField.setMargin(new Insets(0, 8, 0, 0));
        employeeNameField.setFont(DEFAULT_FONT);
        panel.add(employeeNameField);

        employeeGenderLabel.setBounds(100, 120, 60, 20);
        panel.add(employeeGenderLabel);
        maleButton.setBounds(180, 120, 50, 20);
        panel.add(maleButton);
        femaleButton.setBounds(240, 120, 50, 20);
        panel.add(femaleButton);
        ButtonGroup buttonGroup = new ButtonGroup();//逻辑分组
        buttonGroup.add(maleButton);
        buttonGroup.add(femaleButton);
        maleButton.setSelected(true);
        if (employee.getEmployeeGender().equals("女")) {
            femaleButton.setSelected(true);
        }

        employeeAgeLabel.setBounds(100, 160, 60, 20);
        panel.add(employeeAgeLabel);
        employeeAgeField.setBounds(180, 160, 100, 20);
        employeeAgeField.setFont(DEFAULT_FONT);
        employeeAgeField.setMargin(new Insets(0, 8, 0, 0));
        employeeAgeField.setText(String.valueOf(employee.getEmployeeAge()));
        panel.add(employeeAgeField);

        employeeStaffLabel.setBounds(100, 200, 60, 20);
        panel.add(employeeStaffLabel);
        employeeStaffField.setBounds(180, 200, 100, 20);
        employeeStaffField.setMargin(new Insets(0, 8, 0, 0));
        employeeStaffField.setFont(DEFAULT_FONT);
        employeeStaffField.setText(employee.getStaff());
        panel.add(employeeStaffField);

        employeeMessageLabel.setBounds(100, 240, 60, 20);
        panel.add(employeeMessageLabel);
        employeeMessageField.setBounds(180, 240, 100, 20);
        employeeMessageField.setMargin(new Insets(0, 8, 0, 0));
        employeeMessageField.setFont(DEFAULT_FONT);
        employeeMessageField.setText(employee.getEmployeeMessage());
        panel.add(employeeMessageField);

        updateButton.setBounds(150, 290, 100, 30);
        updateButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (employeeNameField.getText().isEmpty() || employeeAgeField.getText().isEmpty() ||
                        employeeStaffField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String employeeAccount = String.valueOf(employee.getEmployeeAccount());
                    String employeeName = employeeNameField.getText();
                    String employeeAge = employeeAgeField.getText();
                    String employeeStaff = employeeStaffField.getText();
                    String employeeMessage = employeeMessageField.getText();
                    String employeeGender = "男";
                    if (femaleButton.isSelected()) {
                        employeeGender = "女";
                    }

                    Connection connection = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "update employee set employee_account=?,employee_name=?,employee_gender=?,employee_age=?," +
                                "staff=?,employee_message=? WHERE employee_account =?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, employeeAccount);
                        ps.setString(2, employeeName);
                        ps.setString(3, employeeGender);
                        ps.setString(4, employeeAge);
                        ps.setString(5, employeeStaff);
                        ps.setString(6, employeeMessage);
                        ps.setString(7, employeeAccount);

                        int n = ps.executeUpdate();
                        if (n == 1) {
                            JOptionPane.showMessageDialog(null, "修改成功");
                            dispose();

                            //刷新表数据
                            defaultTableModel.setRowCount(0);
                            Connection connection2 = null;
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                connection2 = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                                String sql2 = "SELECT * FROM employee";
                                PreparedStatement ps2 = connection2.prepareStatement(sql2);
                                ResultSet rs = ps2.executeQuery();
                                while (rs.next()) {
                                    // 将查询结果添加到表模型中
                                    defaultTableModel.addRow(new Object[]{
                                            rs.getString(1),
                                            rs.getString(2),
                                            rs.getString(3),
                                            rs.getString(4),
                                            rs.getString(5),
                                            rs.getString(6),
                                            rs.getString(7)
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
                            JOptionPane.showMessageDialog(null, "修改失败");
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
}
