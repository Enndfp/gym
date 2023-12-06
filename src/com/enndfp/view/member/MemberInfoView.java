package com.enndfp.view.member;

import com.enndfp.pojo.Member;
import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * 会员信息展示
 *
 * @author Enndfp
 */
public class MemberInfoView extends JPanel {
    private String account;
    private Member member = new Member();
    private ArrayList<JTextField> fields = new ArrayList<>();
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
        setLayout(null); // 绝对布局
        this.account = account;
        queryMemberInfo();

        memberCardIdLabel.setBounds(240, 50, 100, 30);
        memberCardIdField.setBounds(500, 50, 150, 30);
        memberCardIdLabel.setFont(font);
        memberCardIdField.setFont(font);
        memberCardIdLabel.setForeground(Color.ORANGE);
        memberCardIdField.setForeground(Color.ORANGE);
        memberCardIdField.setOpaque(false); // 是否透明，true不透明，false透明
        memberCardIdField.setEditable(false);
        memberCardIdField.setBackground(new Color(0, 0, 0, 0));
        memberCardIdField.setBorder(BorderFactory.createEmptyBorder());
        memberCardIdField.setText(String.valueOf(member.getMemberAccount()));
        add(memberCardIdLabel);
        add(memberCardIdField);

        memberNameLabel.setBounds(240, 95, 100, 30);
        memberNameField.setBounds(500, 95, 150, 30);
        memberNameLabel.setFont(font);
        memberNameField.setFont(font);
        memberNameLabel.setForeground(Color.ORANGE);
        memberNameField.setForeground(Color.ORANGE);
        memberNameField.setOpaque(false);
        memberNameField.setEditable(false);
        memberNameField.setBackground(new Color(0, 0, 0, 0));
        memberNameField.setBorder(BorderFactory.createEmptyBorder());
        memberNameField.setText(member.getMemberName());
        add(memberNameLabel);
        add(memberNameField);

        memberGenderLabel.setBounds(240, 140, 100, 30);
        memberGenderField.setBounds(500, 140, 150, 30);
        memberGenderLabel.setFont(font);
        memberGenderField.setFont(font);
        memberGenderLabel.setForeground(Color.ORANGE);
        memberGenderField.setForeground(Color.ORANGE);
        memberGenderField.setOpaque(false);
        memberGenderField.setEditable(false);
        memberGenderField.setBackground(new Color(0, 0, 0, 0));
        memberGenderField.setBorder(BorderFactory.createEmptyBorder());
        memberGenderField.setText(member.getMemberGender());
        add(memberGenderLabel);
        add(memberGenderField);

        memberAgeLabel.setBounds(240, 185, 100, 30);
        memberAgeField.setBounds(500, 185, 150, 30);
        memberAgeLabel.setFont(font);
        memberAgeField.setFont(font);
        memberAgeLabel.setForeground(Color.ORANGE);
        memberAgeField.setForeground(Color.ORANGE);
        memberAgeField.setOpaque(false);
        memberAgeField.setEditable(false);
        memberAgeField.setBackground(new Color(0, 0, 0, 0));
        memberAgeField.setBorder(BorderFactory.createEmptyBorder());
        memberAgeField.setText(String.valueOf(member.getMemberAge()));
        add(memberAgeLabel);
        add(memberAgeField);

        memberHeightLabel.setBounds(240, 230, 100, 30);
        memberHeightField.setBounds(500, 230, 150, 30);
        memberHeightLabel.setFont(font);
        memberHeightField.setFont(font);
        memberHeightLabel.setForeground(Color.ORANGE);
        memberHeightField.setForeground(Color.ORANGE);
        memberHeightField.setOpaque(false);
        memberHeightField.setEditable(false);
        memberHeightField.setBackground(new Color(0, 0, 0, 0));
        memberHeightField.setBorder(BorderFactory.createEmptyBorder());
        memberHeightField.setText(String.valueOf(member.getMemberHeight()));
        add(memberHeightLabel);
        add(memberHeightField);

        memberWeightLabel.setBounds(240, 275, 100, 30);
        memberWeightField.setBounds(500, 275, 150, 30);
        memberWeightLabel.setFont(font);
        memberWeightField.setFont(font);
        memberWeightLabel.setForeground(Color.ORANGE);
        memberWeightField.setForeground(Color.ORANGE);
        memberWeightField.setOpaque(false);
        memberWeightField.setEditable(false);
        memberWeightField.setBackground(new Color(0, 0, 0, 0));
        memberWeightField.setBorder(BorderFactory.createEmptyBorder());
        memberWeightField.setText(String.valueOf(member.getMemberWeight()));
        add(memberWeightLabel);
        add(memberWeightField);

        memberPhoneLabel.setBounds(240, 320, 100, 30);
        memberPhoneField.setBounds(500, 320, 150, 30);
        memberPhoneLabel.setFont(font);
        memberPhoneField.setFont(font);
        memberPhoneLabel.setForeground(Color.ORANGE);
        memberPhoneField.setForeground(Color.ORANGE);
        memberPhoneField.setOpaque(false);
        memberPhoneField.setEditable(false);
        memberPhoneField.setBackground(new Color(0, 0, 0, 0));
        memberPhoneField.setBorder(BorderFactory.createEmptyBorder());
        memberPhoneField.setText(member.getMemberPhone());
        add(memberPhoneLabel);
        add(memberPhoneField);

        memberCardTimeLabel.setBounds(240, 365, 100, 30);
        memberCardTimeField.setBounds(500, 365, 150, 30);
        memberCardTimeLabel.setFont(font);
        memberCardTimeField.setFont(font);
        memberCardTimeLabel.setForeground(Color.ORANGE);
        memberCardTimeField.setForeground(Color.ORANGE);
        memberCardTimeField.setOpaque(false);
        memberCardTimeField.setEditable(false);
        memberCardTimeField.setBackground(new Color(0, 0, 0, 0));
        memberCardTimeField.setBorder(BorderFactory.createEmptyBorder());
        memberCardTimeField.setText(member.getCardTime());
        add(memberCardTimeLabel);
        add(memberCardTimeField);

        memberCourseLabel.setBounds(240, 410, 100, 30);
        memberCourseField.setBounds(500, 410, 150, 30);
        memberCourseLabel.setFont(font);
        memberCourseField.setFont(font);
        memberCourseLabel.setForeground(Color.ORANGE);
        memberCourseField.setForeground(Color.ORANGE);
        memberCourseField.setOpaque(false);
        memberCourseField.setEditable(false);
        memberCourseField.setBackground(new Color(0, 0, 0, 0));
        memberCourseField.setBorder(BorderFactory.createEmptyBorder());
        memberCourseField.setText(String.valueOf(member.getCardNextClass()));
        add(memberCourseLabel);
        add(memberCourseField);

        updateButton.setBounds(310, 500, 200, 40);
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
                new ModifyMemberView(member, fields);
            }
        });

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageIcon("src/com/enndfp/image/member.jpg").getImage(); // 背景图片路径
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    public void queryMemberInfo() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from member where member_account=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, account);

            rs = ps.executeQuery();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.getClose(connection, ps, rs);
        }
    }
}

