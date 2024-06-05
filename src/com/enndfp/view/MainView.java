package com.enndfp.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 主界面视图
 *
 * @author Enndfp
 */
public class MainView extends JPanel {
    private JLabel label;

    public MainView() {
        label = new JLabel("欢迎您，管理员");
        label.setFont(new Font("宋体", Font.BOLD, 80)); // 设置字体样式
        label.setForeground(Color.ORANGE); // 设置字体颜色
        label.setHorizontalAlignment(JLabel.CENTER); // 水平居中
        label.setVerticalAlignment(JLabel.CENTER); // 垂直居中
        // 在标签的下方添加空白区域
        int top = 0;
        int left = 0;
        int bottom = 150;
        int right = 0;
        label.setBorder(new EmptyBorder(top, left, bottom, right));
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageIcon("src/com/enndfp/image/main.png").getImage(); // 背景图片路径
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MainView());
        frame.setVisible(true);
    }
}
