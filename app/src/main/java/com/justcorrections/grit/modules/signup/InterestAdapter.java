package com.justcorrections.grit.modules.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.justcorrections.grit.R;


public class InterestAdapter extends BaseAdapter {

    private Context context;
    private String[] data;
    private LayoutInflater inflater;
    private boolean[] checked;

    public InterestAdapter(Context context, String[] data, boolean[] checked) {
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.checked = checked;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = inflater.inflate(R.layout.interest_view, viewGroup, false);

        final int index = i;

        TextView tv = rowView.findViewById(R.id.interest_tv);
        tv.setText(data[i]);

        CheckBox cb = rowView.findViewById(R.id.interest_cb);
        cb.setChecked(checked[i]);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked[index] = b;
            }
        });

        return rowView;
    }
}
