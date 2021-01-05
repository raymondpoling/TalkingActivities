package org.mousehole.talkingactivities.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.mousehole.talkingactivities.R;
import org.mousehole.talkingactivities.util.Constants;

public class SwapActivity extends AppCompatActivity {

    private TextView swapPlayerReminderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);

        // bindings
        swapPlayerReminderTextView = findViewById(R.id.swap_reminder_text_view);

        Intent previousIntent = getIntent();
        Intent intent = new Intent(this, GuessActivity.class);
        intent.putExtra(Constants.PLAY, previousIntent.getSerializableExtra(Constants.PLAY).toString());

        swapPlayerReminderTextView.setOnClickListener(v -> {
            startActivityForResult(intent, Constants.GAME_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == Constants.GAME_REQUEST) {
            swapPlayerReminderTextView.setOnClickListener(v -> {
                setResult(Constants.GAME_REQUEST, data);
                finish();
            });

        }
    }
}