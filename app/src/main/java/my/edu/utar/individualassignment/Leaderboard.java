package my.edu.utar.individualassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard extends AppCompatActivity {

    private LinearLayout myLayout;
    private static final String PREF_KEY_SCORE = "score";
    private TextView highScoreTextView;
    private TextView textView;
    //private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        textView = (TextView) findViewById(R.id.RankView);

        ArrayList<Player> players = new ArrayList<>();
        String filename = "Name.txt";
        FileInputStream fis;
        StringBuffer buffer = new StringBuffer(0);

        BufferedReader br;
        String strLine = null;

        try {
            fis = openFileInput(filename);
            br = new BufferedReader(new InputStreamReader(fis));
            while ((strLine = br.readLine()) != null) {
                String[] data = strLine.split(",");
                String name = data[0];
                int score = Integer.parseInt(data[1]);
                Player player = new Player(name, score);
                players.add(player);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sort the player according to score in descending order
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player score1, Player score2) {
                return score2.getScore() - score1.getScore();
            }
        });

        // extract the top 25 scores
        ArrayList<Player> top25 = new ArrayList<>();
        for (int i = 0; i < Math.min(25, players.size()); i++) {
            top25.add(players.get(i));
        }

        TextView scoresTextView = findViewById(R.id.RankView);
        String scoresText = "";

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            scoresText += (i + 1) + ". " + player.getName() + ": " + player.getScore() + "\n";
        }

        scoresTextView.setText(scoresText);

    }

    class Player {
        private final String name;
        private final int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}