package divyanshu.miscellaneous;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
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

        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        rl_foreground = (RelativeLayout) findViewById(R.id.rl_foreground);
        fl_activity_main = (FlowLayout) findViewById(R.id.fl_activity_main);

        initializeBackground();
        createChoiceList();
        addChildViewsToFl();
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
            textView.setOnClickListener(clickListener);
            textView.setClickable(true);
            fl_activity_main.addView(textView);
            flChildTextViews.add(textView);
        }
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
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rl_background.setBackgroundColor(colorArray[colorIndex % colorArray.length]);
                    rl_foreground.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }
                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            anim.start();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
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
