package com.cpfei.stickyrecyclerview;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpfei on 2019/3/21
 * Description:
 */
public class StickyFactory {
    private static StickyFactory mInstance;

    private StickyFactory() {
    }
    public static StickyFactory getInstance() {
        if (mInstance == null) {
            synchronized (StickyFactory.class) {
                if (mInstance == null) {
                    mInstance = new StickyFactory();
                }
            }
        }
        return mInstance;
    }

    public List<DataBean> createDatas() {
        try {
            InputStreamReader isr = new InputStreamReader(StictyApplitacion.mContext.getAssets().open("citylist.json"),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = br.readLine()) != null){
                builder.append(line);
            }
            br.close();
            isr.close();
            String jsonStr = builder.toString();
            Log.d("Tag", jsonStr);
            ArrayList<DataBean> dataBeans = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("city");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                DataBean dataBean = new DataBean();
                dataBean.setTitle(object.getString("title"));
                dataBean.setType(DataBean.TYPE_GROUP);
                dataBeans.add(dataBean);

                JSONArray lists = object.getJSONArray("lists");
                for (int k = 0; k < lists.length(); k++) {
                    DataBean dataBean1 = new DataBean();
                    dataBean1.setTitle(lists.optString(k));
                    dataBean1.setType(DataBean.TYPE_SUB);
                    dataBeans.add(dataBean1);
                }
            }
            return dataBeans;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
