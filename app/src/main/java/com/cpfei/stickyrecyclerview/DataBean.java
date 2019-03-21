package com.cpfei.stickyrecyclerview;

import java.io.Serializable;

/**
 * Created by cpfei on 2019/3/21
 * Description:
 */
public class DataBean implements Serializable {

    public static final int TYPE_GROUP = 1;
    public static final int TYPE_SUB = 2;

    private String title;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
