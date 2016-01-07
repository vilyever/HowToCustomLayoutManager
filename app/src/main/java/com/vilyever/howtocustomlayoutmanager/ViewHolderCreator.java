package com.vilyever.howtocustomlayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

/**
 * ViewHolderCreator
 * HowToCustomLayoutManager <com.vilyever.howtocustomlayoutmanager>
 * Created by vilyever on 2016/1/7.
 * Feature:
 * 提供ViewHolder的便捷构造
 */
public class ViewHolderCreator {
    /** Convenience Var to call this */
    final ViewHolderCreator self = this;

    /**
     * ViewHolder便捷构造
     * @param clazz 需要生成的ViewHolder的class
     * @param parent 生成ViewHolder的itemView的父view
     * @param layoutID 生成ViewHolder的itemView的layout
     * @param <T> 泛型继承{@link android.support.v7.widget.RecyclerView.ViewHolder}
     * @return 类型为T的实例
     */
    @SuppressWarnings("unchecked")
    public static <T extends RecyclerView.ViewHolder> T CreateInstance(Class<?> clazz, ViewGroup parent, int layoutID) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutID, parent, false);

        try {
            Constructor constructor = clazz.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            return (T) constructor.newInstance(itemView);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}