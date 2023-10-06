package com.greenhub.entity;

public class ChartInfo {
    public String song_title;
    public String author;
    public String chart_design;
    public String scene;
    public String bpm;
    public String difficulty;
    public String level;

    public ChartInfo(String song_title, String author, String chart_design, String scene, String bpm, String difficulty, String level) {
        this.song_title = song_title;
        this.author = author;
        this.chart_design = chart_design;
        this.scene = scene;
        this.bpm = bpm;
        this.difficulty = difficulty;
        this.level = level;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChart_design() {
        return chart_design;
    }

    public void setChart_design(String chart_design) {
        this.chart_design = chart_design;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ChartInfo{" +
                "song_title='" + song_title + '\'' +
                ", author='" + author + '\'' +
                ", chart_design='" + chart_design + '\'' +
                ", scene='" + scene + '\'' +
                ", bpm='" + bpm + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
