package com.example.shadow.sortlistviewitem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shadow on 2016/3/4.
 */
public class SessionItemAdapter extends ArrayAdapter<Session> {

    Context mContext;

    /**
     * 不建议使用这种方式 将数据与adapter进行绑定，如果要进行数据更新等操作
     * 因为数据引用是相同的情况，会同步影响数据的变更。例如使用clean（）方法消除数据
     * 不仅仅消除了adapter里面的数据，还会清除了相同内存地址的数据源
     */
    public SessionItemAdapter(Context context, List<Session> sessions) {
        super(context, 0, sessions);
        mContext = context;
    }

    public SessionItemAdapter(Context context) {
        super(context, 0, new ArrayList<Session>());
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ItemView(mContext);
        }
        ItemView itemView = (ItemView) convertView;
        itemView.setText(String.valueOf(getItem(position).getTop()));
        itemView.setAvatar(getItem(position).getAvatar());
        if (getItem(position).getTop() == 1) {
            itemView.setBackgroundResource(R.drawable.bg_top_item_selector);
        } else {
            itemView.setBackgroundResource(R.drawable.bg_item_selector);
        }

        return convertView;
    }

    public void updateData(List<Session> sessionList) {
        clear();
        addAll(sessionList);
    }

}
