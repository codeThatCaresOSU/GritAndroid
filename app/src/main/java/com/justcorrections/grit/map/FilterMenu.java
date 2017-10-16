package com.justcorrections.grit.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.justcorrections.grit.R;
import com.justcorrections.grit.map.FilterMenuItem.OnChangeListener;

/**
 * Created by ianwillis on 10/11/17.
 */

public class FilterMenu extends FrameLayout {

    private FrameLayout background;
    private FloatingActionButton openButton;
    private CardView menuWrapper;
    private LinearLayout menu;

    private OnFilterUpdatedListener onFilterChangedListener;

    public FilterMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = inflate(getContext(), R.layout.filter_menu, this);

        background = view.findViewById(R.id.background);
        openButton = view.findViewById(R.id.open_button);
        menuWrapper = view.findViewById(R.id.menu_wrapper);
        menu = view.findViewById(R.id.menu);

        openButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int openButtonVerticalCenter = openButton.getBottom() - (openButton.getHeight() / 2);
                menuWrapper.setY(openButtonVerticalCenter - menuWrapper.getHeight());
                openButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        background.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Ian here2");
            }
        });
        openButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
    }


    public void setItems(String[] titles, boolean[] checked) {
        for (int i = 0; i < titles.length && i < checked.length; i++) {
            final FilterMenuItem item = new FilterMenuItem(getContext(), titles[i], checked[i]);
            item.setOnChangeListener(new OnChangeListener() {
                @Override
                public void onChange(FilterMenuItem items) {
                    onFilterChangedListener.onFilterUpdated(item.getTitle(), item.isChecked());
                }
            });
            menu.addView(item);
        }
    }

    public void setOnFilterChangedListener(OnFilterUpdatedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }

    private void openMenu() {
        System.out.println("Ian here");
    }

    public interface OnFilterUpdatedListener {
        void onFilterUpdated(String title, boolean checked);
    }

}
