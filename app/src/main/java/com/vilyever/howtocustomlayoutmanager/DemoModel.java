package com.vilyever.howtocustomlayoutmanager;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * DemoModel
 * HowToCustomLayoutManager <com.vilyever.howtocustomlayoutmanager>
 * Created by vilyever on 2016/1/7.
 * Feature:
 * 示例model
 * 仅提供示例所需数据，实际应用中可能存在更多冗余数据（针对RecyclerView所需）
 */
public class DemoModel {
    /** Convenience Var to call this */
    final DemoModel self = this;

    public static DemoModel generateDemoModel() {
        DemoModel demoModel = new DemoModel();
        Random random = new Random();
        int color = Color.rgb(Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256);
        int width = (Math.abs(random.nextInt()) % 6 + 1) * 80 + 100;
        int height = (Math.abs(random.nextInt()) % 6 + 1) * 80 + 100;
        int block = Math.abs(random.nextInt()) % 6;
        demoModel.setTitle(width + "x" + height + "(" + block + ")").setColor(color).setPreferWidth(width).setPreferHeight(height).setBlocks(block);
        return demoModel;
    }

    /**
     * 生成50个model列表
     * @return 50个model列表
     */
    public static ArrayList<DemoModel> generateDemoList() {
        ArrayList<DemoModel> models = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            models.add(DemoModel.generateDemoModel());
        }

        return models;
    }

    private String title;
    public DemoModel setTitle(String title) {
        this.title = title;
        return this; 
    }
    public String getTitle() {
        return title;
    }
    
    private int color;
    public DemoModel setColor(int color) {
        this.color = color;
        return this; 
    }
    public int getColor() {
        return color;
    }

    private int preferWidth;
    public DemoModel setPreferWidth(int preferWidth) {
        this.preferWidth = preferWidth;
        return this;
    }
    public int getPreferWidth() {
        return preferWidth;
    }

    private int preferHeight;
    public DemoModel setPreferHeight(int preferHeight) {
        this.preferHeight = preferHeight;
        return this;
    }
    public int getPreferHeight() {
        return preferHeight;
    }

    private int blocks;
    public DemoModel setBlocks(int blocks) {
        this.blocks = blocks;
        return this;
    }
    public int getBlocks() {
        return blocks;
    }

}