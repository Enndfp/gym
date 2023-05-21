package com.enndfp.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enndfp
 * @date 2023/3/12
 */
public class MainView extends JPanel {
    private JLabel label;

    public MainView() {
        ImageIcon image = new ImageIcon("D:\\图片\\gym.jpg"); // 背景图片路径
        label = new JLabel("欢迎您，管理员");
        label.setFont(new Font("宋体", Font.BOLD, 50)); // 设置字体样式
        label.setForeground(Color.YELLOW); // 设置字体颜色
        label.setHorizontalAlignment(JLabel.CENTER); // 水平居中
        label.setVerticalAlignment(JLabel.CENTER); // 垂直居中
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageIcon("D:\\图片\\gym.jpg").getImage(); // 背景图片路径
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
