package com.greenhub.frame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.greenhub.entity.ChartInfo;
import com.greenhub.frame.AddTagFrame;
import com.greenhub.frame.CinemaInfoGenerateFrame;
import com.greenhub.function.InfoGenerate;
import com.greenhub.utils.*;
import net.sf.json.JSONArray;

public class Mainframe extends JFrame implements ActionListener {
    //title panel components.
    private JPanel title_panel = new JPanel();
    private JPanel title_panel1 = new JPanel();
    private JPanel title_panel2 = new JPanel();
    private Font titleFont = new Font("微软雅黑",Font.BOLD,24);
    private Font mainFont = new Font("微软雅黑", Font.PLAIN,17);
    private JLabel titleLabel = new JLabel("Muse Dash Modding Community(MDMC)谱面文件info.json生成工具  Code by GreenHub");
    private JLabel input_choice = new JLabel("请选择模式：");
    private ButtonGroup choice = new ButtonGroup();
    //两个单选按钮，分别是从文件夹导入和手动填充。
    //手动填充功能已经禁用，目前只允许通过bms文件修改歌曲信息。
    private JRadioButton folder_input = new JRadioButton("从文件夹导入");
    private JRadioButton text_input = new JRadioButton("手动输入");
    //存放文件路径的文本框，打开浏览文件夹位置的按钮以及填充信息。
    private JTextField file_directory = new JTextField("",26);
    private JButton browser = new JButton("浏览...");
    private JButton input = new JButton("开始导入");
    //打开一个选择文件夹的窗体。
    private JFileChooser fileChooser;

    //主窗体，分为4个部分：萌新、高手、大触、里谱，分别是0，1，2，3
    private JPanel main_panel = new JPanel();
    private JPanel item_main_panel[] = new JPanel[4];
    //分为四个板块，获取map的信息。

    //这里是标签。
    private JLabel tiny_title_label[] = {new JLabel("map1.bms"), new JLabel("map2.bms"), new JLabel("map3.bms"), new JLabel("map4.bms")};
    private JLabel song_title_label[] = new JLabel[4];
    private JLabel author_label[] = new JLabel[4];
    private JLabel chart_design[] = new JLabel[4];
    private JLabel scene[] = new JLabel[4];
    private JLabel bpm[] = new JLabel[4];
    private JLabel difficulty[] = new JLabel[4];
    private JLabel level[] = new JLabel[4];
    //这里是面板。
    private JPanel tiny_title_panel[] = new JPanel[4];
    private JPanel song_title_panel[] = new JPanel[4];
    private JPanel author_panel[] = new JPanel[4];
    private JPanel chart_design_panel[] = new JPanel[4];
    private JPanel scene_panel[] = new JPanel[4];
    private JPanel bpm_panel[] = new JPanel[4];
    private JPanel difficulty_panel[] = new JPanel[4];
    private JPanel level_panel[] = new JPanel[4];
    //这里是文本框。
    private JTextField song_title_text[] = new JTextField[4];
    private JTextField author_text[] = new JTextField[4];
    private JTextField chart_design_text[] = new JTextField[4];
    private JTextField scene_text[] = new JTextField[4];
    private JTextField bpm_text[] = new JTextField[4];
    private JTextField difficulty_text[] = new JTextField[4];
    private JTextField level_text[] = new JTextField[4];

    //底部窗体，填写解锁里谱面的info，以及控制面板。
    private JPanel bottom_panel = new JPanel();

    private JPanel hidden_info_panel = new JPanel();
    private JPanel special_effects_panel = new JPanel();
    private JPanel control_panel = new JPanel();

    //解锁里谱面的信息info组件。
    private JLabel tips = new JLabel("map4提示信息     ");
    private JTextField tips_text = new JTextField("",30);
    private JLabel unlock_action_label = new JLabel("里谱解锁方式");
    private JComboBox<String> unlock_action = new JComboBox<>();
    private JLabel hidden_unlock_level_label = new JLabel("里谱解锁等级");
    private JComboBox<String> hidden_unlock_level = new JComboBox<>();
    private JButton hidden_level_help = new JButton("帮助");
    
    // 特效按钮
    private ButtonGroup sceneGroup = new ButtonGroup();
    /**
     * 添加不同的特效按钮，默认为无。
     * */
    private JLabel scene_title = new JLabel("特效按钮      ");
    private JRadioButton scene_None = new JRadioButton("无");
    private JRadioButton scene_Cytus = new JRadioButton("启用Cytus彩蛋");
    private JRadioButton scene_Wacca = new JRadioButton("启用Wacca彩蛋");
    private JRadioButton scene_Touhou = new JRadioButton("启用东方彩蛋");
    private JRadioButton scene_BadApple = new JRadioButton("启用Bad Apple特效");
    private JRadioButton scene_Christmas = new JRadioButton("启用圣诞场景");
    private JRadioButton scene_RinLen = new JRadioButton("启用镜音双子场景");
    
    
    //执行操作按钮。
    private JButton search_tag_generate = new JButton("编辑SearchTag");
    private JButton generate = new JButton("生成info.json文件");
    private JButton generate_cinema = new JButton("生成cinema.json文件");
    private JButton about = new JButton("关于...");
    private JButton random = new JButton("神秘按钮");
    private JButton exit = new JButton("关闭程序");

    //secret counter.
    private int count = 1;
    
    
    //全局变量。
    private String video_file_name = "";
    private String default_Charter = "";
    // 记录日志变量，如果程序执行没有出错，那么就不生成log文件，否则生成文件，并将其保存到指定的文件路径。
    private String log_temp = "";
    private utils util = new utils();
    private InfoGenerate info_function = new InfoGenerate();
    
    public Mainframe() {
        this.setTitle("info.json生成器v0.4.11 - Code by GreenHub");
        this.setSize(1200,580);
        this.setLocation(350,120);
        this.setLayout(new BorderLayout());
        this.add(title_panel, BorderLayout.NORTH);

        //BorderLayout.NORTH
        title_panel.setLayout(new GridLayout(2,1));
        title_panel.add(title_panel1);
        title_panel.add(title_panel2);
        title_panel1.setLayout(new FlowLayout());
        titleLabel.setFont(titleFont);
        title_panel1.add(titleLabel);
        choice.add(folder_input);
        choice.add(text_input);
        text_input.setEnabled(true);
        title_panel2.setLayout(new FlowLayout());
        input_choice.setFont(mainFont);
        folder_input.setFont(mainFont);
        text_input.setFont(mainFont);
        file_directory.setFont(mainFont);
        browser.setFont(mainFont);
        input.setFont(mainFont);
        title_panel2.add(input_choice);
        title_panel2.add(folder_input);
        title_panel2.add(text_input);
        folder_input.addActionListener(this);
        text_input.addActionListener(this);
        title_panel2.add(file_directory);
        title_panel2.add(browser);
        title_panel2.add(input);
        file_directory.setEditable(false);
        browser.setEnabled(true);
        input.setEnabled(false);
        folder_input.setSelected(true);
        browser.addActionListener(this);
        input.addActionListener(this);

        //BorderLayout.CENTER
        this.add(main_panel, BorderLayout.CENTER);
        main_panel.setLayout(new GridLayout(1,4));
        for (int i = 0;i < 4;i ++) {
            //创建对象。
            tiny_title_panel[i] = new JPanel();
            song_title_panel[i] = new JPanel();
            author_panel[i] = new JPanel();
            chart_design_panel[i] = new JPanel();
            scene_panel[i] = new JPanel();
            bpm_panel[i] = new JPanel();
            difficulty_panel[i] = new JPanel();
            level_panel[i] = new JPanel();
            item_main_panel[i] = new JPanel();
            //设置布局。
            tiny_title_panel[i].setLayout(new FlowLayout());
            song_title_panel[i].setLayout(new FlowLayout());
            author_panel[i].setLayout(new FlowLayout());
            chart_design_panel[i].setLayout(new FlowLayout());
            scene_panel[i].setLayout(new FlowLayout());
            bpm_panel[i].setLayout(new FlowLayout());
            difficulty_panel[i].setLayout(new FlowLayout());
            level_panel[i].setLayout(new FlowLayout());
            item_main_panel[i].setLayout(new GridLayout(8,1));
            //设置标签内容。
            song_title_label[i] = new JLabel("曲目名         ");
            author_label[i] = new JLabel("艺术家/歌手 ");
            chart_design[i] = new JLabel("谱面设计      ");
            scene[i] = new JLabel("场景            ");
            bpm[i] = new JLabel("bpm           ");
            difficulty[i] = new JLabel("难度            ");
            level[i] = new JLabel("谱面等级      ");
            //给文本框初始化。
            song_title_text[i] = new JTextField("",10);
            author_text[i] = new JTextField("",10);
            chart_design_text[i] = new JTextField("",10);
            scene_text[i] = new JTextField("",10);
            bpm_text[i] = new JTextField("",10);
            difficulty_text[i] = new JTextField("",10);
            level_text[i] = new JTextField("",10);
            //给控件设置字体。
            tiny_title_label[i].setFont(new Font("微软雅黑", Font.BOLD,17));
            song_title_label[i].setFont(mainFont);
            song_title_text[i].setFont(mainFont);
            author_label[i].setFont(mainFont);
            author_text[i].setFont(mainFont);
            chart_design[i].setFont(mainFont);
            chart_design_text[i].setFont(mainFont);
            scene[i].setFont(mainFont);
            scene_text[i].setFont(mainFont);
            bpm[i].setFont(mainFont);
            bpm_text[i].setFont(mainFont);
            difficulty[i].setFont(mainFont);
            difficulty_text[i].setFont(mainFont);
            level[i].setFont(mainFont);
            level_text[i].setFont(mainFont);
            //添加内容
            tiny_title_panel[i].add(tiny_title_label[i]);
            song_title_panel[i].add(song_title_label[i]);
            song_title_panel[i].add(song_title_text[i]);
            author_panel[i].add(author_label[i]);
            author_panel[i].add(author_text[i]);
            chart_design_panel[i].add(chart_design[i]);
            chart_design_panel[i].add(chart_design_text[i]);
            scene_panel[i].add(scene[i]);
            scene_panel[i].add(scene_text[i]);
            bpm_panel[i].add(bpm[i]);
            bpm_panel[i].add(bpm_text[i]);
            difficulty_panel[i].add(difficulty[i]);
            difficulty_panel[i].add(difficulty_text[i]);
            level_panel[i].add(level[i]);
            level_panel[i].add(level_text[i]);
            //将组件添加到主面板中。
            item_main_panel[i].add(tiny_title_panel[i]);
            item_main_panel[i].add(song_title_panel[i]);
            item_main_panel[i].add(author_panel[i]);
            item_main_panel[i].add(chart_design_panel[i]);
            item_main_panel[i].add(scene_panel[i]);
            item_main_panel[i].add(bpm_panel[i]);
            item_main_panel[i].add(difficulty_panel[i]);
            item_main_panel[i].add(level_panel[i]);

            // 在默认状态下，如果从文件夹导入的话，默认的曲目信息文本输入框处于禁用状态。
            // 禁止了从程序输入的功能，因为不可控的因素太多了。
            song_title_text[i].setEditable(false);
            author_text[i].setEditable(false);
            chart_design_text[i].setEditable(false);
            scene_text[i].setEditable(false);
            bpm_text[i].setEditable(false);
            difficulty_text[i].setEditable(false);
            level_text[i].setEditable(false);

            main_panel.add(item_main_panel[i]);
        }

        //BorderLayout.SOUTH.
        this.add(bottom_panel, BorderLayout.SOUTH);
        bottom_panel.setLayout(new GridLayout(3,1));
        bottom_panel.add(hidden_info_panel);
        bottom_panel.add(special_effects_panel);
        bottom_panel.add(control_panel);
        hidden_info_panel.setLayout(new FlowLayout());
        control_panel.setLayout(new FlowLayout());
        // 设置字体。
        tips.setFont(mainFont);
        tips_text.setFont(mainFont);
        unlock_action.setFont(mainFont);
        unlock_action_label.setFont(mainFont);
        hidden_unlock_level_label.setFont(mainFont);
        hidden_unlock_level.setFont(mainFont);
        hidden_level_help.setFont(mainFont);
        scene_title.setFont(mainFont);
        scene_None.setFont(mainFont);
        scene_Cytus.setFont(mainFont);
        scene_Wacca.setFont(mainFont);
        scene_Touhou.setFont(mainFont);
        scene_BadApple.setFont(mainFont);
        scene_Christmas.setFont(mainFont);
        scene_RinLen.setFont(mainFont);
        tips_text.setEditable(false);
        unlock_action.setEnabled(false);
        unlock_action.addItem("- 请选择 -");
        hidden_unlock_level.setEnabled(false);
        hidden_unlock_level.addItem("- 请选择 -");
        generate.setFont(mainFont);
        generate_cinema.setFont(mainFont);
        search_tag_generate.setFont(mainFont);
        about.setFont(mainFont);
        random.setFont(mainFont);
        exit.setFont(mainFont);


        // sceneEgg特效选择按钮。
        special_effects_panel.setLayout(new FlowLayout());
        sceneGroup.add(scene_None);
        sceneGroup.add(scene_Cytus);
        sceneGroup.add(scene_Wacca);
        sceneGroup.add(scene_Touhou);
        sceneGroup.add(scene_BadApple);
        sceneGroup.add(scene_Christmas);
        sceneGroup.add(scene_RinLen);
        special_effects_panel.add(scene_title);
        special_effects_panel.add(scene_None);
        special_effects_panel.add(scene_Cytus);
        special_effects_panel.add(scene_Wacca);
        special_effects_panel.add(scene_Touhou);
        special_effects_panel.add(scene_BadApple);
        special_effects_panel.add(scene_Christmas);
        special_effects_panel.add(scene_RinLen);
        scene_None.setSelected(true);
        
        // 启动后，默认禁用按钮组的全部按钮。
        scene_None.setEnabled(false);
        scene_Cytus.setEnabled(false);
        scene_Wacca.setEnabled(false);
        scene_Touhou.setEnabled(false);
        scene_BadApple.setEnabled(false);
        scene_Christmas.setEnabled(false);
        scene_RinLen.setEnabled(false);

        
        // 里谱面板，在这里配置里谱的相应的信息。
        hidden_info_panel.add(tips);
        hidden_info_panel.add(tips_text);
        hidden_info_panel.add(unlock_action_label);
        hidden_info_panel.add(unlock_action);
        hidden_info_panel.add(hidden_unlock_level_label);
        hidden_info_panel.add(hidden_unlock_level);
        hidden_info_panel.add(hidden_level_help);
        hidden_level_help.setToolTipText("不知道怎么填？点我！！");
        control_panel.add(generate);
        control_panel.add(generate_cinema);
        control_panel.add(search_tag_generate);
        control_panel.add(about);
        control_panel.add(random);
        control_panel.add(exit);
        generate.addActionListener(this);
        generate_cinema.addActionListener(this);
        hidden_level_help.addActionListener(this);
        search_tag_generate.addActionListener(this);
        about.addActionListener(this);
        random.addActionListener(this);
        exit.addActionListener(this);
        generate.setEnabled(false);
        generate_cinema.setEnabled(false);
        search_tag_generate.setEnabled(false);
        
        // 神秘按钮，默认禁用，开发完成后会打开。
        random.setEnabled(true);
        // Ended
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == text_input) {
            JOptionPane.showMessageDialog(null, "你不会以为这个有用吧？");
            text_input.setSelected(false);
            folder_input.setSelected(true);
        }
        if (actionEvent.getSource() == folder_input) {
            for (int i = 0;i < 4;i ++) {
                song_title_text[i].setEditable(false);
                author_text[i].setEditable(false);
                chart_design_text[i].setEditable(false);
                scene_text[i].setEditable(false);
                bpm_text[i].setEditable(false);
                difficulty_text[i].setEditable(false);
                level_text[i].setEditable(false);
            }
            tips_text.setEditable(false);
            browser.setEnabled(true);
        }
        if (actionEvent.getSource() == browser) {
        	try {
        		String chart_path = info_function.get_chart_path();
        		if (chart_path.equals("ERROR")) {
        			fileChooser = new JFileChooser("C:\\");
        		}
        		else {
        			fileChooser = new JFileChooser(chart_path);
        		}
        		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        		int folder_choose = fileChooser.showOpenDialog(null);
        		File file = fileChooser.getSelectedFile();
        		String files = "";
        		files = file.toString();
        		file_directory.setText(files);
        		if (files != "") {
        			input.setEnabled(true);
        		} else {
        			input.setEnabled(false);
        		}
        		
        	} catch (Exception e) {
        		// do nothing.
        		// 处理提出问题异常的模块比处理异常更简单。
        	}
            
        }
        if (actionEvent.getSource() == input) {
            String path = file_directory.getText();
            if (!path.isEmpty()) {
                save_config_file();
                generate.setEnabled(true);
                search_tag_generate.setEnabled(true);
            }
            info_input(path);
            tips_text.setText("");
        }
        if (actionEvent.getSource() == hidden_level_help) {
            getHelp();
        }
        if (actionEvent.getSource() == search_tag_generate) {
            new AddTagFrame();
        }
        if (actionEvent.getSource() == generate) {
            json_generate(file_directory.getText() + "\\info.json");
        }
        if (actionEvent.getSource() == generate_cinema) {
        	// 检测谱面是否存在，如果存在对应数组自增1.
        	int[] a = {0,0,0,0};
        	for (int i = 0;i < 4;i ++) {
        		if (!difficulty_text[i].getText().isEmpty()) {
        			a[i] ++;
        		}
        	}
            new CinemaInfoGenerateFrame(this, a, file_directory.getText(), video_file_name);
        }
        if (actionEvent.getSource() == about) {
            ShowDialog();
        }
        if (actionEvent.getSource() == random) {
            secretButton();
        }
        if (actionEvent.getSource() == exit) {	
        	File directory = new File("");//参数为空
        	utils tools = new utils();
        	try {
				String courseFile = directory.getCanonicalPath();
				System.exit(1);
			} catch (IOException e) {
				util.generateLogs_Window(e);
			}
            
        }
    }

    //读取到对应文件后执行的操作。
    public void info_input(String folder_path) {
        //读取到文件夹之后的操作。
        //检测是否存在对应难度的谱面。
    	// 加载之后将上一次的记录清空掉。
    	default_Charter = "";
        int map1_tag = 0, map2_tag = 0, map3_tag = 0, map4_tag = 0;
        int map_count = 0;
        File file = new File(folder_path);
        String[] fileList = file.list();
        String tips = "导入完成！曲目包含：";
        ChartInfo chartInfo = null;
        boolean video_exists = false;
        //在开始遍历之前，先将原有的内容全部清空掉。
        for (int i = 0;i < 4;i ++) {
            song_title_text[i].setText("");
            author_text[i].setText("");
            chart_design_text[i].setText("");
            scene_text[i].setText("");
            bpm_text[i].setText("");
            difficulty_text[i].setText("");
            level_text[i].setText("");
        }
        for (int i = 0;i < fileList.length;i ++) {
            if (fileList[i].equals("map1.bms")) {
                map1_tag++;
                map_count++;
            }
            if (fileList[i].equals("map2.bms")) {
                map2_tag++;
                map_count++;
            }
            if (fileList[i].equals("map3.bms")) {
                map3_tag++;
                map_count++;
            }
            if (fileList[i].equals("map4.bms")) {
                map4_tag++;
                map_count++;
            }
        }
        //检测是否有视频格式文件存在。要求必须是mp4格式。
        for (int i = 0;i < fileList.length;i ++) {
            if (fileList[i].endsWith("mp4")) {
                //妈的，不判断了，如果你真的想在谱面里面加视频，你就不会放一个假视频进去。
                video_file_name = fileList[i];
                System.out.println(video_file_name);
                video_exists = true;
                break;
            } else {
                continue;
            }
        }
        if (video_exists == true) {
            generate_cinema.setEnabled(true);
        } else {
            generate_cinema.setEnabled(false);
        }
        try {
        	if (map1_tag == 1) {
            tips += "萌新（Easy） ";
            String temp_path = folder_path + "\\" + "map1.bms";
            chartInfo = getInfo(temp_path);
            //getInfo之后，将获取到的内容填写在文本框中。
            song_title_text[0].setText(chartInfo.getSong_title());
            author_text[0].setText(chartInfo.getAuthor());
            chart_design_text[0].setText(chartInfo.getChart_design());
            scene_text[0].setText(chartInfo.getScene());
            bpm_text[0].setText(chartInfo.getBpm());
            difficulty_text[0].setText("萌新");
            level_text[0].setText(chartInfo.getLevel());
        }
        if (map2_tag == 1) {
            tips += "高手（Hard） ";
            String temp_path = folder_path + "\\" + "map2.bms";
            chartInfo = getInfo(temp_path);

            song_title_text[1].setText(chartInfo.getSong_title());
            author_text[1].setText(chartInfo.getAuthor());
            chart_design_text[1].setText(chartInfo.getChart_design());
            scene_text[1].setText(chartInfo.getScene());
            bpm_text[1].setText(chartInfo.getBpm());
            difficulty_text[1].setText("高手");
            level_text[1].setText(chartInfo.getLevel());
        }
        if (map3_tag == 1) {
            tips += "大触（Master） ";
            String temp_path = folder_path + "\\" + "map3.bms";
            chartInfo = getInfo(temp_path);

            song_title_text[2].setText(chartInfo.getSong_title());
            author_text[2].setText(chartInfo.getAuthor());
            chart_design_text[2].setText(chartInfo.getChart_design());
            scene_text[2].setText(chartInfo.getScene());
            bpm_text[2].setText(chartInfo.getBpm());
            difficulty_text[2].setText("大触");
            level_text[2].setText(chartInfo.getLevel());
        }
        if (map4_tag == 1) {
            tips += "隐藏（Hidden） ";
            String temp_path = folder_path + "\\" + "map4.bms";
            chartInfo = getInfo(temp_path);
            tips_text.setEditable(true);
            unlock_action.setEnabled(true);
            hidden_unlock_level.setEnabled(true);
            //设置隐藏的JComboBox中的内容。
            unlock_action.removeAllItems();
            hidden_unlock_level.removeAllItems();
            hidden_unlock_level.addItem("- 请选择 -");
            hidden_unlock_level.addItem("-1");
            hidden_unlock_level.addItem("0");
            unlock_action.addItem("- 请选择 -");
            unlock_action.addItem("CLICK");
            unlock_action.addItem("PRESS");
            if (map1_tag == 1 && map2_tag == 1 && map3_tag == 1) {
                unlock_action.addItem("TOGGLE");
            }
            if (map1_tag == 1) {
                hidden_unlock_level.addItem("1");
            }
            if (map2_tag == 1) {
                hidden_unlock_level.addItem("2");
            }
            if (map3_tag == 1) {
                hidden_unlock_level.addItem("3");
            }

            song_title_text[3].setText(chartInfo.getSong_title());
            author_text[3].setText(chartInfo.getAuthor());
            chart_design_text[3].setText(chartInfo.getChart_design());
            scene_text[3].setText(chartInfo.getScene());
            bpm_text[3].setText(chartInfo.getBpm());
            difficulty_text[3].setText("隐藏");
            level_text[3].setText(chartInfo.getLevel());
        }
        tips += "难度的谱面。";

        JOptionPane.showMessageDialog(this, tips);
        
        //检测默认谱师的功能，如果只包含一个谱面，就把唯一的那张谱面的谱师名称作为默认谱师名称。
        if (map_count == 1) {
        	for (int i = 0;i < 4;i ++) {
        		if (!chart_design_text[i].getText().isEmpty()) {
        			default_Charter = chart_design_text[i].getText();
        		}
        	}
        }
        
        //如果没有隐藏谱面，将hidden_info的文本框禁用掉。
        if (map4_tag == 0) {
            tips_text.setEditable(false);
            unlock_action.setEnabled(false);
            hidden_unlock_level.setEnabled(false);
        }
        
        // 对于选择的背景进行检测，解锁对应的单选按钮。
        if (scene_text[0].getText().equals("scene_05") || scene_text[1].getText().equals("scene_05") || 
        		scene_text[2].getText().equals("scene_05") ||scene_text[3].getText().equals("scene_05")) {
        	scene_None.setEnabled(true);
        	scene_Cytus.setEnabled(true);
        	scene_Wacca.setEnabled(true);
        	scene_Touhou.setEnabled(true);
        	scene_BadApple.setEnabled(false);
        	scene_Christmas.setEnabled(true);
        	scene_RinLen.setEnabled(false);
        } else {
        	if (scene_text[0].getText().equals("scene_08") || scene_text[1].getText().equals("scene_08") || 
        		scene_text[2].getText().equals("scene_08") ||scene_text[3].getText().equals("scene_08")) {
        		scene_None.setEnabled(true);
            	scene_Cytus.setEnabled(false);
            	scene_Wacca.setEnabled(false);
            	scene_Touhou.setEnabled(false);
            	scene_BadApple.setEnabled(true);
            	scene_Christmas.setEnabled(false);
            	scene_RinLen.setEnabled(false);
        	} else {
        		if ((scene_text[0].getText().equals("scene_07") || scene_text[1].getText().equals("scene_07") || scene_text[2].getText().equals("scene_07") ||scene_text[3].getText().equals("scene_07"))
        				|| (scene_text[0].getText().equals("scene_09") || scene_text[1].getText().equals("scene_09") || 
                        		scene_text[2].getText().equals("scene_09") ||scene_text[3].getText().equals("scene_09"))) {
        			scene_None.setEnabled(true);
                	scene_Cytus.setEnabled(false);
                	scene_Wacca.setEnabled(false);
                	scene_Touhou.setEnabled(false);
                	scene_BadApple.setEnabled(false);
                	scene_Christmas.setEnabled(false);
                	scene_RinLen.setEnabled(false);
        		} else 
        			if (scene_text[0].getText().equals("scene_10") || scene_text[1].getText().equals("scene_10") || 
                		scene_text[2].getText().equals("scene_10") ||scene_text[3].getText().equals("scene_10")) {
        			scene_None.setEnabled(true);
        			scene_Cytus.setEnabled(false);
        			scene_Wacca.setEnabled(false);
        			scene_Touhou.setEnabled(false);
        			scene_BadApple.setEnabled(false);
        			scene_Christmas.setEnabled(false);
        			scene_RinLen.setEnabled(true);
        		}
        		else {
        			scene_None.setEnabled(true);
        			scene_Cytus.setEnabled(true);
        			scene_Wacca.setEnabled(true);
        			scene_Touhou.setEnabled(true);
        			scene_BadApple.setEnabled(false);
        			scene_Christmas.setEnabled(false);
        			scene_RinLen.setEnabled(false);
        		}
        	}
        }
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        }
        

    }

    //根据bms文件的格式来选取特定区域的字符串，以便最后拼接形成最后的json文件。
    public ChartInfo getInfo(String chart_path) {
        StringBuilder textInfo = new StringBuilder();
        File file = new File(chart_path);
        String file_text = null;
        //曲目信息。
        String song_title = "";
        String author = "";
        String chart_design = "";
        String scene = "";
        String bpm = "";
        String difficulty = "";
        String level = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = null;
            //使用readLine方法，一次读取一行。
            while ((s = bufferedReader.readLine()) != null ) {
                textInfo.append(System.lineSeparator() + s);
            }
            bufferedReader.close();
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        }
        try {
        	file_text = textInfo.toString();
        	song_title = file_text.substring(file_text.indexOf("#TITLE ") + 7, file_text.indexOf("#ART"));
        	author = file_text.substring(file_text.indexOf("#ARTIST ") + 8, file_text.indexOf("#LEV"));
        	chart_design = file_text.substring(file_text.indexOf("#LEVELDESIGN ") + 13, file_text.indexOf("#BPM"));
        	scene = file_text.substring(file_text.indexOf("#GENRE ") + 7, file_text.indexOf("#TIT"));
        	bpm = file_text.substring(file_text.indexOf("#BPM ") + 5, file_text.indexOf("#PLAYLEVEL"));
        	difficulty = file_text.substring(file_text.indexOf("#RANK ") + 6, file_text.indexOf("#LN"));
        	level = file_text.substring(file_text.indexOf("#PLAYLEVEL ") + 11, file_text.indexOf("#RAN"));
        	
        	//去除换行符\r,\n
            song_title = song_title.replaceAll("\r|\n","");
            author = author.replaceAll("\r|\n","");
            chart_design = chart_design.replaceAll("\r|\n","");
            scene = scene.replaceAll("\r|\n","");
            bpm = bpm.replaceAll("\r|\n","");
            difficulty = difficulty.replaceAll("\r|\n","");
            level = level.replaceAll("\r|\n","");
            
            ChartInfo chartInfo = new ChartInfo(song_title, author, chart_design, scene, bpm, difficulty, level);
            System.out.println(chartInfo.toString());
            return chartInfo;
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        	return null;
        } 
        
    }

    //对已经输入的输入项进行检查，以保证多张谱面是来自同一个曲目。
    //先不包含参数。
    public String json_generate(String save_path) {
        //检测第一个不是空的歌曲名、艺术家歌手、场景和bpm文本框。
        //all parameters in .json file.
        String name = "?";
        String author = "?";
        String bpm = "?";
        String scene = "scene_01";
        String levelDesigner = "?";
        String levelDesigner1 = "?";
        String levelDesigner2 = "?";
        String levelDesigner3 = "?";
        String levelDesigner4 = "?";
        String difficulty1 = "0";
        String difficulty2 = "0";
        String difficulty3 = "0";
        String difficulty4 = "0";
        String hideBmsMode = "";   //well, it is not useless.
        String hideBmsDifficulty = "0"; //well, it is not useless.
        String hideBmsMessage = "demo created by GreenHub.";
        String unlockLevel = "0";       //useless parameter.
        String[] searchTags = null;
        //find the first textfield that is not blank.
        for (int i = 0;i < 4;i ++) {
            if (!song_title_text[i].getText().isEmpty()) {
                name = song_title_text[i].getText();
                author = author_text[i].getText();
                bpm = bpm_text[i].getText();
                scene = scene_text[i].getText();
                break;
            }
        }
        hideBmsMode = (String) unlock_action.getSelectedItem();
        hideBmsDifficulty = (String) hidden_unlock_level.getSelectedItem();
        if (hideBmsMode == "- 请选择 -") {
            hideBmsMode = "CLICK";
        }
        if (hideBmsDifficulty == "- 请选择 -") {
            hideBmsDifficulty = "0";
        }
        //填充其他的信息。
        if (!chart_design_text[0].getText().isEmpty()) {
            levelDesigner1 = chart_design_text[0].getText();
        }
        if (!chart_design_text[1].getText().isEmpty()) {
            levelDesigner2 = chart_design_text[1].getText();
        }
        if (!chart_design_text[2].getText().isEmpty()) {
            levelDesigner3 = chart_design_text[2].getText();
        }
        if (!chart_design_text[3].getText().isEmpty()) {
            levelDesigner4 = chart_design_text[3].getText();
        }
        if (!level_text[0].getText().isEmpty()) {
            difficulty1 = level_text[0].getText();
        }
        if (!level_text[1].getText().isEmpty()) {
            difficulty2 = level_text[1].getText();
        }
        if (!level_text[2].getText().isEmpty()) {
            difficulty3 = level_text[2].getText();
        }
        if (!level_text[3].getText().isEmpty()) {
            difficulty4 = level_text[3].getText();
        }
        if (!tips_text.getText().isEmpty()) {
            hideBmsMessage = tips_text.getText();
        }
        if (song_title_text[0].getText().isEmpty() && song_title_text[1].getText().isEmpty() && song_title_text[2].getText().isEmpty()
         && song_title_text[3].getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写歌曲信息！");
        } else if (author_text[0].getText().isEmpty() && author_text[1].getText().isEmpty() && author_text[2].getText().isEmpty()
                && author_text[3].getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写艺术家信息！");
        } else if (bpm_text[0].getText().isEmpty() && bpm_text[1].getText().isEmpty() && bpm_text[2].getText().isEmpty()
                && bpm_text[3].getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写bpm信息！");
        } else if (tips_text.getText().isEmpty() && difficulty4 != "0") {
            JOptionPane.showMessageDialog(this, "请输入提示信息！");
        } else if (hideBmsMode.equals("- 请选择 -") && difficulty4 != "0") {
            JOptionPane.showMessageDialog(this, "请选择里谱解锁方式！");
        } else if (hideBmsDifficulty.equals("- 请选择 -") && difficulty4 != "0") {
            JOptionPane.showMessageDialog(this, "请选择里谱面解锁等级！");
        }
        else {
        	String defaultCharter = "";
        	if (default_Charter != "") {
        		levelDesigner = default_Charter;
        		levelDesigner1 = default_Charter;
        		levelDesigner2 = default_Charter;
        		levelDesigner3 = default_Charter;
        		levelDesigner4 = default_Charter;
        	} else {
        		// 输入对话框提示键入默认谱师。
	            defaultCharter = JOptionPane.showInputDialog(this, "请输入默认谱师：", "");
	
	            // 添加这一行只是单纯为了在点击取消后不出现空对象异常。
	            if (defaultCharter == null) {
	                System.out.println("输入的是一个空对象。");
	            } else {
	                while (true) {
	                    assert defaultCharter != null;
	                    if (defaultCharter.isEmpty()) {
	                        JOptionPane.showMessageDialog(this, "默认谱师不能为空！");
	                        defaultCharter = JOptionPane.showInputDialog(this, "请输入默认谱师：", "");
	
	
	                        // TMD，空对象异常去不掉，算了。
	                        // 反正不影响用，就这样吧。改完反倒还容易出问题。
	                        // GreenHub 2023.10.7
	
	                        if (defaultCharter == null) {
	                            System.out.println("输入的是一个空对象。");
	                            return "CANCEL";
	                        }
	                    }
	                    else {
	                        levelDesigner = defaultCharter;
	                        break;
	                    }
	                }
	        	}
        	}
            try {
                    // 增加对?字段的判断。
                    if (levelDesigner1.equals("?")) {
                        levelDesigner1 = defaultCharter;
                    }
                    if (levelDesigner2.equals("?")) {
                        levelDesigner2 = defaultCharter;
                    }
                    if (levelDesigner3.equals("?")) {
                        levelDesigner3 = defaultCharter;
                    }
                    if (levelDesigner4.equals("?")) {
                        levelDesigner4 = defaultCharter;
                    }

                    // 读取根目录的search.json文件，并取出其中的内容，将其写入到search数组中。
                    // PPT提前完工了，有时间把最后一段代码完工，不用等到下周了。
                    // 再稍微改改就可以丢给群友做Beta测试了。

                    // 读取根目录中的search.json的内容。
                    String search_file_path = file_directory.getText() + "\\search.json";
                    File search_file = new File(search_file_path);
                    if (search_file.exists()) {
                        searchTags = search_tag_load(search_file_path);
                    } else {
                        searchTags = new String[0];
                    }
                    JSONObject jsonObject = new JSONObject(true);
                    jsonObject.put("name",name);
                    jsonObject.put("author",author);
                    jsonObject.put("bpm",bpm);
                    jsonObject.put("scene",scene);
                    jsonObject.put("levelDesigner",levelDesigner);
                    jsonObject.put("levelDesigner1",levelDesigner1);
                    jsonObject.put("levelDesigner2",levelDesigner2);
                    jsonObject.put("levelDesigner3",levelDesigner3);
                    jsonObject.put("levelDesigner4",levelDesigner4);
                    jsonObject.put("difficulty1",difficulty1);
                    jsonObject.put("difficulty2",difficulty2);
                    jsonObject.put("difficulty3",difficulty3);
                    jsonObject.put("difficulty4",difficulty4);
                    jsonObject.put("hideBmsMode",hideBmsMode);
                    jsonObject.put("hideBmsDifficulty",hideBmsDifficulty);
                    jsonObject.put("hideBmsMessage",hideBmsMessage);
                    jsonObject.put("unlockLevel",unlockLevel);
                    jsonObject.put("searchTags",searchTags);
                    
                    // 判断Christmas是否被选中，如果选中，添加对应的参数。
                    // 后续看了一下CA的仓库，发现了更多的参数。
                    if (scene_None.isSelected()) {
                    	jsonObject.put("sceneEgg", "none");
                    }
                    if (scene_Cytus.isSelected()) {
                    	jsonObject.put("sceneEgg", "cytus");
                    }
                    if (scene_Wacca.isSelected()) {
                    	jsonObject.put("sceneEgg", "wacca");
                    }
                    if (scene_Touhou.isSelected()) {
                    	jsonObject.put("sceneEgg", "touhou");
                    }
                    if (scene_BadApple.isSelected()) {
                    	jsonObject.put("sceneEgg", "badapple");
                    }
                    if (scene_Christmas.isSelected()) {
                    	jsonObject.put("sceneEgg", "christmas");
                    }
                    if (scene_RinLen.isSelected()) {
                    	jsonObject.put("sceneEgg", "RinLen");
                    }
                    
                    //最后保存到文件。
                    //格式化json字符串。
                    String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                    //标记文件是否成功生成。
                    boolean flag = true;
                    //生成json格式文件。
                    try {
                        File file = new File(save_path);
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
                        flag = false;
                        util.generateLogs_Window(e);
                    }
                    if (flag == true) {
                        JOptionPane.showMessageDialog(this, "info.json文件生成成功！");
                    }
                } catch (Exception e) {
                	util.generateLogs_Window(e);
                }    

        }
        return "OK";

    }
    // 生成Cinema.json方法。
    // 2024.3.31  新增了对于不同难度选择展示视频的功能，现在对该功能模块重写，预计会对窗体进行修改。
    // 写到了InfoGenerate库内部，目前此文件内的函数废弃。
    

    // 生成config.json文件。
    public void save_config_file() {
        String config_path = System.getProperty("user.dir");
        String chart_path = file_directory.getText();
        try {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("chart_path",chart_path);
            //格式化json字符串。
            String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            boolean flag = true;
            //生成json格式文件。
            try {
                File file = new File(config_path + "\\config.json");
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
                flag = false;
                util.generateLogs_Window(e);
            }
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        }
    }
    //About页面的JDialog界面
    public void ShowDialog() {
        JDialog jDialog = new JDialog();
        //组件包含：两个面板，一个JLabel，一个关闭按钮JButton
        JPanel title_panel = new JPanel();
        JPanel main_about_panel = new JPanel();
        JPanel control_about_panel = new JPanel();

        JButton ok = new JButton("确定");
        JLabel title_label = new JLabel("关于此程序");
        JLabel about_label = new JLabel(
                "<html><body>此程序为info.json文件生成器，致力于帮助mdmc自制社区的谱师进行谱面信息生成。<br>" +
                        "<b>我跟你讲写谱超好玩的，快来和我一起写谱呀！（QB脸）</b><br>" +
                "程序开发/打包：GreenHub<br>" +
                        "测试：GreenHub<br>" +
                        "请使用Java 12及以上版本的Java运行。（没错我已经抛弃Java 8了！）<br>" +
                "此程序虽然已经是正式版本，但可能包含少量bug。如果有bug可以联系开发者。</body></html>");
        jDialog.setTitle("关于此程序");
        jDialog.setSize(800,320);
        jDialog.setLocation(400,220);
        jDialog.setLayout(new BorderLayout());
        jDialog.add(main_about_panel);
        jDialog.add(title_panel,BorderLayout.NORTH);
        title_panel.setLayout(new FlowLayout());
        title_panel.add(title_label);
        title_label.setFont(titleFont);
        jDialog.add(main_about_panel, BorderLayout.CENTER);
        main_about_panel.setLayout(new FlowLayout());
        main_about_panel.add(about_label);
        about_label.setFont(mainFont);
        jDialog.add(control_about_panel, BorderLayout.SOUTH);
        control_about_panel.setLayout(new FlowLayout());
        control_about_panel.add(ok);
        ok.setFont(mainFont);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == ok) {
                    jDialog.dispose();
                }
            }
        });
        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }

    // GreenHubの奇妙小按钮，鉴于这个功能真的屁用没有，该功能目前已经被废弃，但不排除将来可能会整点什么别的活。
    // Deprecated.
    
    public void secretButton() {
    	// MysteryFrame类，阻塞父窗体。
        MysteryFrame m = new MysteryFrame(this);
        JOptionPane.showMessageDialog(null, "如果你看到这个对话框，请立刻发信息给GreenHub，并给他一巴掌让他起床写代码！");
        m.dispose();
    }

    //隐藏谱面信息帮助窗体。
    public void getHelp() {
        JDialog jDialog = new JDialog();
        jDialog.setSize(800,400);
        jDialog.setLocation(400,220);
        JPanel title_panel = new JPanel();
        JPanel main_help_panel = new JPanel();
        JPanel control_about_panel = new JPanel();

        JButton ok = new JButton("确定");
        JLabel title_label = new JLabel("关于里谱面信息填写的简要帮助");
        JLabel about_label = new JLabel(
                "<html><body>" +
                        "<b>“里谱解锁方式”参数说明：</b><br>" +
                        "“CLICK”：猛戳难度进入里谱面；<br>" +
                        "“PRESS”：在选歌界面长按封面进入里谱面；<br>" +
                        "“TOGGLE”：在曲目的三个难度之间切换进入里谱面（谱面必须包含完整的三个难度）；<br>" +
                        "<b>“里谱解锁等级”参数说明：</b><br>" +
                        "-1：进入里谱时，只保留里谱面，其余正常谱面全部消失；<br>" +
                        "0：默认模式，进入里谱时，高手难度或大触难度谱面替换为里谱；<br>" +
                        "1：进入里谱时，萌新难度替换为里谱面；<br>" +
                        "2：进入里谱时，高手难度替换为里谱面；<br>" +
                        "3：进入里谱时，大触难度替换为里谱面。<br></body></html>");
        jDialog.setTitle("帮助");
        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jDialog.setLayout(new BorderLayout());
        jDialog.add(main_help_panel);
        jDialog.add(title_panel,BorderLayout.NORTH);
        title_panel.setLayout(new FlowLayout());
        title_panel.add(title_label);
        title_label.setFont(titleFont);
        jDialog.add(main_help_panel, BorderLayout.CENTER);
        main_help_panel.setLayout(new FlowLayout());
        main_help_panel.add(about_label);
        about_label.setFont(mainFont);
        jDialog.add(control_about_panel, BorderLayout.SOUTH);
        control_about_panel.setLayout(new FlowLayout());
        control_about_panel.add(ok);
        ok.setFont(mainFont);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == ok) {
                    jDialog.dispose();
                }
            }
        });
        jDialog.setResizable(false);
        jDialog.setModal(true);
        jDialog.setVisible(true);

    }

    // 读取search.json文件。
    public String[] search_tag_load(String path) {
        // 读取config文件，并将其内容放置在search.json文件中。
        StringBuilder tags_builder = new StringBuilder();
        String tag_json = null;
        String[] tags_list = null;
        File file2 = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                tags_builder.append(System.lineSeparator() + s);
            }
            bufferedReader.close();
        } catch (Exception e) {
        	util.generateLogs_Window(e);
        }
        tag_json = tags_builder.toString();
        net.sf.json.JSONObject tags = net.sf.json.JSONObject.fromObject(tag_json);
        if (tags.containsKey("searchTags")) {
            JSONArray tags_list_json = tags.getJSONArray("searchTags");
            tags_list = new String[tags_list_json.size()];
            for (int i = 0;i < tags_list_json.size();i ++) {
                tags_list[i] = tags_list_json.getString(i);
            }
        }
        return tags_list;
    }
    public static void main(String[] args) {
        new Mainframe();
    }
    
}
