package com.justcorrections.grit.map;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.justcorrections.grit.R;
import com.justcorrections.grit.map.SlidingFloatingActionButton.AnimationFinishedListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // FAB is the floating action button represented by filterMenuOpenButton
    private static final int FAB_OPEN_OFFSET = 200; // in pixels
    private static final int FAB_ANIMATION_DURATION = 175; // in millis

    private static final int FILTER_MENU_OPEN_DELAY = FAB_ANIMATION_DURATION; // in millis
    private static final int FILTER_MENU_OPEN_DURATION = 375 / 2; // in millis
    private static final int FILTER_MENU_HIDE_DURATION = 200; // in milliis
    private static final int FILTER_MENU_HIDE_OFFSET = 200; // in pixels

    private SlidingFloatingActionButton filterMenuOpenButton;
    private CardView filterMenu;
    private FrameLayout backgroundFrame;
    private LinearLayout ll;

    private int filterMenuOpenButton_StartingX;
    private int filterMenu_StartingY;

    private boolean isFilterMenuShowing = false;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        filterMenuOpenButton = view.findViewById(R.id.filter_menu_open_button);
        filterMenuOpenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterMenu();
            }
        });
        filterMenuOpenButton_StartingX = filterMenuOpenButton.getRight();

        final CheckBox check = view.findViewById(R.id.check);
        ll = view.findViewById(R.id.ll);
        ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                check.setChecked(!check.isChecked());
            }
        });

        filterMenu = view.findViewById(R.id.filter_menu);
        filterMenu.setClickable(true);
        filterMenu_StartingY = filterMenu.getTop();

        backgroundFrame = view.findViewById(R.id.background_frame);
        backgroundFrame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFilterMenu();
            }
        });


        return view;
    }

    public void showFilterMenu() {
        if (isFilterMenuShowing)
            return;

        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP && 1 == 1) {

            int x = filterMenu.getLeft() + (filterMenu.getWidth() / 2); // middle of view horizontally
            int y = filterMenu.getBottom() - (filterMenuOpenButton.getHeight() / 2); // middle of view vertically
            int startRadius = 0;
            int endRadius = (int) Math.hypot(filterMenu.getWidth(), filterMenu.getHeight()); // diagonal length of view
            final Animator openFilterMenu = ViewAnimationUtils.createCircularReveal(backgroundFrame, x, y, startRadius, endRadius);

            int toX = filterMenuOpenButton_StartingX - FAB_OPEN_OFFSET;
            filterMenuOpenButton.slideTo(toX, FAB_ANIMATION_DURATION, new AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    backgroundFrame.setVisibility(View.VISIBLE);
                    filterMenuOpenButton.setVisibility(View.GONE);
                    openFilterMenu.start();
                }
            });

        } else

        {
            final int initialHeight = 0;
            filterMenu.getLayoutParams().height = 0;
            filterMenu.requestLayout();

            filterMenu.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            int targetHeight = filterMenu.getMeasuredHeight();

            System.out.println("Ian " + targetHeight);

            backgroundFrame.setVisibility(View.VISIBLE);

            final int distanceToExpand = targetHeight - initialHeight;

            final Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        // Do this after expanded
                    }

                    filterMenu.getLayoutParams().height = (int) (initialHeight + (distanceToExpand * interpolatedTime));
                    filterMenu.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            a.setDuration(FILTER_MENU_OPEN_DURATION);
            int toX = filterMenuOpenButton_StartingX - FAB_OPEN_OFFSET;
            ViewPropertyAnimator translateFab = filterMenuOpenButton.animate().translationX(toX).setDuration(FAB_ANIMATION_DURATION);

            translateFab.setListener(new AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    backgroundFrame.setVisibility(View.VISIBLE);
                    filterMenuOpenButton.setVisibility(View.GONE);
                    filterMenu.startAnimation(a);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            translateFab.start();
        }

        isFilterMenuShowing = true;


    }

    public void hideFilterMenu() {
        if (!isFilterMenuShowing)
            return;

        filterMenuOpenButton.slideBack(FAB_ANIMATION_DURATION);
        backgroundFrame.animate().alpha(0f).setDuration(FILTER_MENU_HIDE_DURATION).translationY(FILTER_MENU_HIDE_OFFSET).setListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                backgroundFrame.setVisibility(View.INVISIBLE);
                backgroundFrame.setAlpha(1f);
                backgroundFrame.setY(backgroundFrame.getY() - FILTER_MENU_HIDE_OFFSET);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        isFilterMenuShowing = false;
    }
}
