package com.enndfp.view.course;

import com.enndfp.utils.JDBCUtil;
import com.enndfp.utils.JTimeChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 添加课程信息界面
 *
 * @author Enndfp
 */
public class AddCourseView extends JDialog {
    private static final String DEFAULT_NAME = "课程名称";
    private static final String DEFAULT_TIME = "XXXX年XX月XX日 00:00";
    private static final String DEFAULT_DURATION = "XX分钟";
    private static final String DEFAULT_COACH = "教练";
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);
    private static final Color DEFAULT_COLOR = Color.lightGray;

    private Date time;

    private JLabel courseNameLabel = new JLabel("名称");
    private JTextField courseNameField = new JTextField();

    private JLabel courseTimeLabel = new JLabel("时间");
    private JTextField courseTimeField = new JTextField();

    private JLabel courseDurationLabel = new JLabel("时长");
    private JTextField courseDurationField = new JTextField();

    private JLabel courseCoachLabel = new JLabel("教练");
    private JTextField courseCoachField = new JTextField();

    private JButton resetButton = new JButton("重置");
    private JButton addButton = new JButton("添加");

    public AddCourseView(DefaultTableModel defaultTableModel) {
        setTitle("添加课程信息");
        setSize(400, 310);
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

        courseNameLabel.setBounds(80, 40, 60, 20);
        panel.add(courseNameLabel);
        courseNameField.setBounds(150, 40, 150, 20);
        courseNameField.setText(DEFAULT_NAME);
        courseNameField.setForeground(DEFAULT_COLOR);
        courseNameField.setMargin(new Insets(0, 8, 0, 0));
        courseNameField.setFont(DEFAULT_FONT);
        panel.add(courseNameField);

        courseTimeLabel.setBounds(80, 80, 60, 20);
        panel.add(courseTimeLabel);
        courseTimeField.setBounds(150, 80, 150, 20);
        courseTimeField.setText(DEFAULT_TIME);
        courseTimeField.setForeground(DEFAULT_COLOR);
        courseTimeField.setMargin(new Insets(0, 8, 0, 0));
        courseTimeField.setFont(new Font("黑体", Font.BOLD, 12));
        panel.add(courseTimeField);

        courseDurationLabel.setBounds(80, 120, 60, 20);
        panel.add(courseDurationLabel);
        courseDurationField.setBounds(150, 120, 150, 20);
        courseDurationField.setText(DEFAULT_DURATION);
        courseDurationField.setForeground(DEFAULT_COLOR);
        courseDurationField.setMargin(new Insets(0, 8, 0, 0));
        courseDurationField.setFont(DEFAULT_FONT);
        panel.add(courseDurationField);

        courseCoachLabel.setBounds(80, 160, 60, 20);
        panel.add(courseCoachLabel);
        courseCoachField.setBounds(150, 160, 150, 20);
        courseCoachField.setText(DEFAULT_COACH);
        courseCoachField.setForeground(DEFAULT_COLOR);
        courseCoachField.setMargin(new Insets(0, 8, 0, 0));
        courseCoachField.setFont(DEFAULT_FONT);
        panel.add(courseCoachField);

        resetButton.setBounds(80, 210, 60, 30);
        resetButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(resetButton);
        addButton.setBounds(240, 210, 60, 30);
        addButton.setFont(new Font("黑体", Font.BOLD, 13));
        panel.add(addButton);

        // 给resetButton按钮添加一个ActionListener，使得当用户点击该按钮时会调用reset()方法
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                resetButton.requestFocusInWindow(); // 将光标移到 resetButton 按钮上
            }
        });

        // 为所有文本框添加FocusListener，使得在获取焦点时清空提示文字并改变字体和颜色，失去焦点时重新设置为默认提示文字
        courseNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (courseNameField.getText().equals(DEFAULT_NAME)) {
                    courseNameField.setText("");
                    courseNameField.setFont(DEFAULT_FONT);
                    courseNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (courseNameField.getText().isEmpty()) {
                    courseNameField.setText(DEFAULT_NAME);
                    courseNameField.setForeground(DEFAULT_COLOR);
                    courseNameField.setFont(DEFAULT_FONT);
                } else {
                    courseNameField.setForeground(Color.BLACK);
                }
                courseNameField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        courseTimeField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTimeChooser chooser = new JTimeChooser((JDialog) null);
                Calendar calendar = chooser.showTimeDialog();
                time = calendar.getTime();
                // 将Calendar对象格式化为yyyy年MM月dd日 HH:mm的字符串
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                String formattedString = sdf.format(time);
                courseTimeField.setFont(new Font("黑体", Font.BOLD, 12));
                courseTimeField.setMargin(new Insets(0, 8, 0, 0));
                courseTimeField.setForeground(Color.BLACK);
                courseTimeField.setText(formattedString);
            }
        });

        courseDurationField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (courseDurationField.getText().equals(DEFAULT_DURATION)) {
                    courseDurationField.setText("");
                    courseDurationField.setFont(DEFAULT_FONT);
                    courseDurationField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (courseDurationField.getText().isEmpty()) {
                    courseDurationField.setText(DEFAULT_DURATION);
                    courseDurationField.setForeground(DEFAULT_COLOR);
                    courseDurationField.setFont(DEFAULT_FONT);
                } else {
                    courseDurationField.setForeground(Color.BLACK);
                }
                courseDurationField.setMargin(new Insets(0, 8, 0, 0));
            }
        });

        courseCoachField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (courseCoachField.getText().equals(DEFAULT_COACH)) {
                    courseCoachField.setText("");
                    courseCoachField.setFont(DEFAULT_FONT);
                    courseCoachField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (courseCoachField.getText().isEmpty()) {
                    courseCoachField.setText(DEFAULT_COACH);
                    courseCoachField.setForeground(DEFAULT_COLOR);
                    courseCoachField.setFont(DEFAULT_FONT);
                } else {
                    courseCoachField.setForeground(Color.BLACK);
                }
                courseCoachField.setMargin(new Insets(0, 8, 0, 0));
            }
        });


        // 给添加按钮绑定监听事件，添加员工信息
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseNameField.getText().equals(DEFAULT_NAME) || courseTimeField.getText().equals(DEFAULT_TIME) ||
                        courseDurationField.getText().equals(DEFAULT_DURATION) || courseCoachField.getText().equals(DEFAULT_COACH)) {
                    JOptionPane.showMessageDialog(null, "请填写正确信息!");
                } else {
                    String courseName = courseNameField.getText();
                    String courseTime = courseTimeField.getText();
                    String courseDuration = courseDurationField.getText();
                    String courseCoach = courseCoachField.getText();

                    Connection connection = null;
                    PreparedStatement ps = null;
                    try {
                        connection = JDBCUtil.getConnection();
                        String sql = "insert into class_table(class_name,class_begin,class_time,coach) values(?,?,?,?)";

                        ps = connection.prepareStatement(sql);
                        ps.setString(1, courseName);
                        ps.setString(2, courseTime);
                        ps.setString(3, courseDuration + "分钟");
                        ps.setString(4, courseCoach);

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
                                String sql2 = "SELECT * FROM class_table";
                                ps2 = connection2.prepareStatement(sql2);
                                rs = ps2.executeQuery();
                                while (rs.next()) {
                                    // 将查询结果添加到表模型中
                                    defaultTableModel.addRow(new Object[]{
                                            rs.getString(1),
                                            rs.getString(2),
                                            rs.getString(3),
                                            rs.getString(4),
                                            rs.getString(5)
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
        courseNameField.setText(DEFAULT_NAME);
        courseNameField.setForeground(DEFAULT_COLOR);
        courseNameField.setFont(DEFAULT_FONT);

        courseTimeField.setText(DEFAULT_TIME);
        courseTimeField.setForeground(DEFAULT_COLOR);
        courseTimeField.setFont(new Font("黑体", Font.BOLD, 12));

        courseDurationField.setText(DEFAULT_DURATION);
        courseDurationField.setForeground(DEFAULT_COLOR);
        courseDurationField.setFont(DEFAULT_FONT);

        courseCoachField.setText(DEFAULT_COACH);
        courseCoachField.setForeground(DEFAULT_COLOR);
        courseCoachField.setFont(DEFAULT_FONT);
    }
}
