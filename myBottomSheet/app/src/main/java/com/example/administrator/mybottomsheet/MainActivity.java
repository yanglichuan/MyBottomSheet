package com.example.administrator.mybottomsheet;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ViewGroup rootView;
    private Button open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (ViewGroup) findViewById(R.id.root);
        open = (Button) findViewById(R.id.open);


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBottomSheet();
            }
        });
    }

    private int getpx(int idp){
       return (int) (getResources().getDisplayMetrics().density*idp +0.5f);
    }


    private boolean bOPen = false;
    private void doButtonAnim(final View target, int fromH, int delay, int duration){
        ObjectAnimator a1 =    ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator a2 =   ObjectAnimator.ofFloat(target, "translationY", fromH, 0);
        final AnimatorSet builder = new  AnimatorSet();
        builder.setDuration(duration);
        builder.playTogether(a1, a2);
        builder.setInterpolator(new OvershootInterpolator(2));
        builder.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                builder.start();
            }
        },delay);
    }

    private void addBottomSheet(){
        if(!bOPen){
            View bottomsheet = View.inflate(this, R.layout.bottomsheet, null);
            bottomsheet.setId(R.id.bottomsheet);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,getpx(300));
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            rootView.addView(bottomsheet, params);

            ObjectAnimator xAnim = ObjectAnimator.ofFloat(bottomsheet, "translationY",
                    getpx(300), 0).setDuration(400);
            xAnim.setStartDelay(0);
            xAnim.setRepeatCount(0);
            xAnim.setInterpolator(new OvershootInterpolator(2));
            xAnim.start();
            bOPen = true;

            Button bt1 = (Button) bottomsheet.findViewById(R.id.bt1);
            Button bt2 = (Button) bottomsheet.findViewById(R.id.bt2);
            Button bt3 = (Button) bottomsheet.findViewById(R.id.bt3);
            Button bt4 = (Button) bottomsheet.findViewById(R.id.bt4);
            Button bt5 = (Button) bottomsheet.findViewById(R.id.bt5);

            doButtonAnim(bt1,getpx(100),100,200);
            doButtonAnim(bt2,getpx(100),200,200);
            doButtonAnim(bt3,getpx(200),300,300);
            doButtonAnim(bt4,getpx(200),400,300);
            doButtonAnim(bt5,getpx(200),400,300);
        }
    }

    private Handler mHandler = new Handler();


    private void delBottomSheet(){
        final View bottomsheet = rootView.findViewById(R.id.bottomsheet);
        if(bottomsheet != null){
            ObjectAnimator xAnim = ObjectAnimator.ofFloat(bottomsheet, "translationY",
                    0,bottomsheet.getMeasuredHeight()).setDuration(400);
            xAnim.setStartDelay(0);
            xAnim.setRepeatCount(0);
            xAnim.setInterpolator(new OvershootInterpolator(2));
            xAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    bOPen = false;
                    rootView.removeView(bottomsheet);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            xAnim.start();
        }

    }

    @Override
    public void onBackPressed() {
        if(bOPen){
            delBottomSheet();
        }else{
            super.onBackPressed();;
        }
    }
}
