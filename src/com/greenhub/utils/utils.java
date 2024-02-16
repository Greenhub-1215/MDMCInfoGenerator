package com.greenhub.utils;

import javax.swing.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class utils {
	
	// 获取出错产生的日志信息。
	public String getLog(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString()).append("\n");
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString()).append("\n");
		}
		
		return sb.toString();
	}
	
	// 将日志输出到文件。
	public void generateLogs(String logs, String path) {
		// 检测Logs文件夹是否存在，如果不存在，就创建一个。
		File file = new File("");
		try {
			String filePath = file.getCanonicalPath() + "\\Logs";
			File file1 = new File(filePath);
			if (!file1.exists() && !file1.isDirectory()) {
				file1.mkdir();
			} else {
				// 日志输出先不做了。
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "Latest.log"))) {
					writer.write(logs);
					writer.newLine();
					writer.flush();
					System.out.println("Log written to Latest.log");
				} catch (IOException e) {
					String output_logs = "程序运行时出现错误，建议把错误日志反馈GreenHub，并给他一巴掌叫他起床修bug！\n\n" + this.getLog(e);
					logs = logs + output_logs;
					JOptionPane.showMessageDialog(null, logs, "错误！", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (IOException e) {
			String output_logs = "程序运行时出现错误，建议把错误日志反馈GreenHub，并给他一巴掌叫他起床修bug！\n\n" + this.getLog(e);
			logs = logs + output_logs;
			JOptionPane.showMessageDialog(null, logs, "错误！", JOptionPane.ERROR_MESSAGE);
		}
	    
	}
	// 保存历史日志
}
