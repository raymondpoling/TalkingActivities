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
import org.mousehole.talkingactivities.util.Constants;

public class GuessActivity extends AppCompatActivity {

    private Button sendButton;
    private EditText guessEditText;
    private TextView clueTextView;

    private SharedPreferences sharedPreferences;
    private String guesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        //binding
        sendButton = findViewById(R.id.send_2_button);
        guessEditText = findViewById(R.id.guess_edit_text);
        clueTextView = findViewById(R.id.clue_text_view);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        guesses = sharedPreferences.getString(Constants.GUESSES, "");

        Intent intent = getIntent(); // assume we are called (started) by an intent


        sendButton.setOnClickListener(v -> {
            String guess = guessEditText.getText().toString() + "\n" + guesses;
            Log.d("TAG: GUESS", guess);
            sharedPreferences.edit().putString(Constants.GUESSES, guess).apply();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.GUESSES, guess);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String hint = sharedPreferences.getString(Constants.HINTS, "");
        clueTextView.setText(hint);
    }
}