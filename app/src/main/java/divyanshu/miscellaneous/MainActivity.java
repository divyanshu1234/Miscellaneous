package divyanshu.miscellaneous;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl_background;
    RelativeLayout rl_foreground;
    FlowLayout fl_activity_main;

    List<View> flChildViews;

    int colorIndex;
    int[] colorArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_main);

        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        rl_foreground = (RelativeLayout) findViewById(R.id.rl_foreground);
        fl_activity_main = (FlowLayout) findViewById(R.id.fl_activity_main);

        initializeBackground();
    }

    private void initializeBackground() {
        colorIndex = 0;

        colorArray = new int[]{
                Color.parseColor("#0588FA"),
                Color.parseColor("#00CBFB"),
                Color.parseColor("#0087FB"),
                Color.parseColor("#FCBA04"),
                Color.parseColor("#FC5510"),
                Color.parseColor("#DC0F65")
        };

        rl_background.setBackgroundColor(colorArray[0]);
        rl_foreground.setBackgroundColor(colorArray[0]);

        flChildViews = new ArrayList<>();
        for (int i = 0; i < fl_activity_main.getChildCount(); ++i){
            flChildViews.add(fl_activity_main.getChildAt(i));
        }
    }


    public void buttonClick(View view) {
        int[] location = new  int[2];
        view.getLocationInWindow(location);

        int cx = location[0] + view.getWidth() / 2;
        int cy = location[1] + view.getHeight() / 2;
        changeBackgroundColor(cx, cy);

        view.setSelected(!view.isSelected());

        for (View child : flChildViews){
            if (child.isSelected()){
                ((TextView) child).setTextColor(colorArray[colorIndex % colorArray.length]);
                child.setBackgroundResource(R.drawable.rounded_corner_pressed);
            }
            else{
                ((TextView) child).setTextColor(Color.parseColor("#FFFFFF"));
                child.setBackgroundResource(R.drawable.rounded_corner_unpressed);
            }
        }
    }

    public void changeBackgroundColor(int cx, int cy) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            int finalRadius = (int) Math.hypot(rl_background.getWidth(), rl_background.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(rl_foreground, cx, cy, 0, finalRadius);
            anim.setDuration(300);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    rl_background.setBackgroundColor(colorArray[colorIndex % colorArray.length]);
                    rl_foreground.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });

            rl_foreground.setBackgroundColor(colorArray[++colorIndex % colorArray.length]);
            rl_foreground.setVisibility(View.VISIBLE);
            anim.start();
        }
    }
}
