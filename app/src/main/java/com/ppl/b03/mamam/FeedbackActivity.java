package com.ppl.b03.mamam;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ppl.b03.mamam.models.Review;


public class FeedbackActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Button btnRate = (Button) findViewById(R.id.btnRate);
        //if click on me, then display the current rating value.
        btnRate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText ratingText = (EditText) findViewById(R.id.fld_review);
                final int id = getIntent().getIntExtra("id",0);
                SharedPreferences prefs = getSharedPreferences("codelearn_twitter", MODE_PRIVATE);
                String savedUsername = prefs.getString("usernameKey", null);
                Review review = new Review("", ratingText.getText().toString(),Integer.parseInt(savedUsername),0,id);
                new AsyncFeedbackPost(FeedbackActivity.this).execute(review);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void finishReview() {
        Toast.makeText(FeedbackActivity.this,
                "Feedback telah berhasil dikirim",
                Toast.LENGTH_LONG).show();
        finish();
    }
}
