package org.mousehole.talkingactivities.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mousehole.talkingactivities.R;
import org.mousehole.talkingactivities.util.Constants;

import java.util.Random;

import static org.mousehole.talkingactivities.util.Constants.PLAY;
import static org.mousehole.talkingactivities.util.Constants.GAME_REQUEST;
import static org.mousehole.talkingactivities.util.Constants.GUESSES;
import static org.mousehole.talkingactivities.util.Constants.HINTS;
import static org.mousehole.talkingactivities.util.Constants.SOLUTION;

public class AskerActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView solutionTextView, guessTextView;
    private EditText hintEditText;
    private Button submitButton, acceptButton;
    private String hints;

    private Random random = new Random();

    public String selectAnswer() {
        String answer = sharedPreferences.getString(SOLUTION,
                 Constants.possibleAnswers[
                            random.nextInt(Constants.possibleAnswers.length)].toUpperCase());
        solutionTextView.setText(answer);
        return answer;
    }

    public void clear() {
        hints = "";
        sharedPreferences.edit().clear().apply();
        guessTextView.setText("");
        selectAnswer();
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public String getHints() {
        return hints;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        solutionTextView = findViewById(R.id.solution_text_view);
        guessTextView = findViewById(R.id.guess_text_view);
        hintEditText = findViewById(R.id.hint_edit_text);
        submitButton = findViewById(R.id.send_1_button);
        acceptButton = findViewById(R.id.accept_button);

        selectAnswer();

        String hints = sharedPreferences.getString(HINTS, "");

        Log.d(HINTS, "What is the hints? " + hints);

        submitButton.setOnClickListener(v -> {
            Log.d(HINTS, "hints is: " + getHints());
            String hint = hintEditText.getText().toString() + "\n" + getHints();
            setHints(hint);
            Log.d(HINTS, "hint is " + hint);
            sharedPreferences.edit().putString(HINTS, hint).apply();

            Intent hintIntent = new Intent(this, SwapActivity.class);
            hintIntent.putExtra(PLAY, hints);
            startActivityForResult(hintIntent, GAME_REQUEST);
        });

        acceptButton.setOnClickListener(v -> {
            clear();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GAME_REQUEST) {
            guessTextView.setText(sharedPreferences.getString(GUESSES, ""));
            hintEditText.setText("");
        }
    }
}