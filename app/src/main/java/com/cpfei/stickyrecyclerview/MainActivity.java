package com.cpfei.stickyrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View title;
    private ViewGroup stickyView;
    private TextView stickyTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.titleBraaa);
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
        stickyOnScrollListener.setViewSticky(stickyView, title);
        stickyOnScrollListener.setListener(new StickyOnScrollListener.OnChangeStickyStatusListener() {
            @Override
            public void onStickyStatus(int position) {
                int i = position;
                for (; i > 0; i--) {
                    DataBean dataBean = datas.get(i - 1);
                    if (dataBean != null) {
                        if (dataBean.getType() == DataBean.TYPE_GROUP) {
                            break;
                        }
                    }
                }
                if (i > 0) {
                    stickyView.setVisibility(View.VISIBLE);
                    DataBean dataBean = datas.get(i - 1);
                    stickyTitleText.setText(dataBean.getTitle());
                } else {
                    stickyView.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.addOnScrollListener(stickyOnScrollListener);

    }
}
