package org.mousehole.talkingactivities.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mousehole.talkingactivities.R;
import org.mousehole.talkingactivities.model.GameData;
import org.mousehole.talkingactivities.util.Constants;

public class GuessActivity extends AppCompatActivity {

    private Button sendButton;
    private EditText guessEditText;
    private TextView hintTextView;

    private SharedPreferences sharedPreferences;
    private GameData gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        //binding
        sendButton = findViewById(R.id.send_2_button);
        guessEditText = findViewById(R.id.guess_edit_text);
        hintTextView = findViewById(R.id.hint_text_view);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        String guesses = sharedPreferences.getString(Constants.GUESS, "");
        String hints = sharedPreferences.getString(Constants.HINT, "");

        gameData = new GameData(guesses, hints);

        hintTextView.setText(gameData.getHints());

        sendButton.setOnClickListener(v -> {
            String guess = guessEditText.getText().toString() + "\n" + guesses;
            Log.d("TAG: GUESS", guess);
            gameData.addGuess(guess);
            sharedPreferences.edit().putString(Constants.GUESS, gameData.getGuesses()).apply();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.GUESS, gameData.getGuesses());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String hints = sharedPreferences.getString(Constants.HINT, "");
        String guesses = sharedPreferences.getString(Constants.GUESS, "");
        gameData = new GameData(guesses, hints);
        hintTextView.setText(gameData.getHints());
    }
}