package org.mousehole.talkingactivities.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.mousehole.talkingactivities.R;
import org.mousehole.talkingactivities.util.Constants;

import java.util.Random;

import static org.mousehole.talkingactivities.util.Constants.GAME_REQUEST;
import static org.mousehole.talkingactivities.util.Constants.PLAY;
import static org.mousehole.talkingactivities.util.Constants.SOLUTION;

public class AskerActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView solutionTextView, guessTextView;
    private EditText hintEditText;
    private Button submitButton, acceptButton;

    private final Random random = new Random();

    public void selectSolution() {
        String solution = sharedPreferences.getString(SOLUTION,
                 Constants.possibleAnswers[
                            random.nextInt(Constants.possibleAnswers.length)].toUpperCase());
        solutionTextView.setText(solution);
    }

    /*
    Used for cleaning up after a successful guess.
     */
    public void clear() {
        sharedPreferences.edit().clear().apply();
        guessTextView.setText("");
        selectSolution();
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

        selectSolution();

        String guess = sharedPreferences.getString(Constants.GUESS, "");

        guessTextView.setText(guess);

        submitButton.setOnClickListener(v -> {
            String hint = hintEditText.getText().toString();
            Log.d(Constants.HINT, "hint is " + hint);
            sharedPreferences.edit().putString(Constants.HINT, hint).apply();

            Intent hintIntent = new Intent(this, SwapActivity.class);
            hintIntent.putExtra(PLAY, hint);
            startActivityForResult(hintIntent, GAME_REQUEST);
        });

        acceptButton.setOnClickListener(v -> clear());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GAME_REQUEST) {
            guessTextView.setText(sharedPreferences.getString(Constants.GUESS, ""));
            hintEditText.setText("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String guess = sharedPreferences.getString(Constants.GUESS, "");
        guessTextView.setText(guess);
    }
}