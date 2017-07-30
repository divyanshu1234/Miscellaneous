package divyanshu.miscellaneous;

import android.animation.Animator;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl_text_animation;
    RelativeLayout rl_content;
    RelativeLayout rl_background;
    RelativeLayout rl_foreground;
    FlowLayout fl_activity_main;

    TextView tv_almost_done;
    TextView tv_but_first;

    Button b_continue;

    List<String> choiceList;
    List<TextView> flChildTextViews;

    int colorIndex;
    int[] colorArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_main);

        rl_text_animation = (RelativeLayout) findViewById(R.id.rl_text_animation);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        rl_foreground = (RelativeLayout) findViewById(R.id.rl_foreground);
        fl_activity_main = (FlowLayout) findViewById(R.id.fl_activity_main);

        tv_almost_done = (TextView) findViewById(R.id.tv_almost_done);
        tv_but_first = (TextView) findViewById(R.id.tv_but_first);

        b_continue = (Button) findViewById(R.id.b_continue);

        initializeBackground();
        createChoiceList();
        addChildViewsToFl();
        initializeAnimation();
    }

    private void initializeBackground() {
        colorIndex = 0;

        Resources resources = getResources();
        Resources.Theme theme = getTheme();

        colorArray = new int[]{
                ResourcesCompat.getColor(resources, R.color.bg_color_0, theme),
                ResourcesCompat.getColor(resources, R.color.bg_color_1, theme),
                ResourcesCompat.getColor(resources, R.color.bg_color_2, theme),
                ResourcesCompat.getColor(resources, R.color.bg_color_3, theme),
                ResourcesCompat.getColor(resources, R.color.bg_color_4, theme),
                ResourcesCompat.getColor(resources, R.color.bg_color_5, theme),
        };

        rl_background.setBackgroundColor(colorArray[0]);
        rl_foreground.setBackgroundColor(colorArray[0]);
    }


    private void createChoiceList() {
        choiceList = new ArrayList<>();

        choiceList.add("Adrenaline Zone");
        choiceList.add("Choreo");
        choiceList.add("Comedy");
        choiceList.add("Design");
        choiceList.add("Fine Arts");
        choiceList.add("Food Fest");
        choiceList.add("Gameplex");
        choiceList.add("Informals");
        choiceList.add("LifeStyle");
        choiceList.add("Media");
        choiceList.add("Music");
        choiceList.add("Oratory");
        choiceList.add("Quizzing");
        choiceList.add("Roadshows");
        choiceList.add("Spotlight");
        choiceList.add("Thespian");
        choiceList.add("Word Games");
        choiceList.add("World Fest");
        choiceList.add("Writing");
    }

    private void addChildViewsToFl() {
        flChildTextViews = new ArrayList<>();

        for (String choice : choiceList){
            TextView textView = new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(choice);
            textView.setTextSize(18.0f);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setBackgroundResource(R.drawable.rounded_corner_unpressed);
            textView.setOnClickListener(choiceItemClickListener);
            textView.setClickable(true);
            fl_activity_main.addView(textView);
            flChildTextViews.add(textView);
        }
    }

    private void initializeAnimation() {
        tv_almost_done
                .animate()
                .alpha(0.0f)
                .setStartDelay(1000)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
            @Override public void onAnimationEnd(Animator animation) {
                tv_but_first.animate().alpha(1.0f).setDuration(500).start();
            }
            @Override public void onAnimationStart(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
        }).start();

        rl_text_animation
                .animate()
                .alpha(0.0f)
                .setStartDelay(3000)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rl_text_animation.setVisibility(View.GONE);
                        rl_content
                                .animate()
                                .alpha(1.0f)
                                .setDuration(500)
                                .start();
                    }
                    @Override public void onAnimationStart(Animator animation) {}
                    @Override public void onAnimationCancel(Animator animation) {}
                    @Override public void onAnimationRepeat(Animator animation) {}
                }).start();
    }


    public void changeBackgroundColor(int cx, int cy) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            int finalRadius = (int) Math.hypot(rl_background.getWidth(), rl_background.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(rl_foreground, cx, cy, 0, finalRadius);
            anim.setDuration(300);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    rl_foreground.setBackgroundColor(colorArray[++colorIndex % colorArray.length]);
                    rl_foreground.setVisibility(View.VISIBLE);

                    b_continue.setTextColor(colorArray[colorIndex % colorArray.length]);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rl_background.setBackgroundColor(colorArray[colorIndex % colorArray.length]);
                    rl_foreground.setVisibility(View.INVISIBLE);
                }

                @Override public void onAnimationCancel(Animator animator) {}
                @Override public void onAnimationRepeat(Animator animator) {}
            });
            anim.start();
        }
    }

    View.OnClickListener choiceItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setSelected(!view.isSelected());

            if (view.isSelected()) {

                int[] location = new int[2];
                view.getLocationInWindow(location);

                int cx = location[0] + view.getWidth() / 2;
                int cy = location[1] + view.getHeight() / 2;
                changeBackgroundColor(cx, cy);
            }

            for (TextView child : flChildTextViews) {
                if (child.isSelected()) {
                    child.setTextColor(colorArray[colorIndex % colorArray.length]);
                    child.setBackgroundResource(R.drawable.rounded_corner_pressed);
                } else {
                    child.setTextColor(Color.parseColor("#FFFFFF"));
                    child.setBackgroundResource(R.drawable.rounded_corner_unpressed);
                }
            }

        }
    };
}
