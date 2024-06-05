package com.enndfp.view;

import com.enndfp.view.course.CourseManagementView;
import com.enndfp.view.employee.EmployeeManagementView;
import com.enndfp.view.equipment.EquipmentManagementView;
import com.enndfp.view.member.MemberManagementView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 管理员页面
 *
 * @author Enndfp
 */
public class AdminView extends JFrame {
    // 选项卡容器
    private JTabbedPane option;

    public AdminView() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // 防止点击关闭页面 隐藏界面
        option = new JTabbedPane(JTabbedPane.LEFT); // 指定JTabbedPane靠左

        JPanel mainView = new MainView();
        option.add("主界面", mainView);

        JPanel memberManagementView = new MemberManagementView();
        option.add("会员管理", memberManagementView);

        JPanel employeeManagementView = new EmployeeManagementView();
        option.add("员工管理", employeeManagementView);

        JPanel equipmentManagementView = new EquipmentManagementView();
        option.add("器材管理", equipmentManagementView);

        JPanel courseManagementView = new CourseManagementView();
        option.add("课程管理", courseManagementView);

        // 修改选项卡的属性
        option.setFont(new Font("宋体", Font.BOLD, 20));
        option.setBackground(Color.CYAN);

        // 自定义选项卡外观和行为
        option.setUI(new BasicTabbedPaneUI() {
            // 增加选项卡高度
            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 20;
            }

            // 增加选项卡宽度
            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 30;
            }

            // 根据是否被选中来设置不同的背景颜色
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Color defaultColor = new Color(0, 255, 255);
                Color selectedColor = new Color(255, 165, 0);
                g.setColor(!isSelected ? defaultColor : selectedColor);
                g.fillRect(x, y, w, h);
            }

            // 绘制选项卡的边框
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Color defaultColor = new Color(0, 255, 255);
                Color selectedColor = new Color(255, 165, 0);
                g.setColor(!isSelected ? defaultColor : selectedColor);
                g.fillRect(x, y, w, h);
            }

            // 这个方法定义如果没有的话，选项卡在选中时，内测会有虚线
            protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
            }

            // 自定义布局
            protected LayoutManager createLayoutManager() {
                return new TabbedPaneLayout();
            }

            class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
                // 要想实现：1.选中选项卡时，选项卡突出显示 2.选项卡之间有间距。那么必须重写以下方法！！
                protected void calculateTabRects(int tabPlacement, int tabCount) {
                    super.calculateTabRects(tabPlacement, tabCount);
                    // 设置间距
                    setRec(81);
                }

                public void setRec(int rec) {
                    for (int i = 0; i < rects.length; i++) {
                        rects[i].y = rects[i].y + rec * i;
                    }
                }
            }
        });

        // 选项卡切换
        option.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = option.getSelectedIndex();
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        ((MemberManagementView) memberManagementView).updateContent();
                        break;
                    case 2:
                        ((EmployeeManagementView) employeeManagementView).updateContent();
                        break;
                    case 3:
                        ((EquipmentManagementView) equipmentManagementView).updateContent();
                        break;
                    case 4:
                        ((CourseManagementView) courseManagementView).updateContent();
                        break;
                }
            }
        });

        // 自定义退出操作
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new LoginView();
            }
        });

        add(option);
        setTitle("管理员页面");
        setSize(1020, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
