package com.justcorrections.grit.map;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justcorrections.grit.R;

/**
 * Created by ianwillis on 10/16/17.
 */

public class FilterMenuItem extends LinearLayout {

    private TextView title;
    private CheckBox checkBox;

    private OnChangeListener onChangeListener;

    public FilterMenuItem(Context context, String title, boolean checked) {
        this(context);
        this.title.setText(title);
        this.checkBox.setChecked(checked);
    }

    public FilterMenuItem(Context context) {
        super(context);

        View view = inflate(getContext(), R.layout.filter_menu_item, this);

        title = view.findViewById(R.id.menu_item_title);
        checkBox = view.findViewById(R.id.menu_item_check_box);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(!checkBox.isChecked());
                onChangeListener.onChange(FilterMenuItem.this);
            }
        });
        checkBox.setChecked(true);
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public interface OnChangeListener {

        void onChange(FilterMenuItem item);
    }
}
