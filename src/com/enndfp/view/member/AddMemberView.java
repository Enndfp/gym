package com.enndfp.view.member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Enndfp
 * @date 2023/3/13
 */
public class AddMemberView extends JDialog {
    // 定义所有文本框和单选按钮的初始提示文字和默认属性
    private static final String DEFAULT_NAME = "姓名";
    private static final String DEFAULT_AGE = "年龄";
    private static final String DEFAULT_PHONE = "11位手机号";
    private static final String DEFAULT_COURSE = "分钟";
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private static final Color DEFAULT_COLOR = Color.lightGray;

    private JLabel memberCardIdLabel = new JLabel("会员卡号");
    private JTextField memberCardIdField = new JTextField();

    private JLabel memberNameLabel = new JLabel("姓名");
    private JTextField memberNameField = new JTextField();

    private JLabel memberPasswordLabel = new JLabel("密码");
    private JTextField memberPasswordField = new JTextField();

    private JLabel memberGenderLabel = new JLabel("性别");
    private JRadioButton maleButton = new JRadioButton("男");
    private JRadioButton femaleButton = new JRadioButton("女");

    private JLabel memberAgeLabel = new JLabel("年龄");
    private JTextField memberAgeField = new JTextField();

    private JLabel memberPhoneLabel = new JLabel("联系方式");
    private JTextField memberPhoneField = new JTextField();
    private JLabel checkPhoneLabel = new JLabel();

    private JLabel memberCourseLabel = new JLabel("购买课时");
    private JTextField memberCourseField = new JTextField();

    private JButton resetButton = new JButton("重置");
    private JButton addButton = new JButton("添加");

    public AddMemberView(DefaultTableModel defaultTableModel) {
        setTitle("添加会员信息");
        setSize(400, 440);
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

        memberCardIdLabel.setBounds(100, 40, 60, 20);
        panel.add(memberCardIdLabel);
        memberCardIdField.setBounds(180, 40, 100, 20);
        panel.add(memberCardIdField);
        memberCardIdField.setText("系统自动生成");
        memberCardIdField.setFont(new Font("宋体", Font.BOLD, 13));
        memberCardIdField.setEditable(false);

        memberPasswordLabel.setBounds(100, 80, 60, 20);
        panel.add(memberPasswordLabel);
        memberPasswordField.setBounds(180, 80, 100, 20);
        panel.add(memberPasswordField);
        memberPasswordField.setText("初始密码:123456");
        memberPasswordField.setFont(new Font("宋体", Font.BOLD, 13));
        memberPasswordField.setEditable(false);

        memberNameLabel.setBounds(100, 120, 60, 20);
        panel.add(memberNameLabel);
        memberNameField.setBounds(180, 120, 100, 20);
        memberNameField.setText(DEFAULT_NAME);
        memberNameField.setForeground(DEFAULT_COLOR);
        memberNameField.setMargin(new Insets(0, 8, 0, 0));
        memberNameField.setFont(DEFAULT_FONT);
        panel.add(memberNameField);

        memberGenderLabel.setBounds(100, 160, 60, 20);
        panel.add(memberGenderLabel);
        maleButton.setBounds(180, 160, 50, 20);
        panel.add(maleButton);
        femaleButton.setBounds(240, 160, 50, 20);
        panel.add(femaleButton);
        ButtonGroup buttonGroup = new ButtonGroup();//逻辑分组
        buttonGroup.add(maleButton);
        buttonGroup.add(femaleButton);
        maleButton.setSelected(true);//把"男"设置为选中

        memberAgeLabel.setBounds(100, 200, 60, 20);
        panel.add(memberAgeLabel);
        memberAgeField.setBounds(180, 200, 100, 20);
        memberAgeField.setText(DEFAULT_AGE);
        memberAgeField.setForeground(DEFAULT_COLOR);
        memberAgeField.setMargin(new Insets(0, 8, 0, 0));
        memberAgeField.setFont(DEFAULT_FONT);
        panel.add(memberAgeField);

        memberPhoneLabel.setBounds(100, 240, 60, 20);
        panel.add(memberPhoneLabel);
        memberPhoneField.setBounds(180, 240, 100, 20);
        memberPhoneField.setText(DEFAULT_PHONE);
        memberPhoneField.setForeground(DEFAULT_COLOR);
        memberPhoneField.setMargin(new Insets(0, 8, 0, 0));
        memberPhoneField.setFont(DEFAULT_FONT);
        checkPhoneLabel.setBounds(290, 240, 50, 20);
        panel.add(checkPhoneLabel);
        panel.add(memberPhoneField);

        memberCourseLabel.setBounds(100, 280, 60, 20);
        panel.add(memberCourseLabel);
        memberCourseField.setBounds(180, 280, 100, 20);
        memberCourseField.setText(DEFAULT_COURSE);
        memberCourseField.setForeground(DEFAULT_COLOR);
        memberCourseField.setMargin(new Insets(0, 8, 0, 0));
        memberCourseField.setFont(DEFAULT_FONT);
        panel.add(memberCourseField);

        resetButton.setBounds(100, 330, 60, 30);
        resetButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(resetButton);
        addButton.setBounds(220, 330, 60, 30);
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
        memberNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (memberNameField.getText().equals(DEFAULT_NAME)) {
                    memberNameField.setText("");
                    memberNameField.setFont(DEFAULT_FONT);
                    memberNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (memberNameField.getText().isEmpty()) {
                    memberNameField.setText(DEFAULT_NAME);
                    memberNameField.setForeground(DEFAULT_COLOR);
                    memberNameField.setFont(DEFAULT_FONT);
                } else {
                    memberNameField.setForeground(Color.BLACK);
                }
                memberNameField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        memberAgeField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (memberAgeField.getText().equals(DEFAULT_AGE)) {
                    memberAgeField.setText("");
                    memberAgeField.setFont(DEFAULT_FONT);
                    memberAgeField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (memberAgeField.getText().isEmpty()) {
                    memberAgeField.setText(DEFAULT_AGE);
                    memberAgeField.setForeground(DEFAULT_COLOR);
                    memberAgeField.setFont(DEFAULT_FONT);
                } else {
                    memberAgeField.setForeground(Color.BLACK);
                }
                memberAgeField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        memberPhoneField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (memberPhoneField.getText().equals(DEFAULT_PHONE)) {
                    memberPhoneField.setText("");
                    memberPhoneField.setFont(DEFAULT_FONT);
                    memberPhoneField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (memberPhoneField.getText().isEmpty()) {
                    memberPhoneField.setText(DEFAULT_PHONE);
                    memberPhoneField.setForeground(DEFAULT_COLOR);
                    memberPhoneField.setFont(DEFAULT_FONT);
                } else {
                    memberPhoneField.setForeground(Color.BLACK);
                    String emptel = memberPhoneField.getText();//得到用户填写的联系电话
                    if (emptel.matches("1[3-9]\\d{9}")) {
                        checkPhoneLabel.setText("√");
                        checkPhoneLabel.setFont(new Font("宋体", Font.BOLD, 20));
                        checkPhoneLabel.setForeground(Color.GREEN);//设置字体颜色
                    } else {
                        checkPhoneLabel.setText("×");
                        checkPhoneLabel.setFont(new Font("宋体", Font.BOLD, 15));
                        checkPhoneLabel.setForeground(Color.RED);
                    }
                }
                memberPhoneField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        memberCourseField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (memberCourseField.getText().equals(DEFAULT_COURSE)) {
                    memberCourseField.setText("");
                    memberCourseField.setFont(DEFAULT_FONT);
                    memberCourseField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (memberCourseField.getText().isEmpty()) {
                    memberCourseField.setText(DEFAULT_COURSE);
                    memberCourseField.setForeground(DEFAULT_COLOR);
                    memberCourseField.setFont(DEFAULT_FONT);
                } else {
                    memberCourseField.setForeground(Color.BLACK);
                }
                memberCourseField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        //给添加按钮绑定监听事件，添加会员信息
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (memberNameField.getText().equals("姓名") || memberAgeField.getText().equals("年龄") ||
                        memberCourseField.getText().equals("分钟") || checkPhoneLabel.getText().equals("×") ||
                        checkPhoneLabel.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String memberName = memberNameField.getText();
                    String memberAge = memberAgeField.getText();
                    String memberCourse = memberCourseField.getText();
                    String memberPhone = memberPhoneField.getText();
                    String memberGender = "男";
                    if (femaleButton.isSelected()) {
                        memberGender = "女";
                    }

                    //卡号随机生成
                    String memberAccount = "2023";
                    UUID uuid = UUID.randomUUID();
                    int hashCode = uuid.hashCode();
                    String fiveDigitCode = String.valueOf(Math.abs(hashCode) % 100000);
                    while (fiveDigitCode.length() != 5) {
                        uuid = UUID.randomUUID();
                        hashCode = uuid.hashCode();
                        fiveDigitCode = String.valueOf(Math.abs(hashCode) % 100000);
                    }
                    memberAccount += fiveDigitCode;

                    //初始密码
                    String memberPassword = "123456";

                    //获取当前日期
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String cardTime = simpleDateFormat.format(date);

                    Connection connection = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "insert into member(member_account,member_password,member_name,member_gender," +
                                "member_age,member_phone,card_time,card_class,card_next_class) values(?,?,?,?,?,?,?,?,?)";

                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, memberAccount);
                        ps.setString(2, memberPassword);
                        ps.setString(3, memberName);
                        ps.setString(4, memberGender);
                        ps.setString(5, memberAge);
                        ps.setString(6, memberPhone);
                        ps.setString(7, cardTime);
                        ps.setString(8, memberCourse);
                        ps.setString(9, memberCourse);

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
                                String sql2 = "SELECT * FROM member";
                                PreparedStatement ps2 = connection2.prepareStatement(sql2);
                                ResultSet rs = ps2.executeQuery();
                                while (rs.next()) {
                                    // 将查询结果添加到表模型中
                                    defaultTableModel.addRow(new Object[]{
                                            rs.getString(1),
                                            rs.getString(3),
                                            rs.getString(4),
                                            rs.getString(5),
                                            rs.getString(10),
                                            rs.getString(11),
                                            rs.getString(8),
                                            rs.getString(9)
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
        setVisible(true);//显示模态对话框
    }

    // 将所有文本框和单选按钮恢复到初始状态的reset()方法
    private void reset() {
        memberNameField.setText(DEFAULT_NAME);
        memberNameField.setForeground(DEFAULT_COLOR);
        memberNameField.setFont(DEFAULT_FONT);

        memberAgeField.setText(DEFAULT_AGE);
        memberAgeField.setForeground(DEFAULT_COLOR);
        memberAgeField.setFont(DEFAULT_FONT);

        memberPhoneField.setText(DEFAULT_PHONE);
        memberPhoneField.setForeground(DEFAULT_COLOR);
        memberPhoneField.setFont(DEFAULT_FONT);

        memberCourseField.setText(DEFAULT_COURSE);
        memberCourseField.setForeground(DEFAULT_COLOR);
        memberCourseField.setFont(DEFAULT_FONT);

        maleButton.setSelected(true);
        checkPhoneLabel.setText("");
    }

}