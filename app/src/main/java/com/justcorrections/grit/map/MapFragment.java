package com.justcorrections.grit.map;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private FloatingActionButton filterMenuOpenButton;
    private CardView card;
    private LinearLayout ll;
    private FrameLayout frame;

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

        filterMenuOpenButton = view.findViewById(R.id.filterMenuOpenButton);
        filterMenuOpenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterMenuOpen();
            }
        });

        final CheckBox check = view.findViewById(R.id.check);
        ll = view.findViewById(R.id.ll);
        ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                check.setChecked(!check.isChecked());
            }
        });

        card = view.findViewById(R.id.card);
        frame = view.findViewById(R.id.frame);

        frame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMenu();
            }
        });

        card.setClickable(true);

        return view;
    }

    public void onFilterMenuOpen() {

        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            int x = card.getLeft() + (card.getWidth() / 2);
            int y = card.getBottom() - (filterMenuOpenButton.getHeight() / 2);

            int startRadius = 0;
            int endRadius = (int) Math.hypot(card.getWidth(), card.getHeight());

            TranslateAnimation tAnim = new TranslateAnimation(0, -200, 0, 0);
            tAnim.setDuration(200);
            filterMenuOpenButton.animate().translationXBy(-200).setDuration(200).start();


            Animator animator = ViewAnimationUtils.createCircularReveal(frame, x, y, startRadius, endRadius);
            animator.setDuration(500);


            animator.setStartDelay(175);

            animator.addListener(new AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    frame.setVisibility(View.VISIBLE);
                    filterMenuOpenButton.setVisibility(View.GONE);
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

    public void hideMenu() {
        filterMenuOpenButton.setVisibility(View.VISIBLE);
        filterMenuOpenButton.animate().translationXBy(200).setDuration(200).start();
        frame.animate().alpha(0f).setDuration(200).translationY(200).setListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                frame.setVisibility(View.INVISIBLE);
                frame.setAlpha(1f);
                frame.setY(frame.getY() - 200);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
//            int x = card.getLeft() + (card.getWidth() / 2);
//            int y = card.getBottom();
//
//            int startRadius = 0;
//            int endRadius = (int) Math.hypot(card.getWidth(), card.getHeight());
//
//            TranslateAnimation tAnim = new TranslateAnimation(0, -200, 0, 0);
//            tAnim.setDuration(200);
//            filterMenuOpenButton.animate().translationXBy(-200).setDuration(200).start();
//
//
//            Animator animator = ViewAnimationUtils.
//            animator.setDuration(700);
//
//
//            animator.setStartDelay(200);
//
//            animator.addListener(new AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animator) {
//                    frame.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animator) {
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animator) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animator) {
//                }
//            });
//            animator.start();
        }
    }
}
