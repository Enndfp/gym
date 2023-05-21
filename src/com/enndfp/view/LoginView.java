package com.enndfp.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


/**
 * @author Enndfp
 * @date 2023/3/4
 */
public class LoginView extends JFrame {
    private JLabel accountLabel = new JLabel("账号");
    private JTextField accountField = new JTextField();

    private JLabel passwordLabel = new JLabel("密码");
    private JPasswordField passwordField = new JPasswordField();

    private JButton memberButton = new JButton("会员登录");
    private JButton loginButton = new JButton("登录");

    public LoginView() {
        setTitle("健身房管理系统"); //标题
        setSize(400, 300); //大小
        setLocationRelativeTo(null); //居中
        setResizable(false); //大小不可改变
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // 创建一个JPanel来放置所有的组件
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("D:\\图片\\gym2.jpg").getImage(); // 背景图片路径
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        //设置页面布局方式
        panel.setLayout(null); //绝对布局，添加控件之前手动设置大小

        accountLabel.setBounds(100, 50, 50, 25);
        accountField.setBounds(170, 50, 120, 25);

        passwordLabel.setBounds(100, 100, 50, 25);
        passwordField.setBounds(170, 100, 120, 25);

        memberButton.setBounds(100, 160, 85, 25);
        loginButton.setBounds(230, 160, 60, 25);

        //把控件添加到主界面
        panel.add(accountLabel);
        panel.add(accountField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(memberButton);
        panel.add(loginButton);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //防止点击关闭页面 隐藏界面

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "确定要退出吗?", "退出界面", JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    //关闭界面 退出程序
                    System.exit(0);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String password = passwordField.getText();

                Connection connection = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                    String sql = "select * from admin where admin_account=? and admin_password=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        LoginView.this.dispose();
                        new AdminView();
                    } else {
                        JOptionPane.showMessageDialog(null, "账号或密码错误!");
                    }

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        });

        memberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String password = passwordField.getText();

                Connection connection = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql:///gym", "root", "123456");
                    String sql = "select member_account,member_password from member where member_account=? and member_password=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        LoginView.this.dispose();
                        new MemberView(account);
                    } else {
                        JOptionPane.showMessageDialog(null, "账号或密码错误!");
                    }

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //回车登录
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {//点击的是回车按钮
                    loginButton.doClick();//点击一次登录按钮
                }
            }
        });
        add(panel);
        setVisible(true); //设置可见，默认不可见
    }

    public static void main(String[] args) {
        new LoginView();
    }


}
