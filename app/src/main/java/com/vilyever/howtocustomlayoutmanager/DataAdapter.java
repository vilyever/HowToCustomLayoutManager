package com.vilyever.howtocustomlayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * DataAdapter
 * HowToCustomLayoutManager <com.vilyever.howtocustomlayoutmanager>
 * Created by vilyever on 2016/1/7.
 * Feature:
 * 示例适配器
 * 通过绑定数据列表向绑定的ViewHolder提供内容
 */
public class DataAdapter extends RecyclerView.Adapter<DemoViewHolder> implements DemoViewHolder.Datasource {
    /**
     * this的便捷访问
     */
    final DataAdapter self = this;

    /**
     * 绑定的数据列表
     */
    private List<DemoModel> demoModels;
    public DataAdapter setDemoModels(List<DemoModel> demoModels) {
        this.demoModels = demoModels;
        self.notifyDataSetChanged();
        return self;
    }

    /* #Constructors */
    
    /* #Overrides */
    /** @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int) **/
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DemoViewHolder.newInstance(parent);
    }

    /** @see RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)  **/
    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        holder.reload(self);
    }

    /** @see RecyclerView.Adapter#getItemCount() **/
    @Override
    public int getItemCount() {
        return self.demoModels == null ? 0 : self.demoModels.size();
    }

    /** {@link com.vilyever.howtocustomlayoutmanager.DemoViewHolder.Datasource} **/
    @Override
    public String gainTitle(DemoViewHolder viewHolder) {
        return self.demoModels.get(viewHolder.getAdapterPosition()).getTitle();
    }
    @Override
    public int gainBackgroundColor(DemoViewHolder viewHolder) {
        return self.demoModels.get(viewHolder.getAdapterPosition()).getColor();
    }
    /** {@link com.vilyever.howtocustomlayoutmanager.DemoViewHolder.Datasource} **/
}