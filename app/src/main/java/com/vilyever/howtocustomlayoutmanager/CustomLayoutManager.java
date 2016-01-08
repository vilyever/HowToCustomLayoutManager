package com.vilyever.howtocustomlayoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
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

    /** @see android.support.v7.widget.RecyclerView.LayoutManager#onLayoutChildren(RecyclerView.Recycler, RecyclerView.State) */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画，暂时先使用自带的简单的fading
            return;
        }
        self.detachAndScrapAttachedViews(recycler); // 分离所有的itemView

        self.getState().contentWidth = 0;
        self.getState().contentHeight = 0;

        int offsetX = 0;
        int offsetY = 0;

        /**
         * 计算所有item的frame，以及总宽高
         */
        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i); // 根据position获取一个碎片view，可以从回收的view中获取，也可能新构造一个

            self.addView(scrap);
            self.measureChildWithMargins(scrap, 0, 0);  // 计算此碎片view包含边距的尺寸

            int width = self.getDecoratedMeasuredWidth(scrap);  // 获取此碎片view包含边距和装饰的宽度width
            int height = self.getDecoratedMeasuredHeight(scrap); // 获取此碎片view包含边距和装饰的高度height

            Rect frame = self.getState().itemsFrames.get(i); // 若先前生成过Rect，重复使用
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(offsetX, offsetY, offsetX + width, offsetY + height);
            self.getState().itemsFrames.put(i, frame); // 记录每个item的frame
            self.getState().itemsAttached.put(i, false); // 因为先前已经回收了所有item，此处将item显示标识置否

            self.detachAndScrapView(scrap, recycler); // 回收本次计算的碎片view

            offsetX += width;
            offsetY += height;

            self.getState().contentWidth += width;
            self.getState().contentHeight += height;
        }

        self.getState().contentWidth = Math.max(self.getState().contentWidth, self.getHorizontalSpace()); // 内容宽度最小为RecyclerView容器宽度
        self.getState().contentHeight = Math.max(self.getState().contentHeight, self.getVerticalSpace()); // 内容高度最小为RecyclerView容器高度

        // 依照内容宽高调整记录的滑动距离
        if (self.getState().contentWidth == self.getHorizontalSpace()) {
            self.getState().scrolledX = 0;
        }
        if (self.getState().scrolledX > (self.getState().contentWidth - self.getHorizontalSpace())) {
            self.getState().scrolledX = self.getState().contentWidth - self.getHorizontalSpace();
        }
        if (self.getState().contentHeight == self.getVerticalSpace()) {
            self.getState().scrolledY = 0;
        }
        if (self.getState().scrolledY > (self.getState().contentHeight - self.getVerticalSpace())) {
            self.getState().scrolledY = self.getState().contentHeight - self.getVerticalSpace();
        }

        self.layoutItems(recycler, state); // 放置当前scroll offset处要展示的item
    }

    /** @see RecyclerView.LayoutManager#canScrollHorizontally() */
    @Override
    public boolean canScrollHorizontally() {
        return self.getState().canScrollHorizontal;
    }

    /** @see android.support.v7.widget.RecyclerView.LayoutManager#scrollHorizontallyBy(int, RecyclerView.Recycler, RecyclerView.State) */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int willScroll = dx;
        /**
         * 限制滑动距离的最小值和最大值
         */
        if (self.getState().scrolledX + dx < 0) {
            willScroll = -self.getState().scrolledX;
        }
        else if (self.getState().scrolledX + dx > self.getState().contentWidth - self.getHorizontalSpace()) {
            willScroll = self.getState().contentWidth - self.getHorizontalSpace() - self.getState().scrolledX;
        }

        // 如果将要滑动的距离为0，返回-dx以显示边缘光晕
        if (willScroll == 0) {
            return -dx;
        }

        self.getState().scrolledX += willScroll;

        // 平移容器内的item
        self.offsetChildrenHorizontal(-willScroll);
        // 移除屏幕外的item，添加当前可显示的新item
        self.layoutItems(recycler, state);

        return willScroll;
    }

    /** @see RecyclerView.LayoutManager#canScrollVertically() */
    @Override
    public boolean canScrollVertically() {
        return self.getState().canScrollVertical;
    }

    /** @see android.support.v7.widget.RecyclerView.LayoutManager#scrollVerticallyBy(int, RecyclerView.Recycler, RecyclerView.State) */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int willScroll = dy;

        /**
         * 限制滑动距离的最小值和最大值
         */
        if (self.getState().scrolledY + dy < 0) {
            willScroll = -self.getState().scrolledY;
        }
        else if (self.getState().scrolledY + dy > self.getState().contentHeight - self.getVerticalSpace()) {
            willScroll = self.getState().contentHeight - self.getVerticalSpace() - self.getState().scrolledY;
        }

        // 如果将要滑动的距离为0，返回-dy以显示边缘光晕
        if (willScroll == 0) {
            return -dy;
        }

        self.getState().scrolledY += willScroll;

        // 平移容器内的item
        self.offsetChildrenVertical(-willScroll);
        // 移除屏幕外的item，添加当前可显示的新item
        self.layoutItems(recycler, state);

        return willScroll;
    }

    /** @see RecyclerView.LayoutManager#generateDefaultLayoutParams() */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 摆放当前状态下要展示的item
     * @param recycler         Recycler to use for fetching potentially cached views for a
     *                         position
     * @param state            Transient state of RecyclerView
     */
    private void layoutItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画
            return;
        }

        // 当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(self.getState().scrolledX, self.getState().scrolledY, self.getState().scrolledX + self.getHorizontalSpace(), self.getState().scrolledY + self.getVerticalSpace());

        /**
         * 移除已显示的但在当前scroll offset状态下处于屏幕外的item
         */
        Rect childFrame = new Rect();
        for (int i = 0; i < self.getChildCount(); i++) {
            View child = self.getChildAt(i);
            childFrame.left = self.getDecoratedLeft(child);
            childFrame.top = self.getDecoratedTop(child);
            childFrame.right = self.getDecoratedRight(child);
            childFrame.bottom = self.getDecoratedBottom(child);

            if (!Rect.intersects(displayFrame, childFrame)) {
                self.getState().itemsAttached.put(self.getPosition(child), false);
                self.removeAndRecycleView(child, recycler);
            }
        }

        /**
         * 摆放需要显示的item
         * 由于RecyclerView实际上并没有scroll，也就是说RecyclerView容器的滑动效果是依赖于LayoutManager对item进行平移来实现的
         * 故在放置item时要将item的计算位置平移到实际位置
         */
        for (int i = 0; i < self.getItemCount(); i++) {
            if (Rect.intersects(displayFrame, self.getState().itemsFrames.get(i))) {
                /**
                 * 在onLayoutChildren时由于移除了所有的item view，可以遍历全部item进行添加
                 * 在scroll时就不同了，由于scroll时会先将已显示的item view进行平移，然后移除屏幕外的item view，此时仍然在屏幕内显示的item view就无需再次添加了
                 */
                if (!self.getState().itemsAttached.get(i)) {
                    View scrap = recycler.getViewForPosition(i);
                    self.measureChildWithMargins(scrap, 0, 0);
                    self.addView(scrap);

                    Rect frame = self.getState().itemsFrames.get(i);
                    self.layoutDecorated(scrap,
                            frame.left - self.getState().scrolledX,
                            frame.top - self.getState().scrolledY,
                            frame.right - self.getState().scrolledX,
                            frame.bottom - self.getState().scrolledY); // Important！布局到RecyclerView容器中，所有的计算都是为了得出任意position的item的边界来布局

                    self.getState().itemsAttached.put(i, true);
                }
            }
        }
    }


    /**
     * 记录当前LayoutManager的一些信息
     */
    private State state;
    public State getState() {
        if (state == null) {
            state = new State();
        }
        return state;
    }
    class State {
        /**
         * 存放所有item的位置和尺寸
         */
        SparseArray<Rect> itemsFrames;

        /**
         * 记录item是否已经展示
         */
        SparseBooleanArray itemsAttached;

        /**
         * 横向滑动距离
         * @see #scrollHorizontallyBy(int, RecyclerView.Recycler, RecyclerView.State)
         */
        int scrolledX;

        /**
         * 纵向滑动距离
         * @see #scrollVerticallyBy(int, RecyclerView.Recycler, RecyclerView.State)
         */
        int scrolledY;

        /**
         * 内容宽度
         * note：最小宽度为容器宽度
         */
        int contentWidth;

        /**
         * 内容高度
         * note：最小高度为容器高度
         */
        int contentHeight;

        /**
         * 是否允许横向滑动
         * 默认允许
         */
        boolean canScrollHorizontal;

        /**
         * 是否允许纵向滑动
         * 默认允许
         */
        boolean canScrollVertical;

        public State() {
            itemsFrames = new SparseArray<>();
            itemsAttached = new SparseBooleanArray();
            scrolledX = 0;
            scrolledY = 0;
            contentWidth = 0;
            contentHeight = 0;
            canScrollHorizontal = true;
            canScrollVertical = true;
        }
    }

    /**
     * 启用/禁用横向滑动
     * @param canScrollHorizontal 启用/禁用
     * @return {@link State#canScrollHorizontal}
     */
    public CustomLayoutManager setCanScrollHorizontal(boolean canScrollHorizontal) {
        self.getState().canScrollHorizontal = canScrollHorizontal;
        return this;
    }
    public boolean getCanScrollHorizontal() {
        return self.getState().canScrollHorizontal;
    }

    /**
     * 启用/禁用纵向滑动
     * @param canScrollVertical 启用/禁用
     * @return {@link State#canScrollVertical}
     */
    public CustomLayoutManager setCanScrollVertical(boolean canScrollVertical) {
        self.getState().canScrollVertical = canScrollVertical;
        return this;
    }
    public boolean getCanScrollVertical() {
        return self.getState().canScrollVertical;
    }

    /**
     * 容器去除padding后的宽度
     * @return 实际可摆放item的空间
     */
    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 容器去除padding后的高度
     * @return 实际可摆放item的空间
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
}