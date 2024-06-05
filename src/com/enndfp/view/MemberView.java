package com.enndfp.view;

import com.enndfp.view.course.ChooseCourseView;
import com.enndfp.view.course.MemberCourseView;
import com.enndfp.view.member.MemberInfoView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

/**
 * 会员页面
 *
 * @author Enndfp
 */
public class MemberView extends JFrame {
    // 选项卡容器
    private JTabbedPane option;

    public MemberView(String account) {
        option = new JTabbedPane(JTabbedPane.LEFT); // 指定JTabbedPane靠左

        JPanel memberInfoView = new MemberInfoView(account);
        option.add("个人信息", memberInfoView);

        JPanel chooseCourseView = new ChooseCourseView(account);
        option.add("报名选课", chooseCourseView);

        JPanel memberCourseView = new MemberCourseView(account);
        option.add("我的课程", memberCourseView);

        // 修改选项卡的属性
        option.setFont(new Font("宋体", Font.BOLD, 20));
        option.setBackground(Color.CYAN);

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
                        ((MemberInfoView) memberInfoView).queryMemberInfo();
                        break;
                    case 1:
                        ((ChooseCourseView) chooseCourseView).updateContent();
                        break;
                    case 2:
                        ((MemberCourseView) memberCourseView).selectCourse(account);
                        break;
                }
            }
        });

        add(option);
        setTitle("星航健身房会员页面");
        setSize(1020, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
