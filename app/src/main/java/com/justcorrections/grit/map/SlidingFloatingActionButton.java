package com.justcorrections.grit.map;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Created by ianwillis on 10/8/17.
 */

public class SlidingFloatingActionButton extends FloatingActionButton {


    public SlidingFloatingActionButton(Context context) {
        super(context);
    }

    public SlidingFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void slideTo(float x, int duration, final SlidingFloatingActionButton.AnimationFinishedListener listener) {
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

    public void slideBack(int duration) {
        this.setVisibility(VISIBLE);
        slideTo(0, duration, null);
    }

    public interface AnimationFinishedListener {
        void onAnimationFinished();
    }
}
