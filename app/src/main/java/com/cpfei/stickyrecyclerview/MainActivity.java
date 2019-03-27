package com.cpfei.stickyrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpfei.stickyrecyclerview.nested.NestedScrollViewActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewGroup stickyView;
    private TextView stickyTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stickyView = findViewById(R.id.stickyTitle);
        stickyTitleText = findViewById(R.id.stickyTitleText);

        final List<DataBean> datas = StickyFactory.getInstance().createDatas();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stickyRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        StickyAdapter stickyAdapter = new StickyAdapter(this);
        recyclerView.setAdapter(stickyAdapter);
        stickyAdapter.setDatas(datas);

        StickyOnScrollListener stickyOnScrollListener = new StickyOnScrollListener(this);
        stickyOnScrollListener.setViewSticky(stickyView);
        stickyOnScrollListener.setListener(new StickyOnScrollListener.OnChangeStickyStatusListener() {
            @Override
            public void onStickyStatus(int position) {
                int i = position;
                for (; i >= 0; i--) {
                    DataBean dataBean = datas.get(i);
                    if (dataBean != null) {
                        if (dataBean.getType() == DataBean.TYPE_GROUP) {
                            break;
                        }
                    }
                }
                if (i >= 0) {
                    stickyView.setVisibility(View.VISIBLE);
                    DataBean dataBean = datas.get(i);
                    stickyTitleText.setText(dataBean.getTitle());
                } else {
                    stickyView.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.addOnScrollListener(stickyOnScrollListener);

//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(recyclerView);

//        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(recyclerView);


        findViewById(R.id.nestedScrollView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NestedScrollViewActivity.class);
                startActivity(intent);
            }
        });

    }
}
