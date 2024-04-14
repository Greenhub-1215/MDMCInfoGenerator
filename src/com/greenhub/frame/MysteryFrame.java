package com.greenhub.frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.greenhub.function.*;

public class MysteryFrame extends JFrame implements ActionListener {
	
	// 新建一个mystery变量，抽奖和对数据库的操作都会写到这里。
	private Mystery mystery = new Mystery();
	public MysteryFrame(JFrame frame) {
		/*
		* 第一个参数：父窗体对象
		* 第二个参数：对话框标题
		* 第三个参数：是否阻塞父窗体
		*/
		this.setTitle("这是什么？点一下 - Coded by GreenHub");
		this.setSize(1100,500);
		this.setLocation(300,110);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	// 神秘按钮的抽卡功能，计划与窗体代码分离，抽出来单独做一个类去完成。
}
