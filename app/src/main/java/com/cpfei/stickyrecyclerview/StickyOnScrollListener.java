package com.cpfei.stickyrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by cpfei on 2019/3/21
 * Description:
 */
public class StickyOnScrollListener extends RecyclerView.OnScrollListener {

    private View view;
    private ViewGroup viewSticky;
    private OnChangeStickyStatusListener listener;
    private int coverHeight;

    public StickyOnScrollListener(Context context) {
        coverHeight = dip2px(context, 250);
    }

    public void setViewSticky(ViewGroup viewSticky, View title) {
        this.viewSticky = viewSticky;
        this.view = title;
    }

    public void setListener(OnChangeStickyStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            float alpha = 255;
            if (linearLayoutManager.findFirstVisibleItemPosition() == 0) { // 在顶部滚动时，设置标题栏的颜色渐变，同时设置封面图的margin来代替应有的滚动效果
                View child = linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstVisibleItemPosition());
                if (null == child) {
                    view.setAlpha(1);

//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ntCover.getLayoutParams();
//                    layoutParams.topMargin = 0;
//                    ntCover.setLayoutParams(layoutParams);
                } else {
                    alpha = Math.abs(child.getTop());
                    alpha = alpha * 255 / (coverHeight - view.getHeight());
                    view.setAlpha(Math.min(alpha, 255) / 255);

//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ntCover.getLayoutParams();
//                    layoutParams.topMargin -= dy;
//                    layoutParams.topMargin = Math.min(layoutParams.topMargin, 0);
//                    ntCover.setLayoutParams(layoutParams);
                }
            } else {
                view.setAlpha(1);
            }

            int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();// 获取第一个完全可见的item position
            View child = null;
            if (position >= 0) {
                child = linearLayoutManager.findViewByPosition(position);
            }
            if (child == null) {
                return;
            }
            int itemViewType = linearLayoutManager.getItemViewType(child);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewSticky.getLayoutParams();
            if (itemViewType == DataBean.TYPE_GROUP) {
                int margin = child.getTop();
                margin -= viewSticky.getHeight();
                if (margin < - viewSticky.getHeight()) margin = 0;
                params.topMargin = Math.min(margin, 0);
                viewSticky.setLayoutParams(params);
            } else {
                params.topMargin = 0;
                viewSticky.setLayoutParams(params);
            }

            position--;
            if (position < 0) {
                position = 0;
            }
            if (listener != null) {
                listener.onStickyStatus(position);
            }
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnChangeStickyStatusListener {
        void onStickyStatus(int position);
    }


}
