package com.vilyever.howtocustomlayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * DataAdapter
 * HowToCustomLayoutManager <com.vilyever.howtocustomlayoutmanager>
 * Created by vilyever on 2016/1/7.
 * Feature:
 * 示例适配器
 * 通过绑定数据列表向绑定的ViewHolder提供内容
 */
public class DataAdapter extends RecyclerView.Adapter<DemoViewHolder> implements DemoViewHolder.Datasource, DemoViewHolder.Delegate {
    /** Convenience Var to call this */
    final DataAdapter self = this;

    /** @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int) **/
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DemoViewHolder.newInstance(parent);
    }

    /** @see RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)  **/
    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        holder.itemView.getLayoutParams().width = self.getDemoModels().get(position).getPreferWidth();
        holder.itemView.getLayoutParams().height = self.getDemoModels().get(position).getPreferHeight();
        ((CustomLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).occupationLineBlocks = self.getDemoModels().get(position).getBlocks();
        holder.setDelegate(self);
        holder.reload(self);
    }

    /** @see RecyclerView.Adapter#getItemCount() **/
    @Override
    public int getItemCount() {
        return self.getDemoModels().size();
    }

    /** {@link com.vilyever.howtocustomlayoutmanager.DemoViewHolder.Datasource} **/
    @Override
    public String gainTitle(DemoViewHolder viewHolder) {
        return self.getDemoModels().get(viewHolder.getAdapterPosition()).getTitle();
    }
    @Override
    public int gainBackgroundColor(DemoViewHolder viewHolder) {
        return self.getDemoModels().get(viewHolder.getAdapterPosition()).getColor();
    }
    /** {@link com.vilyever.howtocustomlayoutmanager.DemoViewHolder.Datasource} **/

    /** {@link com.vilyever.howtocustomlayoutmanager.DemoViewHolder.Delegate} **/
    @Override
    public void onClick(DemoViewHolder viewHolder) {
        self.getDelegate().onItemClick(self, viewHolder.getAdapterPosition());
    }
    /** {@link com.vilyever.howtocustomlayoutmanager.DemoViewHolder.Delegate} **/

    private Delegate delegate;
    public DataAdapter setDelegate(Delegate delegate) {
        this.delegate = delegate;
        return this;
    }
    public Delegate getDelegate() {
        if (delegate == null) {
            return NullDelegate;
        }
        return delegate;
    }
    public interface Delegate {
        void onItemClick(DataAdapter adapter, int position);
    }
    private static final Delegate NullDelegate = new Delegate() {
        @Override
        public void onItemClick(DataAdapter adapter, int position) {
        }
    };

    /**
     * 绑定的数据列表
     */
    private List<DemoModel> demoModels;
    public List<DemoModel> getDemoModels() {
        if (demoModels == null) {
            demoModels = new ArrayList<>();
        }
        return demoModels;
    }
    public DataAdapter setDemoModels(List<DemoModel> demoModels) {
        this.demoModels = demoModels;
//        self.notifyDataSetChanged();
        self.notifyItemRangeChanged(0, demoModels.size());
        return self;
    }

    public DataAdapter addModel(int position, DemoModel demoModel) {
        if (position < 0 || position > self.getDemoModels().size()) {
            throw new IndexOutOfBoundsException("position should between " + 0 + "~" + self.getDemoModels().size());
        }
        self.getDemoModels().add(position, demoModel);
        self.notifyItemInserted(position);
        return self;
    }

    public DataAdapter removeModel(int position) {
        if (position < 0 || position > self.getDemoModels().size()) {
            throw new IndexOutOfBoundsException("position should between " + 0 + "~" + (self.getDemoModels().size() - 1));
        }
        self.getDemoModels().remove(position);
        self.notifyItemRemoved(position);
        return self;
    }

    public DataAdapter updateModel(int position, DemoModel demoModel) {
        self.getDemoModels().set(position, demoModel);
        self.notifyItemChanged(position);
        return self;
    }

    public DataAdapter clear() {
        self.getDemoModels().clear();
        self.notifyDataSetChanged();
        return self;
    }

}