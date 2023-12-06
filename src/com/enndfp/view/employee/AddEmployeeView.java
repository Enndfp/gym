package com.enndfp.view.employee;

import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Enndfp
 * @date 2023/3/17
 */
public class AddEmployeeView extends JDialog {
    // 定义所有文本框和单选按钮的初始提示文字和默认属性
    private static final String DEFAULT_NAME = "姓名";
    private static final String DEFAULT_AGE = "年龄";
    private static final String DEFAULT_STAFF = "职务";
    private static final String DEFAULT_MESSAGE = "备注信息";
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private static final Color DEFAULT_COLOR = Color.lightGray;

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

    private JButton resetButton = new JButton("重置");
    private JButton addButton = new JButton("添加");

    public AddEmployeeView(DefaultTableModel defaultTableModel) {
        setTitle("添加员工信息");
        setSize(400, 390);
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

        employeeAccountLabel.setBounds(100, 40, 60, 20);
        panel.add(employeeAccountLabel);
        employeeAccountField.setBounds(180, 40, 100, 20);
        panel.add(employeeAccountField);
        employeeAccountField.setText("系统自动生成");
        employeeAccountField.setFont(new Font("宋体", Font.BOLD, 13));
        employeeAccountField.setEditable(false);

        employeeNameLabel.setBounds(100, 80, 60, 20);
        panel.add(employeeNameLabel);
        employeeNameField.setBounds(180, 80, 100, 20);
        employeeNameField.setText(DEFAULT_NAME);
        employeeNameField.setForeground(DEFAULT_COLOR);
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
        maleButton.setSelected(true); //把"男"设置为选中

        employeeAgeLabel.setBounds(100, 160, 60, 20);
        panel.add(employeeAgeLabel);
        employeeAgeField.setBounds(180, 160, 100, 20);
        employeeAgeField.setText(DEFAULT_AGE);
        employeeAgeField.setForeground(DEFAULT_COLOR);
        employeeAgeField.setMargin(new Insets(0, 8, 0, 0));
        employeeAgeField.setFont(DEFAULT_FONT);
        panel.add(employeeAgeField);

        employeeStaffLabel.setBounds(100, 200, 60, 20);
        panel.add(employeeStaffLabel);
        employeeStaffField.setBounds(180, 200, 100, 20);
        employeeStaffField.setText(DEFAULT_STAFF);
        employeeStaffField.setForeground(DEFAULT_COLOR);
        employeeStaffField.setMargin(new Insets(0, 8, 0, 0));
        employeeStaffField.setFont(DEFAULT_FONT);
        panel.add(employeeStaffField);

        employeeMessageLabel.setBounds(100, 240, 60, 20);
        panel.add(employeeMessageLabel);
        employeeMessageField.setBounds(180, 240, 100, 20);
        employeeMessageField.setText(DEFAULT_MESSAGE);
        employeeMessageField.setForeground(DEFAULT_COLOR);
        employeeMessageField.setMargin(new Insets(0, 8, 0, 0));
        employeeMessageField.setFont(DEFAULT_FONT);
        panel.add(employeeMessageField);

        resetButton.setBounds(100, 290, 60, 30);
        resetButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(resetButton);
        addButton.setBounds(220, 290, 60, 30);
        addButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(addButton);

        // 给resetButton按钮添加一个ActionListener，使得当用户点击该按钮时会调用reset()方法
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        // 为所有文本框添加FocusListener，使得在获取焦点时清空提示文字并改变字体和颜色，失去焦点时重新设置为默认提示文字
        employeeNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (employeeNameField.getText().equals(DEFAULT_NAME)) {
                    employeeNameField.setText("");
                    employeeNameField.setFont(DEFAULT_FONT);
                    employeeNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (employeeNameField.getText().isEmpty()) {
                    employeeNameField.setText(DEFAULT_NAME);
                    employeeNameField.setForeground(DEFAULT_COLOR);
                    employeeNameField.setFont(DEFAULT_FONT);
                } else {
                    employeeNameField.setForeground(Color.BLACK);
                }
                employeeNameField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        employeeAgeField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (employeeAgeField.getText().equals(DEFAULT_AGE)) {
                    employeeAgeField.setText("");
                    employeeAgeField.setFont(DEFAULT_FONT);
                    employeeAgeField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (employeeAgeField.getText().isEmpty()) {
                    employeeAgeField.setText(DEFAULT_AGE);
                    employeeAgeField.setForeground(DEFAULT_COLOR);
                    employeeAgeField.setFont(DEFAULT_FONT);
                } else {
                    employeeAgeField.setForeground(Color.BLACK);
                }
                employeeAgeField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        employeeStaffField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (employeeStaffField.getText().equals(DEFAULT_STAFF)) {
                    employeeStaffField.setText("");
                    employeeStaffField.setFont(DEFAULT_FONT);
                    employeeStaffField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (employeeStaffField.getText().isEmpty()) {
                    employeeStaffField.setText(DEFAULT_STAFF);
                    employeeStaffField.setForeground(DEFAULT_COLOR);
                    employeeStaffField.setFont(DEFAULT_FONT);
                } else {
                    employeeStaffField.setForeground(Color.BLACK);
                }
                employeeStaffField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        employeeMessageField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (employeeMessageField.getText().equals(DEFAULT_MESSAGE)) {
                    employeeMessageField.setText("");
                    employeeMessageField.setFont(DEFAULT_FONT);
                    employeeMessageField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (employeeMessageField.getText().isEmpty()) {
                    employeeMessageField.setText(DEFAULT_MESSAGE);
                    employeeMessageField.setForeground(DEFAULT_COLOR);
                    employeeMessageField.setFont(DEFAULT_FONT);
                } else {
                    employeeMessageField.setForeground(Color.BLACK);
                }
                employeeMessageField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        // 给添加按钮绑定监听事件，添加员工信息
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (employeeNameField.getText().equals(DEFAULT_NAME) || employeeAgeField.getText().equals(DEFAULT_AGE) ||
                        employeeStaffField.getText().equals(DEFAULT_STAFF)) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String employeeName = employeeNameField.getText();
                    String employeeAge = employeeAgeField.getText();
                    String employeeStaff = employeeStaffField.getText();
                    String employeeMessage = employeeMessageField.getText();
                    if (employeeMessageField.getText().equals("备注信息")) {
                        employeeMessage = "";
                    }
                    String employeeGender = "男";
                    if (femaleButton.isSelected()) {
                        employeeGender = "女";
                    }

                    // 卡号随机生成
                    String employeeAccount = "1010";
                    UUID uuid = UUID.randomUUID();
                    int hashCode = uuid.hashCode();
                    String fiveDigitCode = String.valueOf(Math.abs(hashCode) % 100000);
                    while (fiveDigitCode.length() != 5) {
                        uuid = UUID.randomUUID();
                        hashCode = uuid.hashCode();
                        fiveDigitCode = String.valueOf(Math.abs(hashCode) % 100000);
                    }
                    employeeAccount += fiveDigitCode;


                    // 获取当前日期
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String entryTime = simpleDateFormat.format(date);

                    Connection connection = null;
                    PreparedStatement ps = null;
                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "insert into employee values(?,?,?,?,?,?,?)";

                        ps = connection.prepareStatement(sql);
                        ps.setString(1, employeeAccount);
                        ps.setString(2, employeeName);
                        ps.setString(3, employeeGender);
                        ps.setString(4, employeeAge);
                        ps.setString(5, entryTime);
                        ps.setString(6, employeeStaff);
                        ps.setString(7, employeeMessage);

                        int n = ps.executeUpdate();
                        if (n == 1) {
                            JOptionPane.showMessageDialog(null, "添加成功");
                            dispose();

                            // 刷新表数据
                            defaultTableModel.setRowCount(0);
                            Connection connection2 = null;
                            PreparedStatement ps2 = null;
                            ResultSet rs = null;
                            try {
                                connection2 = JDBCUtil.getConnection();
                                String sql2 = "SELECT * FROM employee";
                                ps2 = connection2.prepareStatement(sql2);
                                rs = ps2.executeQuery();
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
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } finally {
                                JDBCUtil.getClose(connection2, ps2, rs);
                                defaultTableModel.fireTableDataChanged();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败");
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

    // 将所有文本框和单选按钮恢复到初始状态的reset()方法
    private void reset() {
        employeeNameField.setText(DEFAULT_NAME);
        employeeNameField.setForeground(DEFAULT_COLOR);
        employeeNameField.setFont(DEFAULT_FONT);

        employeeAgeField.setText(DEFAULT_AGE);
        employeeAgeField.setForeground(DEFAULT_COLOR);
        employeeAgeField.setFont(DEFAULT_FONT);

        employeeStaffField.setText(DEFAULT_STAFF);
        employeeStaffField.setForeground(DEFAULT_COLOR);
        employeeStaffField.setFont(DEFAULT_FONT);

        employeeMessageField.setText(DEFAULT_MESSAGE);
        employeeMessageField.setForeground(DEFAULT_COLOR);
        employeeMessageField.setFont(DEFAULT_FONT);

        maleButton.setSelected(true);
    }
}
