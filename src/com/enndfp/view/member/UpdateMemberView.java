package com.enndfp.view.member;

import com.enndfp.pojo.Member;

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
 * @date 2023/3/15
 */
public class UpdateMemberView extends JDialog {
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private JLabel memberCardIdLabel = new JLabel("会员卡号");
    private JTextField memberCardIdField = new JTextField();

    private JLabel memberPasswordLabel = new JLabel("密码");
    private JTextField memberPasswordField = new JTextField();

    private JLabel memberNameLabel = new JLabel("姓名");
    private JTextField memberNameField = new JTextField();

    private JLabel memberGenderLabel = new JLabel("性别");
    private JRadioButton maleButton = new JRadioButton("男");
    private JRadioButton femaleButton = new JRadioButton("女");

    private JLabel memberAgeLabel = new JLabel("年龄");
    private JTextField memberAgeField = new JTextField();

    private JLabel memberCourseLabel = new JLabel("购买课时");
    private JTextField memberCourseField = new JTextField();

    private JLabel memberCourseLabel2 = new JLabel("剩余课时");
    private JTextField memberCourseField2 = new JTextField();

    private JLabel memberPhoneLabel = new JLabel("联系方式");
    private JTextField memberPhoneField = new JTextField();
    private JLabel checkPhoneLabel = new JLabel();

    private JButton updateButton = new JButton("确认修改");

    public UpdateMemberView(Member member, DefaultTableModel defaultTableModel) {
        setTitle("修改会员信息");
        setSize(400, 460);
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
        memberCardIdField.setText(String.valueOf(member.getMemberAccount()));
        memberCardIdField.setFont(DEFAULT_FONT);
        memberCardIdField.setMargin(new Insets(0, 8, 0, 0));
        memberCardIdField.setEditable(false);
        panel.add(memberCardIdField);

        memberPasswordLabel.setBounds(100, 80, 60, 20);
        panel.add(memberPasswordLabel);
        memberPasswordField.setBounds(180, 80, 100, 20);
        memberPasswordField.setText(member.getMemberPassword());
        memberPasswordField.setFont(DEFAULT_FONT);
        memberPasswordField.setMargin(new Insets(0, 8, 0, 0));
        memberPasswordField.setEditable(false);
        panel.add(memberPasswordField);

        memberNameLabel.setBounds(100, 120, 60, 20);
        panel.add(memberNameLabel);
        memberNameField.setBounds(180, 120, 100, 20);
        memberNameField.setText(member.getMemberName());
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
        maleButton.setSelected(true);
        if (member.getMemberGender().equals("女")) {
            femaleButton.setSelected(true);
        }

        memberAgeLabel.setBounds(100, 200, 60, 20);
        panel.add(memberAgeLabel);
        memberAgeField.setBounds(180, 200, 100, 20);
        memberAgeField.setFont(DEFAULT_FONT);
        memberAgeField.setMargin(new Insets(0, 8, 0, 0));
        memberAgeField.setText(String.valueOf(member.getMemberAge()));
        panel.add(memberAgeField);

        memberCourseLabel.setBounds(100, 240, 60, 20);
        panel.add(memberCourseLabel);
        memberCourseField.setBounds(180, 240, 100, 20);
        memberCourseField.setMargin(new Insets(0, 8, 0, 0));
        memberCourseField.setFont(DEFAULT_FONT);
        memberCourseField.setText(String.valueOf(member.getCardClass()));
        panel.add(memberCourseField);

        memberCourseLabel2.setBounds(100, 280, 60, 20);
        panel.add(memberCourseLabel2);
        memberCourseField2.setBounds(180, 280, 100, 20);
        memberCourseField2.setMargin(new Insets(0, 8, 0, 0));
        memberCourseField2.setFont(DEFAULT_FONT);
        memberCourseField2.setText(String.valueOf(member.getCardNextClass()));
        panel.add(memberCourseField2);

        memberPhoneLabel.setBounds(100, 320, 60, 20);
        panel.add(memberPhoneLabel);
        memberPhoneField.setBounds(180, 320, 100, 20);
        memberPhoneField.setFont(DEFAULT_FONT);
        memberPhoneField.setMargin(new Insets(0, 8, 0, 0));
        memberPhoneField.setText(String.valueOf(member.getMemberPhone()));
        checkPhoneLabel.setBounds(290, 320, 50, 20);
        panel.add(checkPhoneLabel);
        panel.add(memberPhoneField);

        updateButton.setBounds(150, 370, 100, 30);
        updateButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(updateButton);

        memberPhoneField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
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
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (memberNameField.getText().isEmpty() || memberAgeField.getText().isEmpty() ||
                        memberCourseField.getText().isEmpty() || checkPhoneLabel.getText().equals("×") ||
                        memberCourseField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String memberAccount = String.valueOf(member.getMemberAccount());
                    String memberPassword = member.getMemberPassword();
                    String memberName = memberNameField.getText();
                    String memberAge = memberAgeField.getText();
                    String memberCourse = memberCourseField.getText();
                    String memberCourse2 = memberCourseField2.getText();
                    String memberPhone = memberPhoneField.getText();
                    String memberGender = "男";
                    if (femaleButton.isSelected()) {
                        memberGender = "女";
                    }

                    Connection connection = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                        String sql = "update member set member_account=?,member_password=?,member_name=?,member_gender=?," +
                                "member_age=?,member_phone=?,card_class=?,card_next_class=? WHERE member_account =?";

                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, memberAccount);
                        ps.setString(2, memberPassword);
                        ps.setString(3, memberName);
                        ps.setString(4, memberGender);
                        ps.setString(5, memberAge);
                        ps.setString(6, memberPhone);
                        ps.setString(7, memberCourse);
                        ps.setString(8, memberCourse2);
                        ps.setString(9, memberAccount);

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
