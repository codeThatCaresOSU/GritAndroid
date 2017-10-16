package com.justcorrections.grit.map;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ianwillis on 10/8/17.
 */

public class SlidingFloatingActionButton extends FloatingActionButton {

    public static final long DURATION = 175; // in millis

    // parent of this view used to capture click events
    private FrameLayout backgroundFrame;

    public SlidingFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBackgroundFrame(FrameLayout backgroundFrame) {
        this.backgroundFrame = backgroundFrame;
    }

    public void slideTo(View view, final SlidingFloatingActionButton.AnimationFinishedListener listener) {
        int viewX = view.getLeft() + (view.getWidth() / 2); // middle of view horizontally
        int toX = viewX - this.getLeft();
        System.out.println("Ian " + toX + " " + viewX + " " + this.getLeft());
        this.slide(toX, DURATION, listener);
    }

    public void slideBack() {
        this.setVisibility(VISIBLE);
        slide(0, DURATION, null);
    }

    private void slide(long x, long duration, final SlidingFloatingActionButton.AnimationFinishedListener listener) {
        this.animate().translationX(x).setDuration(duration).setListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null)
                    listener.onAnimationFinished();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    public interface AnimationFinishedListener {
        void onAnimationFinished();
    }
}
