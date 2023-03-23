package my.edu.utar.individualassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Level;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = getIntent();
//        int lastPage_score = intent.getIntExtra("score", 0); // 0 is the default value if "score" extra is not found
//        scoreTextView.setText("Score: " + lastPage_score);

        button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openLevel1();
            }

        });

        button2 = (Button) findViewById(R.id.Leaderboard);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openLeaderboard();
            }

        });


    }

    public void openLevel1() {
        Intent intent = new Intent(this, Level1.class);
        startActivity(intent);
    }

    public void openLeaderboard() {
        Intent intent = new Intent(this, Leaderboard.class);
        startActivity(intent);
    }


}