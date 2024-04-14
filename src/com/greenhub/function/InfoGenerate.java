package com.greenhub.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.greenhub.utils.utils;

public class InfoGenerate{
	
    private utils util = new utils();
    
    
	// cinema.json文件生成函数，写在这里。
	// 2024.3.31  新增了对于不同难度选择展示视频的功能，现在对该功能模块重写，预计会对窗体进行修改。
    public void cinema_info_generate(String save_path, String video_file_name, double opacity) {
        String file_name = video_file_name;
        try {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("file_name", file_name);
            jsonObject.put("opacity", opacity);


            //最后保存到文件。
            //格式化json字符串。
            String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            
            //生成json格式文件。
            try {
                File file = new File(save_path + "\\cinema.json");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                //将格式化后的字符串写入文件。
                Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                writer.write(content);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                util.generateLogs_Window(e);
            }
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        }
    }
    
    // 包含指定谱面启动开关的重载函数。
    public void cinema_info_generate(String save_path, String video_file_name, double opacity, int[] maps) {
        String file_name = video_file_name;
        // 选中对应的谱面文件，然后写进去，是可选的选项。
        int[] temp = new int[4];
        int tag = 0;
        for (int i = 0;i < 4;i ++) {
        	if (maps[i] == 1) {
        		temp[tag] = i + 1;
        		tag++;
        	}
        }
        int[] difficulties = new int[tag];
        // 转移参数，我目前只能想到这种方法。
        for (int j = 0;j < tag;j ++) {
        	difficulties[j] = temp[j];
        }
        System.out.println(difficulties.toString());
        try {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("file_name", file_name);
            jsonObject.put("opacity", opacity);
            jsonObject.put("difficulties", difficulties);


            //最后保存到文件。
            //格式化json字符串。
            String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            
            //生成json格式文件。
            try {
                File file = new File(save_path + "\\cinema.json");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                //将格式化后的字符串写入文件。
                Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                writer.write(content);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                util.generateLogs_Window(e);
            }
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        }
    }
    
    // 读取config.json文件，查看里面是否存在所谓的chart_path字段。
    public String get_chart_path() {
    	try {
    		// 读取 JSON 文件
            BufferedReader reader = new BufferedReader(new FileReader("config.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            // 解析 JSON
            JSONObject jsonObject = JSON.parseObject(jsonContent.toString());
            
            // 获取特定字段。
            String path = jsonObject.getString("chart_path");
            return path;
    	} catch (Exception e) {
    		// do nothing.
    		// 处理提出问题异常的模块比处理异常更简单。
    	} 
    	return "ERROR";
    }
    
    // Just for demo.
    public static void main(String[] args) {
    	InfoGenerate infoGenerate = new InfoGenerate();
    	String demo = infoGenerate.get_chart_path();
    	System.out.println("Temp chart folder: " + demo);
    }

}
