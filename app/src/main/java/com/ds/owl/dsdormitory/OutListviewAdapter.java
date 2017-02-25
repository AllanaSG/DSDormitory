package com.ds.owl.dsdormitory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Seonggyeong on 2017-02-24.
 */

public class OutListviewAdapter extends BaseAdapter {

    private ArrayList<OutListview> listViewItemList = new ArrayList<OutListview>() ;

    public OutListviewAdapter() {

    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_out,parent,false);
        }

        TextView day = (TextView)convertView.findViewById(R.id.apply_day);
        TextView term = (TextView)convertView.findViewById(R.id.out_term);
        TextView destination = (TextView)convertView.findViewById(R.id.out_destination);
        TextView reason = (TextView)convertView.findViewById(R.id.out_reason);

        OutListview outListview = listViewItemList.get(position);

        day.setText(outListview.getDay());
        term.setText(outListview.getTerm());
        destination.setText(outListview.getDestination());
        reason.setText(outListview.getReason());

        return convertView;
    }

    public void addItem(String day, String term, String destination, String reason) {
        OutListview item = new OutListview();

        item.setDay(day);
        item.setTerm(term);
        item.setDestination(destination);
        item.setReason(reason);

        listViewItemList.add(item);
    }
}
