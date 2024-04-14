package com.greenhub.frame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.greenhub.function.*;
import com.greenhub.utils.utils;

public class CinemaInfoGenerateFrame extends JDialog implements ActionListener {
	
	// Add Components.
	private JLabel opactiy_label = new JLabel("透明度(请输入0~1之间的数值)：");
	private JTextField opacity_input = new JTextField("0.5", 12);
	
	private JCheckBox activate_selected_cinema = new JCheckBox("指定难度谱面显示开关（打开后可指定特定难度加载视频）");
	private JCheckBox[] map_selection = new JCheckBox[4];
	private JButton generate = new JButton("确定");
	private JButton exit = new JButton("取消");
	// Add Panels.
	private JPanel main_Panel = new JPanel();
	private JPanel opacity_Panel = new JPanel();
	private JPanel activate_selection_Panel = new JPanel();
	private JPanel map_selection_Panel = new JPanel();
	private JPanel operation_Panel = new JPanel();
	
	// 定义全局变量，实现InfoGenerate中的方法。
	private InfoGenerate infoGenerate = new InfoGenerate();
	private utils util = new utils();
	
	
	// 传入frame参数，用于阻塞父窗体。
	public CinemaInfoGenerateFrame(JFrame frame, int[] maps, String save_path, String video_file_name) {
		
		super(frame, "cinema.json文件生成 - Coded by GreenHub", true);
		this.setSize(400,180);
		this.setLocation(500,300);
		this.setLayout(new BorderLayout());
		
		// 放置面板
		// 放置主面板
		this.add(main_Panel, BorderLayout.CENTER);
		main_Panel.setLayout(new GridLayout(3,1));
		main_Panel.add(opacity_Panel);
		opacity_Panel.setLayout(new FlowLayout());
		opacity_Panel.add(opactiy_label);
		opacity_Panel.add(opacity_input);
		main_Panel.add(activate_selection_Panel);
		activate_selection_Panel.add(activate_selected_cinema);
		main_Panel.add(map_selection_Panel);
		// 绑定CheckBox按钮。
		for (int i = 0;i < 4;i ++) {
			map_selection[i] = new JCheckBox("map" + (i + 1) + ".bms");
			map_selection_Panel.add(map_selection[i]);
		}
		
		// 放置操作按钮的面板
		this.add(operation_Panel, BorderLayout.SOUTH);
		operation_Panel.setLayout(new FlowLayout());
		operation_Panel.add(generate);
		operation_Panel.add(exit);
		generate.addActionListener(this);
		exit.addActionListener(this);
		
		// 默认讲所有的CheckBox全部禁用。
		for (int i = 0;i < 4;i ++) {
			map_selection[i].setEnabled(false);
		}
		// 根据传入的数组来决定哪些显示哪些不显示，添加CheckBox响应事件。
		activate_selected_cinema.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				// TODO Auto-generated method stub
				if (activate_selected_cinema.isSelected()) {
					for (int i = 0;i < 4;i ++) {
						// 根据压缩包中所包含的谱面难度来决定启用哪一个开关。
						if (maps[i] == 1) {
							map_selection[i].setEnabled(true);
						}
					}
				}
				if (!activate_selected_cinema.isSelected()) {
					for (int i = 0;i < 4;i ++) {
						map_selection[i].setEnabled(false);
					}
				}
				
			}
			
		});
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 对输入的结果进行校验，如果无法转换为浮点型，或者转换失败，抛出错误提示信息。
				try {
					double opacity = Double.parseDouble(opacity_input.getText());
					if (opacity < 0.0 || opacity > 1.0) {
						JOptionPane.showMessageDialog(null, "输入的透明度参数超出范围！请重新输入！");
					}
					else if (!activate_selected_cinema.isSelected()) {
						infoGenerate.cinema_info_generate(save_path, video_file_name, opacity);
						JOptionPane.showMessageDialog(null, "cinema.json文件生成完成！");
					} else {
						// 如果开关打开了应该怎么做？
						// 获取按钮选中状态。
						int[] map_static = {0,0,0,0};
						for (int i = 0;i < 4;i ++) {
							if (map_selection[i].isSelected()) {
								map_static[i] ++;
							}
						}
						
						// 传入参数，验证测试。
						infoGenerate.cinema_info_generate(save_path, video_file_name, opacity, map_static);
						JOptionPane.showMessageDialog(null, "cinema.json文件生成完成！");
					}
				} catch (NumberFormatException ex) {
					util.generateLogs_Window(ex);
				}
			}
			
		});
		// 窗体显示。
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit) {
			this.dispose();
		}
		
	}
	
}
