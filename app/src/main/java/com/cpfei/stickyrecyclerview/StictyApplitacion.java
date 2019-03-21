package com.cpfei.stickyrecyclerview;

import android.app.Application;
import android.content.Context;

/**
 * Created by cpfei on 2019/3/21
 * Description:
 */
public class StictyApplitacion extends Application{

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

}
