package com.cpfei.stickyrecyclerview.design;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.cpfei.stickyrecyclerview.R;

public class DesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(new View(this));
    }
}
