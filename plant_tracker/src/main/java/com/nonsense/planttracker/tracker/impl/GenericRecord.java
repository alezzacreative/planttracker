package com.nonsense.planttracker.tracker.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Derek Brooks on 12/23/2017.
 */

public class GenericRecord implements Serializable, Cloneable {

    public String displayName;
    public Calendar time;
    public String notes;
    public TreeMap<String, Object> dataPoints;
    public String summaryTemplate;
    public boolean showNotes;

    public GenericRecord(String displayName)  {
        this.displayName = displayName;

        this.dataPoints = new TreeMap<>();
        this.time = Calendar.getInstance();
    }

    public void setDataPoint(String key, Object value) {
        if (dataPoints.containsKey(key))    {
            dataPoints.remove(key);
        }

        dataPoints.put(key, value);
    }

    public Object getDataPoint(String key)    {
        return dataPoints.get(key);
    }

    public String getSummary()  {
        if (summaryTemplate == null)    {
            return "";
        }

        String buildTemplate = summaryTemplate;
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(summaryTemplate);

        ArrayList<String> placeholders = new ArrayList<>();
        while(m.find()) {
            String ph = m.group();
            if (!placeholders.contains(ph))  {
                placeholders.add(ph);
            }
        }

        String summary = summaryTemplate;
        for(String ph : placeholders)   {
            String key = ph.replace('{', ' ')
                    .replace('}', ' ').trim();

            String regex = ph.replace("{", "\\{")
                    .replace("}", "\\}");

            if (dataPoints.containsKey(key)) {
                summary = summary.replaceAll(regex, dataPoints.get(key).toString());
            }
        }

        if (showNotes && notes != null)  {
            summary += ", Notes: " + notes;
        }

        return summary;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        GenericRecord record = (GenericRecord)super.clone();

        record.dataPoints = new TreeMap<>();
        for(String key : dataPoints.keySet())   {
            Object value = dataPoints.get(key);

            if (value != null)  {
                if( value instanceof String )   {
                    record.setDataPoint(key, new String((String)record.dataPoints.get(key)));
                }
                else if( value instanceof Integer ) {
                    record.setDataPoint(key, Integer.valueOf((Integer)record.dataPoints.get(key)));
                }
                else if( value instanceof Double )    {
                    record.setDataPoint(key, Double.valueOf((Double)record.dataPoints.get(key)));
                }
            }
        }

        return super.clone();
    }
}
