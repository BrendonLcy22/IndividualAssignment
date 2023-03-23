package my.edu.utar.individualassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Level3 extends AppCompatActivity {

    TextView Timer;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    public View[] circleViews = new View[16];
    private int randomIndex;
    private int score = 0;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);

        Intent intent = getIntent();
        int lastPage_score = intent.getIntExtra("score", 0); // 0 is the default value if "score" extra is not found

        linearLayout1 = findViewById(R.id.linear_layout_1);
        linearLayout2 = findViewById(R.id.linear_layout_2);
        linearLayout3 = findViewById(R.id.linear_layout_3);
        linearLayout4 = findViewById(R.id.linear_layout_4);
        Timer = findViewById(R.id.timer_textview);
        scoreTextView = findViewById(R.id.score_textview);
        scoreTextView.setText("Score: " + lastPage_score);

        int color = getRandomColor();
        score = lastPage_score;

        long duration = TimeUnit.SECONDS.toMillis(5);

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                //When tick
                //Convert millisecond to second
                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d"
                        , TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                //Set converted string on Textview
                Timer.setText((sDuration));

            }

            @Override
            public void onFinish() {
                //When finnish
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Level3.this);

                builder.setTitle("Times Up ! ");
                builder.setMessage("Do you wish to proceed to the next level?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Level3.this, Level4.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMenu();
                        //dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();



            }
        }.start();

        // Create 16 circle views
        for (int i = 0; i < 16; i++) {
            View circleView = new View(this);
            circleView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
            circleView.setBackgroundColor(getRandomColor());
            circleView.setClickable(true);

            // Set the layout parameters for the circle view (e.g. width, height, margin)
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
            layoutParams.setMargins(10, 10, 10, 10);
            circleView.setLayoutParams(layoutParams);
            //circleView.setLayoutParams(new LinearLayout.LayoutParams(2, 2));


            // Set the circle shape for the view
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            shape.setColor(color);

            circleView.setBackground(shape);

            StateListDrawable stateListDrawable = new StateListDrawable();

            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, shape);
            stateListDrawable.addState(new int[]{}, shape);

            circleView.setBackground(stateListDrawable);
            circleView.setClickable(true);

            circleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event
                    // Refresh the color of circle view
                    boolean isRandomView = false;
                    for (int i = 0; i < circleViews.length; i++) {
                        if (v == circleViews[i] && i == randomIndex) {
                            isRandomView = true;
                            break;
                        }
                    }

                    if (isRandomView) {
                        // Proceed with the for loop
                        int color2 = getRandomColor();

                        for (int i = 0; i < circleViews.length; i++) {
                            View circleView = circleViews[i];

                            GradientDrawable shape = new GradientDrawable();
                            shape.setShape(GradientDrawable.OVAL);
                            shape.setColor(color2);
                            circleView.setBackground(shape);

                            StateListDrawable stateListDrawable = new StateListDrawable();
                            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, shape);
                            stateListDrawable.addState(new int[]{}, shape);
                            circleView.setBackground(stateListDrawable);
                        }

                        // Get a new random index
                        randomIndex = getRandomIndex();

                        // Increase score by 1 and display it on screen
                        score++;
                        scoreTextView.setText("Score: " + score);
                        scoreTextView.setTextColor(Color.WHITE);
                    }

                }
            });
            circleViews[i] = circleView;

            if (i < 4) {
                linearLayout1.addView(circleView);
            }
            else if (i < 8) {
                linearLayout2.addView(circleView);
            }
            else if (i < 12) {
                linearLayout3.addView(circleView);
            }
            else{
                linearLayout4.addView(circleView);
            }

        }
        randomIndex = getRandomIndex();
    }

    public void openMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private int getRandomIndex(){
        int randomIndex = new Random().nextInt(circleViews.length);
        View chosenView = circleViews[randomIndex];

        // Change color of the chosen circle view
        GradientDrawable chosenShape = new GradientDrawable();
        chosenShape.setShape(GradientDrawable.OVAL);
        chosenShape.setColor(getRandomColor());
        chosenView.setBackground(chosenShape);
        return randomIndex;
    }

}