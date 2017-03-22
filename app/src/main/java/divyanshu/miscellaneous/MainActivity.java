package divyanshu.miscellaneous;

import android.animation.Animator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl_background;
    RelativeLayout rl_foreground;

    int colorIndex;
    int[] colorArray;

    MotionEvent touchEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        rl_foreground = (RelativeLayout) findViewById(R.id.rl_foreground);

        colorIndex = 0;

        colorArray = new int[]{
                Color.parseColor("#FF0000"),
                Color.parseColor("#00FF00"),
                Color.parseColor("#FF00FF"),
                Color.parseColor("#FFFF00")};

        rl_background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                touchEvent = motionEvent;
                return false;
            }
        });

        rl_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackgroundColor((int) touchEvent.getX(), (int) touchEvent.getY());
            }
        });
    }


    public void changeBackgroundColor(int cx, int cy) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            int finalRadius = (int) Math.hypot(rl_background.getWidth(), rl_background.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(rl_foreground, cx, cy, 0, finalRadius);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rl_background.setBackgroundColor(colorArray[colorIndex % 4]);
                    rl_foreground.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            rl_foreground.setBackgroundColor(colorArray[++colorIndex % 4]);
            rl_foreground.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

}
