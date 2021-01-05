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
    private TextView hintTextView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        //binding
        sendButton = findViewById(R.id.send_2_button);
        guessEditText = findViewById(R.id.guess_edit_text);
        hintTextView = findViewById(R.id.hint_text_view);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        String hint = sharedPreferences.getString(Constants.HINT, "");

        hintTextView.setText(hint);

        sendButton.setOnClickListener(v -> {
            String guess = guessEditText.getText().toString();
            Log.d("TAG: GUESS", guess);
            sharedPreferences.edit().putString(Constants.GUESS, guess).apply();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.GUESS, guess);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String hint = sharedPreferences.getString(Constants.HINT, "");
        hintTextView.setText(hint);
    }
}