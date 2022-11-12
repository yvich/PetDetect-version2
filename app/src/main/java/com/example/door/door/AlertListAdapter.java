package com.example.door;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlertListAdapter extends BaseAdapter {
    Context context;
    String doorname[];
    String doortimeStr[];
    int flags[];
    LayoutInflater inflter;

    public AlertListAdapter(Context applicationContext, String[] doorname,String[] doortimeStr, int[] flags) {
        this.context = context;
        this.doorname = doorname;
        this.doortimeStr = doortimeStr;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return doorname.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.alertlistviewitem, null);
        TextView doorTxt = (TextView) view.findViewById(R.id.doornameTxt);
        TextView doortimeTxt = (TextView) view.findViewById(R.id.doortimeTxt);
        ImageView icon = (ImageView) view.findViewById(R.id.doorImg);
        doorTxt.setText(doorname[i]);
        doortimeTxt.setText(doortimeStr[i]);
        icon.setImageResource(flags[i]);
        return view;
    }
}
