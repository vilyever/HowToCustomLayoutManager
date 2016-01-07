package com.vilyever.howtocustomlayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * CustomLayoutManager
 * HowToCustomLayoutManager <com.vilyever.howtocustomlayoutmanager>
 * Created by vilyever on 2016/1/7.
 * Feature:
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    /** Convenience Var to call this */
    final CustomLayoutManager self = this;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler); // 分离所有的itemView

        int offsetX = 0;
        int offsetY = 0;

        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i); // 根据position获取一个碎片view，可以从回收的view中获取，也可能新构造一个

            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);  // 计算此碎片view包含边距的尺寸

            int width = getDecoratedMeasuredWidth(scrap);  // 获取此碎片view包含边距和装饰的宽度width
            int height = getDecoratedMeasuredHeight(scrap); // 获取此碎片view包含边距和装饰的高度height

            layoutDecorated(scrap, offsetX , offsetY, offsetX + width, offsetY + height); // Important！布局到RecyclerView容器中，所有的计算都是为了得出任意position的item的边界来布局

            offsetX += width;
            offsetY += height;
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
//    public static class LayoutParams extends RecyclerView.LayoutParams {
//
//        /**
//         * 当前行数
//         */
//        public int row;
//        /**
//         * 当前列数
//         */
//        public int column;
//
//        /**
//         * 占用块数
//         * 不同方向布局时，占用行数或列数的值
//         * default is 1
//         */
//        public int occupationBlocks = 1;
//
//        public LayoutParams(Context c, AttributeSet attrs) {
//            super(c, attrs);
//        }
//        public LayoutParams(int width, int height) {
//            super(width, height);
//        }
//        public LayoutParams(ViewGroup.MarginLayoutParams source) {
//            super(source);
//        }
//        public LayoutParams(ViewGroup.LayoutParams source) {
//            super(source);
//        }
//        public LayoutParams(RecyclerView.LayoutParams source) {
//            super(source);
//        }
//    }

}