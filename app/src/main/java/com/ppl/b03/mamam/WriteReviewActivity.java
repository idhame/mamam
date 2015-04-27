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


public class WriteReviewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Button btnRate = (Button) findViewById(R.id.btnRate);

        //if click on me, then display the current rating value.
        btnRate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                EditText ratingText = (EditText) findViewById(R.id.fld_review);
                EditText titleText = (EditText) findViewById(R.id.fld_title);
                final int id = getIntent().getIntExtra("id",0);
                SharedPreferences prefs = getSharedPreferences("codelearn_twitter", MODE_PRIVATE);
                String savedUsername = prefs.getString("usernameKey", null);
                Review review = new Review(titleText.getText().toString(), ratingText.getText().toString(),Integer.parseInt(savedUsername),ratingBar.getRating(),id);
                new AsyncReviewPost(WriteReviewActivity.this).execute(review);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_review, menu);
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
        Toast.makeText(WriteReviewActivity.this,
                "Penilaian telah berhasil disimpan",
                Toast.LENGTH_LONG).show();
        finish();
    }
}
