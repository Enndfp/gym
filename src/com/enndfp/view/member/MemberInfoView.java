package com.enndfp.view.member;

import com.enndfp.pojo.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class MemberInfoView extends JPanel{
    private String account;
    private Member member = new Member();
    private ArrayList<JTextField> fields =new ArrayList<>();
    private JLabel memberCardIdLabel = new JLabel("会员卡号");
    private JTextField memberCardIdField = new JTextField();

    private JLabel memberNameLabel = new JLabel("姓名");
    private JTextField memberNameField = new JTextField();

    private JLabel memberGenderLabel = new JLabel("性别");
    private JTextField memberGenderField = new JTextField();

    private JLabel memberAgeLabel = new JLabel("年龄");
    private JTextField memberAgeField = new JTextField();

    private JLabel memberHeightLabel = new JLabel("身高");
    private JTextField memberHeightField = new JTextField();

    private JLabel memberWeightLabel = new JLabel("体重");
    private JTextField memberWeightField = new JTextField();

    private JLabel memberPhoneLabel = new JLabel("联系方式");
    private JTextField memberPhoneField = new JTextField();

    private JLabel memberCardTimeLabel = new JLabel("办卡时间");
    private JTextField memberCardTimeField = new JTextField();

    private JLabel memberCourseLabel = new JLabel("剩余课时");
    private JTextField memberCourseField = new JTextField();

    private JButton updateButton = new JButton("编辑个人信息");
    private Font font = new Font("宋体", Font.BOLD, 20);

    public MemberInfoView(String account) {
        setLayout(null);//绝对布局
        this.account=account;
        queryMemberInfo();

        memberCardIdLabel.setBounds(220, 50, 100, 30);
        memberCardIdField.setBounds(480, 50, 150, 30);
        memberCardIdLabel.setFont(font);
        memberCardIdField.setFont(font);
        memberCardIdField.setOpaque(false);
        memberCardIdField.setEditable(false);
        memberCardIdField.setBackground(new Color(0, 0, 0, 0));
        memberCardIdField.setBorder(BorderFactory.createEmptyBorder());
        memberCardIdField.setText(String.valueOf(member.getMemberAccount()));
        add(memberCardIdLabel);
        add(memberCardIdField);

        memberNameLabel.setBounds(220, 95, 100, 30);
        memberNameField.setBounds(480, 95, 150, 30);
        memberNameLabel.setFont(font);
        memberNameField.setFont(font);
        memberNameField.setOpaque(false);
        memberNameField.setEditable(false);
        memberNameField.setBackground(new Color(0, 0, 0, 0));
        memberNameField.setBorder(BorderFactory.createEmptyBorder());
        memberNameField.setText(member.getMemberName());
        add(memberNameLabel);
        add(memberNameField);

        memberGenderLabel.setBounds(220, 140, 100, 30);
        memberGenderField.setBounds(480, 140, 150, 30);
        memberGenderLabel.setFont(font);
        memberGenderField.setFont(font);
        memberGenderField.setOpaque(false);
        memberGenderField.setEditable(false);
        memberGenderField.setBackground(new Color(0, 0, 0, 0));
        memberGenderField.setBorder(BorderFactory.createEmptyBorder());
        memberGenderField.setText(member.getMemberGender());
        add(memberGenderLabel);
        add(memberGenderField);

        memberAgeLabel.setBounds(220, 185, 100, 30);
        memberAgeField.setBounds(480, 185, 150, 30);
        memberAgeLabel.setFont(font);
        memberAgeField.setFont(font);
        memberAgeField.setOpaque(false);
        memberAgeField.setEditable(false);
        memberAgeField.setBackground(new Color(0, 0, 0, 0));
        memberAgeField.setBorder(BorderFactory.createEmptyBorder());
        memberAgeField.setText(String.valueOf(member.getMemberAge()));
        add(memberAgeLabel);
        add(memberAgeField);

        memberHeightLabel.setBounds(220, 230, 100, 30);
        memberHeightField.setBounds(480, 230, 150, 30);
        memberHeightLabel.setFont(font);
        memberHeightField.setFont(font);
        memberHeightField.setOpaque(false);
        memberHeightField.setEditable(false);
        memberHeightField.setBackground(new Color(0, 0, 0, 0));
        memberHeightField.setBorder(BorderFactory.createEmptyBorder());
        memberHeightField.setText(String.valueOf(member.getMemberHeight()));
        add(memberHeightLabel);
        add(memberHeightField);

        memberWeightLabel.setBounds(220, 275, 100, 30);
        memberWeightField.setBounds(480, 275, 150, 30);
        memberWeightLabel.setFont(font);
        memberWeightField.setFont(font);
        memberWeightField.setOpaque(false);
        memberWeightField.setEditable(false);
        memberWeightField.setBackground(new Color(0, 0, 0, 0));
        memberWeightField.setBorder(BorderFactory.createEmptyBorder());
        memberWeightField.setText(String.valueOf(member.getMemberWeight()));
        add(memberWeightLabel);
        add(memberWeightField);

        memberPhoneLabel.setBounds(220, 320, 100, 30);
        memberPhoneField.setBounds(480, 320, 150, 30);
        memberPhoneLabel.setFont(font);
        memberPhoneField.setFont(font);
        memberPhoneField.setOpaque(false);
        memberPhoneField.setEditable(false);
        memberPhoneField.setBackground(new Color(0, 0, 0, 0));
        memberPhoneField.setBorder(BorderFactory.createEmptyBorder());
        memberPhoneField.setText(member.getMemberPhone());
        add(memberPhoneLabel);
        add(memberPhoneField);

        memberCardTimeLabel.setBounds(220, 365, 100, 30);
        memberCardTimeField.setBounds(480, 365, 150, 30);
        memberCardTimeLabel.setFont(font);
        memberCardTimeField.setFont(font);
        memberCardTimeField.setOpaque(false);
        memberCardTimeField.setEditable(false);
        memberCardTimeField.setBackground(new Color(0, 0, 0, 0));
        memberCardTimeField.setBorder(BorderFactory.createEmptyBorder());
        memberCardTimeField.setText(member.getCardTime());
        add(memberCardTimeLabel);
        add(memberCardTimeField);

        memberCourseLabel.setBounds(220, 410, 100, 30);
        memberCourseField.setBounds(480, 410, 150, 30);
        memberCourseLabel.setFont(font);
        memberCourseField.setFont(font);
        memberCourseField.setOpaque(false);
        memberCourseField.setEditable(false);
        memberCourseField.setBackground(new Color(0, 0, 0, 0));
        memberCourseField.setBorder(BorderFactory.createEmptyBorder());
        memberCourseField.setText(String.valueOf(member.getCardNextClass()));
        add(memberCourseLabel);
        add(memberCourseField);

        updateButton.setBounds(290, 480, 200, 40);
        updateButton.setFont(font);
        add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fields.add(memberCardIdField);
                fields.add(memberNameField);
                fields.add(memberGenderField);
                fields.add(memberAgeField);
                fields.add(memberHeightField);
                fields.add(memberWeightField);
                fields.add(memberCourseField);

                queryMemberInfo();
                new ModifyMemberView(member,fields);
            }
        });

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageIcon("D:\\图片\\gym4.jpg").getImage(); // 背景图片路径
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    public void queryMemberInfo(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
            String sql = "select * from member where member_account=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                member.setMemberAccount(Integer.valueOf(rs.getString(1)));
                member.setMemberPassword(rs.getString(2));
                member.setMemberName(rs.getString(3));
                member.setMemberGender(rs.getString(4));
                member.setMemberAge(Integer.valueOf(rs.getString(5)));
                member.setMemberHeight(Integer.valueOf(rs.getString(6)));
                member.setMemberWeight(Integer.valueOf(rs.getString(7)));
                member.setMemberPhone(rs.getString(8));
                member.setCardTime(rs.getString(9));
                member.setCardClass(Integer.valueOf(rs.getString(10)));
                member.setCardNextClass(Integer.valueOf(rs.getString(11)));

                memberCardIdField.setText(String.valueOf(member.getMemberAccount()));
                memberNameField.setText(member.getMemberName());
                memberGenderField.setText(member.getMemberGender());
                memberAgeField.setText(String.valueOf(member.getMemberAge()));
                memberHeightField.setText(String.valueOf(member.getMemberHeight()));
                memberWeightField.setText(String.valueOf(member.getMemberWeight()));
                memberPhoneField.setText(member.getMemberPhone());
                memberCardTimeField.setText(member.getCardTime());
                memberCourseField.setText(String.valueOf(member.getCardNextClass()));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

