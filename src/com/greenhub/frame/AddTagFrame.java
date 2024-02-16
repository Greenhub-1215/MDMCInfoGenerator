package com.greenhub.frame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;

public class AddTagFrame extends JDialog implements ActionListener{

    private String title = "添加搜索Tag";
    private JPanel main_panel = new JPanel();
    private JPanel main_panel_1 = new JPanel();
    private JPanel control = new JPanel();
    private JPanel control_part_1 = new JPanel();
    private JPanel control_part_2 = new JPanel();
    private JList<String> tag_list;
    private JScrollPane scrollPane;
    private JButton add_item = new JButton("新增项目");
    private JButton modify_item = new JButton("修改项目");
    private JButton delete_item = new JButton("删除项目");
    private JButton save = new JButton("保存");
    private JButton load = new JButton("加载");
    private JButton exit = new JButton("关闭");
    private DefaultListModel<String> model = new DefaultListModel<>();
    public AddTagFrame() {
        this.setTitle(title);
        this.setSize(500,300);
        this.setLocation(600,300);
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 在这里设置布局和相关的属性。
        this.setLayout(new BorderLayout());
        this.add(main_panel, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);
        main_panel.setLayout(new BorderLayout());
        main_panel.add(main_panel_1);
        main_panel_1.setLayout(new BorderLayout());

        tag_list = new JList<>(model);
        main_panel_1.setBorder(BorderFactory.createTitledBorder("Tag标签"));
        scrollPane = new JScrollPane(tag_list);
        main_panel_1.add(scrollPane);
        control.setLayout(new GridLayout(2,1));
        control.add(control_part_1);
        control.add(control_part_2);
        control_part_1.setLayout(new FlowLayout());
        control_part_2.setLayout(new FlowLayout());
        control_part_1.add(add_item);
        control_part_1.add(modify_item);
        control_part_1.add(delete_item);
        control_part_2.add(save);
        control_part_2.add(load);
        control_part_2.add(exit);



        // add action event.
        tag_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int i = tag_list.getSelectedIndex();
                if (i != -1) {
                    modify_item.setEnabled(true);
                    delete_item.setEnabled(true);
                }
            }
        });
        add_item.addActionListener(this);
        modify_item.addActionListener(this);
        modify_item.setEnabled(false);
        delete_item.addActionListener(this);
        delete_item.setEnabled(false);
        save.addActionListener(this);
        load.addActionListener(this);
        exit.addActionListener(this);
        // Ends here.

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add_item) {
            String str = JOptionPane.showInputDialog(this,"请输入新增Tag内容：");
            while (Objects.equals(str, "")) {
                JOptionPane.showMessageDialog(this,"文本框内容不能为空！！");
                str = JOptionPane.showInputDialog(this,"请输入新增Tag内容：");
            }
            model.addElement(str);
            title = "*添加搜索Tag";
            this.setTitle(title);

        }
        if (e.getSource() == delete_item) {
            Object[] options = {"确定", "取消"};        //定义按钮上的文字
            int n = JOptionPane.showOptionDialog(null,"确定要删除该条记录吗？","Title",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options);
            if (n == 1) {
                System.out.println("不执行删除操作。");
            } else if (n == 0) {
                int i = tag_list.getSelectedIndex();
                model.removeElementAt(i);
                System.out.println("执行删除操作。");
                modify_item.setEnabled(false);
                delete_item.setEnabled(false);
                title = "*添加搜索Tag";
                this.setTitle(title);
            }
        }
        if (e.getSource() == modify_item) {
            System.out.println("执行修改操作。");
            int i = tag_list.getSelectedIndex();
            String value = tag_list.getSelectedValue();
            String str = JOptionPane.showInputDialog(this,"请输入修改后的Tag内容：",value);
            System.out.println(str);
            while (Objects.equals(str, "")) {
                JOptionPane.showMessageDialog(this,"文本框内容不能为空！！");
                str = JOptionPane.showInputDialog(this,"请输入修改后的Tag内容：");
            }
            model.set(i, str);
            tag_list.updateUI();
            title = "*添加搜索Tag";
            this.setTitle(title);

        }
        if (e.getSource() == save) {
            search_tag_save();
        }
        if (e.getSource() == load) {
            JSONObject jsonObject = JSONObject.fromObject(current_path());
            JFileChooser search_tag_chooser = new JFileChooser(jsonObject.getString("chart_path"));
            // 禁用“全部文件”的选择。
            search_tag_chooser.setAcceptAllFileFilterUsed(false);
            // 只选用json格式的文件。
            search_tag_chooser.setFileFilter(new FileNameExtensionFilter("JSON格式文件","json"));
            int file_choose = search_tag_chooser.showOpenDialog(null);
            if (file_choose == JFileChooser.APPROVE_OPTION) {
                File file = search_tag_chooser.getSelectedFile();
                String file_path = file.toString();
                search_tag_load(file_path);
                if (!file_path.isEmpty()) {
                    title = "*添加搜索Tag";
                    this.setTitle(title);
                }
            }
        }
        if (e.getSource() == exit) {
            // 检测文件是否保存，如果没有保存则提示用户是否保存。
            if (Objects.equals(title, "*添加搜索Tag")) {
                int result = JOptionPane.showConfirmDialog(null,"您尚未保存对searchTag的更改，是否保存？","",JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    search_tag_save();
                } else if (result == JOptionPane.NO_OPTION) {
                    this.dispose();
                }
            } else {
                this.dispose();
            }
        }
    }
    
    // 生成search.json文件。
    public void search_tag_save() {
        // 从根目录中读取config.json文件，读取到此时的项目根目录。
        String chart_file_path = null;
        chart_file_path = current_path();
        JSONObject load_json = JSONObject.fromObject(chart_file_path);
        // 读取此时谱面的根目录。
        String save_path = load_json.getString("chart_path");
        try {
            JSONObject jsonObject = new JSONObject();
            // 读取JList中的内容，并将其写入String数组之中。
            int num = tag_list.getModel().getSize();
            String[] searchTags = new String[num];
            for (int i = 0;i < num;i ++) {
                searchTags[i] = tag_list.getModel().getElementAt(i);
            }
            jsonObject.put("searchTags", searchTags);
            //格式化json字符串。
            String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            System.out.println("info content: " + content);
            //生成json格式文件。
            File file1 = new File(save_path + "\\search.json");
            if (file1.exists()) {
                file1.delete();
            }
            file1.createNewFile();
            //将格式化后的字符串写入文件。
            Writer writer = new OutputStreamWriter(new FileOutputStream(file1), "UTF-8");
            writer.write(content);
            JOptionPane.showMessageDialog(this,"searchTag文件已保存。");
            title = "添加搜索Tag";
            this.setTitle(title);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取search.json文件。
    public void search_tag_load(String path) {
        // 读取config文件，并将其内容放置在search.json文件中。
        StringBuilder tags_builder = new StringBuilder();
        String tag_json = null;
        File file2 = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                tags_builder.append(System.lineSeparator() + s);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tag_json = tags_builder.toString();
        JSONObject tags = JSONObject.fromObject(tag_json);
        if (tags.containsKey("searchTags")) {
            JSONArray tags_list_json = tags.getJSONArray("searchTags");
            String[] tags_list = new String[tags_list_json.size()];
            model.removeAllElements();
            for (int i = 0;i < tags_list_json.size();i ++) {
                tags_list[i] = tags_list_json.getString(i);
                model.addElement(tags_list[i]);
            }
        }
    }

    // 读取当前的项目目录。
    public String current_path() {
        String config_json_path = "config.json";
        StringBuilder json_info = new StringBuilder();
        String chart_file_path = null;
        // 读取config.json文件的内容。
        File file = new File(config_json_path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = null;
            //使用readLine方法，一次读取一行。
            while ((s = bufferedReader.readLine()) != null ) {
                json_info.append(System.lineSeparator() + s);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json_info.toString();
    }
}
