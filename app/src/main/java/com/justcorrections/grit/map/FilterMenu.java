package com.justcorrections.grit.map;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
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
    private FrameLayout menuWrapper;
    private CardView menu;
    private LinearLayout menuItems;

    private OnFilterUpdatedListener onFilterChangedListener;

    public FilterMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = inflate(getContext(), R.layout.filter_menu, this);

        background = view.findViewById(R.id.background);
        openButton = view.findViewById(R.id.open_button);
        menuWrapper = view.findViewById(R.id.menu_wrapper);
        menu = view.findViewById(R.id.menu);
        menuItems = view.findViewById(R.id.menu_items);

        background.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                background.setClickable(false);
                closeMenu();
            }
        });
        background.setClickable(false);
        openButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                background.setClickable(true);
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
            menuItems.addView(item);
        }
    }

    public void setOnFilterChangedListener(OnFilterUpdatedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }

    private void openMenu() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int[] menuCoords = {0, 0};
            menu.getLocationOnScreen(menuCoords);
            int menuX = menuCoords[0];
            int menuY = menuCoords[1];
            int menuCenterX = menuX + (menu.getWidth() / 2);
            int menuCenterY = menuY + (menu.getHeight() / 2);

            int[] openButtonCoords = {0, 0};
            openButton.getLocationOnScreen(openButtonCoords);
            int openButtonX = openButtonCoords[0];
            int openButtonY = openButtonCoords[1];
            int openButtonCenterX = openButtonX + (openButton.getWidth() / 2);
            int openButtonCenterY = openButtonY + (openButton.getHeight() / 2);

            int x = (menu.getWidth() / 2);
            int y = (menu.getHeight() / 2) + (openButtonCenterY - menuCenterY);

            int startRadius = openButton.getWidth() / 2;
            int endRadius = (int) Math.hypot(menu.getWidth(), menu.getHeight());
            System.out.println("Ian " + startRadius + " " + endRadius + " " + openButton.getWidth() + " " + openButton.getHeight());

            Animator animator = null;
            animator = ViewAnimationUtils.createCircularReveal(menuWrapper, x, y, startRadius, endRadius);
            openButton.animate().translationXBy(menuCenterX - openButtonCenterX).setDuration(250).start();
            animator.setDuration(500);

            animator.setStartDelay(250);

            ObjectAnimator a = ObjectAnimator
                    .ofPropertyValuesHolder(openButton.getDrawable(),
                            PropertyValuesHolder.ofInt("alpha", 255, 0));
            a.setTarget(openButton.getDrawable());
            a.setDuration(20);
            a.start();

            animator.addListener(new AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    openButton.setVisibility(INVISIBLE);
                    menuWrapper.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            animator.start();
        }
    }

    private void closeMenu() {
        menuWrapper.setVisibility(INVISIBLE);
        openButton.setVisibility(VISIBLE);
        openButton.animate().translationX(0).setDuration(200).start();
    }

    public interface OnFilterUpdatedListener {
        void onFilterUpdated(String title, boolean checked);
    }

}
