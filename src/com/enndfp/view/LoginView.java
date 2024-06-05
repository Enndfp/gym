package com.enndfp.view;

import com.enndfp.utils.JDBCUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 登录页面
 *
 * @author Enndfp
 */
public class LoginView extends JFrame {
    private JLabel accountLabel = new JLabel("账号");
    private JTextField accountField = new JTextField();

    private JLabel passwordLabel = new JLabel("密码");
    private JPasswordField passwordField = new JPasswordField();

    private JButton memberButton = new JButton("会员登录");
    private JButton loginButton = new JButton("管理员登录");

    public LoginView() {
        setTitle("星航健身房管理系统"); // 标题
        setSize(600, 400); // 大小
        setLocationRelativeTo(null); // 居中
        setResizable(false); // 大小不可改变
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // 防止点击关闭页面 隐藏界面

        // 创建一个JPanel来放置所有的组件
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/com/enndfp/image/background.jpg").getImage(); // 背景图片路径
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        //设置页面布局方式
        panel.setLayout(null); //绝对布局，添加控件之前手动设置大小

        // 设置字体大小和加粗
        accountLabel.setFont(new Font("宋体", Font.BOLD, 16));
        passwordLabel.setFont(new Font("宋体", Font.BOLD, 16));
        memberButton.setFont(new Font("宋体", Font.BOLD, 16));
        loginButton.setFont(new Font("宋体", Font.BOLD, 16));

        accountLabel.setBounds(160, 100, 150, 25);
        accountField.setBounds(270, 100, 150, 25);

        passwordLabel.setBounds(160, 160, 150, 25);
        passwordField.setBounds(270, 160, 150, 25);

        memberButton.setBounds(160, 220, 100, 30);
        loginButton.setBounds(300, 220, 120, 30);

        // 更改文本标签颜色
        accountLabel.setForeground(Color.ORANGE);
        passwordLabel.setForeground(Color.ORANGE);

        // 设置按钮的背景颜色
        Color customGreen = new Color(0, 128, 0);
        memberButton.setBackground(customGreen);
        loginButton.setBackground(customGreen);

        // 设置按钮的前景（文本）颜色
        memberButton.setForeground(Color.WHITE);
        loginButton.setForeground(Color.WHITE);

        //把控件添加到主界面
        panel.add(accountLabel);
        panel.add(accountField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(memberButton);
        panel.add(loginButton);

        // 自定义退出操作
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

        // 给登录按钮添加监听事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. 获取文本内容
                String account = accountField.getText();
                String password = passwordField.getText();

                // 2. 获得连接
                Connection connection = JDBCUtil.getConnection();
                // 3. 定义SQL
                String sql = "select * from admin where admin_account=? and admin_password=?";
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    // 4. 获得预编译对象
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, password);

                    // 5. 执行SQL
                    rs = ps.executeQuery();
                    // 6. 对结果集判断
                    if (rs.next()) {
                        LoginView.this.dispose();
                        new AdminView();
                    } else {
                        JOptionPane.showMessageDialog(null, "账号或密码错误!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    // 7. 关闭连接
                    JDBCUtil.getClose(connection, ps, rs);
                }
            }
        });

        // 给会员登录按钮添加监听事件
        memberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. 获取文本内容
                String account = accountField.getText();
                String password = passwordField.getText();

                // 2. 获得连接
                Connection connection = JDBCUtil.getConnection();
                // 3. 定义SQL
                String sql = "select member_account,member_password from member where member_account=? and member_password=?";
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    // 4. 获得预编译对象
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, password);

                    // 5. 执行SQL
                    rs = ps.executeQuery();
                    // 6. 对结果集判断
                    if (rs.next()) {
                        LoginView.this.dispose();
                        new MemberView(account);
                    } else {
                        JOptionPane.showMessageDialog(null, "账号或密码错误!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    // 7. 关闭连接
                    JDBCUtil.getClose(connection, ps, rs);
                }
            }
        });

        // 回车登录
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
