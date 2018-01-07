package com.nonsense.planttracker.tracker.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nonsense.planttracker.R;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Derek Brooks on 7/1/2017.
 */

public class DataPointTileArrayAdapter extends ArrayAdapter<String> {

    private int viewResourceId;
    private TreeMap<String, Object> dataPoints;

    public DataPointTileArrayAdapter(Context context, int textViewResourceId,
                                     TreeMap<String, Object> dataPoints) {
        super(context, textViewResourceId, new ArrayList<String>(dataPoints.keySet()));
        viewResourceId = textViewResourceId;
        this.dataPoints = dataPoints;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(viewResourceId, null);
        }

        String p = getItem(position);

        Object o = dataPoints.get(p);

        if (o != null) {
            TextView firstLine = (TextView)v.findViewById(R.id.observEventTypeTextView);
            firstLine.setText(p);


            TextView secondLine = (TextView)v.findViewById(R.id.recordableSummaryTextView);
            //FIXME use a different tile layout instead of doing this
            firstLine.setBackground(secondLine.getBackground());


            String valueType = "";
            if (o instanceof String) {
                valueType = "Text";
            }
            else if (o instanceof Integer)   {
                valueType = "Integer";
            }
            else if (o instanceof Double)    {
                valueType = "Decimal";
            }

            secondLine.setText("Value type: " + valueType + ". Default value: " +
                    o.toString() );
        }

        return v;
    }
}