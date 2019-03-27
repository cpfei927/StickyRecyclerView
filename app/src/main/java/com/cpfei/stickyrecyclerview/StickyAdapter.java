package com.cpfei.stickyrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cpfei.stickyrecyclerview.nested.SimpleTestAdapter;

import java.util.List;

/**
 * Created by cpfei on 2019/3/21
 * Description:
 */
public class StickyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataBean> datas;

    public StickyAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<DataBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addHeaderView(View view) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header_view, null));
        } else if (viewType == DataBean.TYPE_GROUP) {
            return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_view, null));
        } else {
            return new SubViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sub_view, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof HeaderViewHolder) {

        } else if (viewHolder instanceof GroupViewHolder) {
            GroupViewHolder holder = (GroupViewHolder) viewHolder;
            holder.text.setText(datas.get(i).getTitle());
        } else if(viewHolder instanceof SubViewHolder) {
            SubViewHolder holder = (SubViewHolder) viewHolder;
            holder.text.setText(datas.get(i).getTitle());

            SimpleTestAdapter simpleTestAdapter = new SimpleTestAdapter();
            holder.recyclerViewItem.setAdapter(simpleTestAdapter);


        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 0;// header view
//        }
        DataBean dataBean = datas.get(position);
        if (dataBean.getType() == DataBean.TYPE_GROUP) {
            return DataBean.TYPE_GROUP;
        } else {
            return DataBean.TYPE_SUB;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.itemGroupTitleText);
        }
    }

    class SubViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        private RecyclerView recyclerViewItem;
        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.itemSubTitleText);
            recyclerViewItem = itemView.findViewById(R.id.recyclerViewItem);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            recyclerViewItem.setLayoutManager(gridLayoutManager);

//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            recyclerViewItem.setLayoutManager(linearLayoutManager);

            recyclerViewItem.setNestedScrollingEnabled(false);
        }
    }


}
