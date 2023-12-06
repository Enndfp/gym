package com.enndfp.view.course;

import com.enndfp.pojo.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * @author Enndfp
 * @date 2023/3/18
 */
public class SelectMemberOrderView extends JDialog {
    // 滚动面板
    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public JTable table = new JTable();
    private static final Font DEFAULT_FONT = new Font("黑体", Font.BOLD, 15);

    private JLabel classIdLabel = new JLabel("课程编号");
    private JTextField classIdField = new JTextField();

    private JLabel classNameLabel = new JLabel("课程名称");
    private JTextField classNameField = new JTextField();

    private JLabel classBeginLabel = new JLabel("开课时间");
    private JTextField classBeginField = new JTextField();

    public SelectMemberOrderView(Course course,Vector<Vector<String>> dataVector){
        setTitle("查看报名信息");
        setSize(400, 480);
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

        scrollPane.setBounds(10, 180, 360, 150);
        scrollPane.getViewport().add(table); // 把表格加入到滚动面板中

        classIdLabel.setBounds(80, 40, 60, 20);
        panel.add(classIdLabel);
        classIdField.setBounds(150, 40, 150, 20);
        classIdField.setText(String.valueOf(course.getClassId()));
        classIdField.setFont(DEFAULT_FONT);
        classIdField.setMargin(new Insets(0, 8, 0, 0));
        classIdField.setEditable(false);
        panel.add(classIdField);

        classNameLabel.setBounds(80, 80, 60, 20);
        panel.add(classNameLabel);
        classNameField.setBounds(150, 80, 150, 20);
        classNameField.setText(course.getClassName());
        classNameField.setFont(DEFAULT_FONT);
        classNameField.setMargin(new Insets(0, 8, 0, 0));
        classNameField.setEditable(false);
        panel.add(classNameField);

        classBeginLabel.setBounds(80, 120, 60, 20);
        panel.add(classBeginLabel);
        classBeginField.setBounds(150, 120, 150, 20);
        classBeginField.setText(course.getClassBegin());
        classBeginField.setFont(new Font("黑体",Font.BOLD,12));
        classBeginField.setMargin(new Insets(0, 8, 0, 0));
        classBeginField.setEditable(false);
        panel.add(classBeginField);

        Vector<String> thVector = new Vector<>(); // 表头集合
        thVector.add("会员卡号");
        thVector.add("会员名称");

        // 构建表模型
        DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
        table.setModel(defaultTableModel); // 告知表格  要显示的数据

        // 表格的数据居中
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        // 给表格指定渲染器
        table.setDefaultRenderer(Object.class, cellRenderer);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setEnabled(false);
        // 设置表格字体样式
        Font font = new Font("黑体", Font.BOLD, 16);
        table.setFont(font);
        table.getTableHeader().setFont(font);

        add(scrollPane);
        add(panel);
        setVisible(true);
    }
}
